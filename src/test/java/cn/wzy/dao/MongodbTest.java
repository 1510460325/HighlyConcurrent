package cn.wzy.dao;

import cn.wzy.entity.Room;
import cn.wzy.entity.User;
import cn.wzy.entity.User_info;
import org.cn.wzy.query.BaseQuery;

import java.util.List;

/**
 * Create by Wzy
 * on 2018/7/28 16:39
 * 不短不长八字刚好
 */
public class MongodbTest {

    public static void main(String[] args){
//        User_info record = new User_info();
//        record.setRole(1);
//        BaseQuery<User_info> query = new BaseQuery<>();
//        query.setQuery(record);
//
//        BaseMongoDao dao = new BaseMongoDao("test");
//        List<User_info> result = dao.queryByCondition(query, true);
//        for (User_info user : result) {
//            System.out.println(user);
//        }

//        Room room = new Room();
//        room.setId(1).setAge(15).setBirth(new Date()).setName("wzy");
        BaseMongoDao dao = new BaseMongoDao();
        User_info user = new User_info();
        user.setName("那片海").setAge(15).setRole(1).setId(1);
        System.out.println(dao.deleteById(2,user.getClass()));
        List<User_info> list = dao.queryByCondition(new BaseQuery<>(User_info.class),true);
        for (User_info record: list) {
            System.out.println(record);
        }
    }
}
