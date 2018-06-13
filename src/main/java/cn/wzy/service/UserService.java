package cn.wzy.service;

import cn.wzy.dao.Impl.RedisDao;
import cn.wzy.dao.UserDao;
import cn.wzy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Wzy
 * on 2018/6/4
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisDao redisDao;

    @Transactional
    public User queryById(int id) {
        userDao.selectByPrimaryKey(id);
        User user = redisDao.getUser(id);
        if (user == null) {
            user = userDao.selectByPrimaryKey(id);
            System.out.println("select from db.");
            if (user == null)
                return null;
            else {
                redisDao.putUser(user);
                return user;
            }
        }
        System.out.println("select from redis.");
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return user;
    }
}
