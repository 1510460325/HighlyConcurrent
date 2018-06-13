package cn.wzy.dao.Impl;

import cn.wzy.dao.UserDao;
import cn.wzy.entity.User;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Wzy
 * on 2018/6/4
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.dao.UserDao";
    }
}
