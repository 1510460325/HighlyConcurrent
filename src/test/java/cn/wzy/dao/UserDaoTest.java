package cn.wzy.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Wzy
 * on 2018/6/4
 */
public class UserDaoTest extends BaseTest{

    @Autowired
    private UserDao userDao;


    @Test
    public void test1() {
        System.out.println(userDao.selectByPrimaryKey(1));
    }
}
