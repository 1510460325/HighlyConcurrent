package cn.wzy.dao;

import cn.wzy.annotation.MGColName;
import cn.wzy.util.MapUtil;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.cn.wzy.query.BaseQuery;
import org.cn.wzy.util.PropertiesUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by Wzy
 * on 2018/7/28 18:15
 * 不短不长八字刚好
 */
public class BaseMongoDao {

    private static final MongoClient mongoClient;

    private static final MongoDatabase mongo;

    static {
        MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(150)
                .maxWaitTime(2000)
                .socketTimeout(2000)
                .maxConnectionLifeTime(5000)
                .connectTimeout(5000).build();
        ServerAddress serverAddress = new ServerAddress(PropertiesUtil.StringValue("mongo.host"),
                PropertiesUtil.IntegerValue("mongo.port"));
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential(
                PropertiesUtil.StringValue("mongo.user")
                , PropertiesUtil.StringValue("mongo.db")
                , PropertiesUtil.StringValue("mongo.pwd").toCharArray());
        mongoClient = new MongoClient(addrs, credential, options);
        mongo = mongoClient.getDatabase(PropertiesUtil.StringValue("mongo.db"));
    }

    private String collection;

    private MongoCollection<Document> thisCollection() {
        return mongo.getCollection(collection);
    }


    public <Q> List<Q> queryByCondition(BaseQuery<Q> query, boolean up) {
        Q record = query.getQuery();
        try {
            changeCollection(record.getClass());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
        BasicDBObject cond = new BasicDBObject(MapUtil.parseEntity(record));
        FindIterable<Document> findIterable;
        if (query.getStart() != null && query.getRows() != null)
            findIterable = thisCollection().find(cond)
                    .sort(new BasicDBObject("id", up ? 1 : -1))
                    .skip((query.getStart() - 1) * query.getRows())
                    .limit(query.getRows());
        else
            findIterable = thisCollection().find(cond)
                    .sort(new BasicDBObject("id", up ? 1 : -1));
        MongoCursor<Document> iterator = findIterable.iterator();
        List<Q> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            result.add((Q) MapUtil.castToEntity(document, record.getClass()));
        }
        iterator.close();
        return result;
    }

    public <Q> Integer queryCoditionCount(BaseQuery<Q> query) {
        Q record = query.getQuery();
        try {
            changeCollection(record.getClass());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
        BasicDBObject cond = new BasicDBObject(MapUtil.parseEntity(record));
        return (int) thisCollection().countDocuments(cond);
    }

    public <Q> boolean insertOne(Q record) {
        BasicDBObject cond = new BasicDBObject(MapUtil.parseEntity(record));
        try {
            changeCollection(record.getClass());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        try {
            int top = getTop();
            cond.put("id", ++top);
            thisCollection().insertOne(new Document(cond));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public <Q> boolean insertList(List<Q> records) {
        try {
            changeCollection(records.get(0).getClass());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        try {
            List<Document> list = new ArrayList<>(records.size());
            if (!changeIds(records))
                return false;
            for (Q record : records) {
                list.add(new Document(MapUtil.parseEntity(record)));
            }
            thisCollection().insertMany(list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Integer id, Class<?> clazz) {
        try {
            changeCollection(clazz);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        try {
            if (id == null)
                return false;
            thisCollection().deleteOne(new BasicDBObject("id", id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteByIds(List<Integer> ids, Class<?> clazz) {
        try {
            changeCollection(clazz);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        BasicDBObject cond = new BasicDBObject("id", new BasicDBObject("$in", ids.toArray()));
        try {
            thisCollection().deleteMany(cond);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 只通过id更改，查询就只是搜索id
     *
     * @param record
     * @param <Q>
     */
    public <Q> boolean updateById(Q record) {
        try {
            changeCollection(record.getClass());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        Map<String, Object> cond = MapUtil.parseEntity(record);
        BasicDBObject update = new BasicDBObject("$set", cond);
        BasicDBObject query = new BasicDBObject("id", cond.get("id"));
        thisCollection().updateOne(query, update);
        return true;
    }

    /**
     * 使id自增
     *
     * @param records
     * @param <Q>
     * @return
     */
    private <Q> boolean changeIds(List<Q> records) {
        try {
            changeCollection(records.get(0).getClass());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        if (records == null || records.size() == 0)
            return false;
        Class clazz = records.get(0).getClass();
        try {
            Field id = clazz.getDeclaredField("id");
            id.setAccessible(true);
            int top = getTop();
            for (Q record : records) {
                id.set(record, ++top);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查找顶针
     *
     * @return
     */
    private int getTop() {
        MongoCursor<Document> documents = thisCollection().find().sort(new BasicDBObject("id", -1)).iterator();
        if (documents != null && documents.hasNext()) {
            return ((Number) documents.next().get("id")).intValue();
        }
        return 0;
    }

    private void changeCollection(Class clazz) throws Throwable {
        MGColName mgColName = (MGColName) clazz.getAnnotation(MGColName.class);
        if (mgColName == null) {
            throw new Throwable("The entity must map a mongodb collection");
        }
        Method colName = mgColName.annotationType().getDeclaredMethod("value");
        String name = (String) colName.invoke(mgColName);
        if (name.trim().equals("")) {
            throw new Throwable("mongodb collection must not be null");
        }
        this.collection = name;
    }
}
