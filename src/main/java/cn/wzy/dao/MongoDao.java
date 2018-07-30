package cn.wzy.dao;

import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/29 16:12
 * 不短不长八字刚好
 */
@Repository
public class MongoDao {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private MongoDbFactory factory;

    public <Q> List<Q> queryList(BaseQuery<Q> query) {
        factory.getDb("").getMongo();
        return null;
    }


}
