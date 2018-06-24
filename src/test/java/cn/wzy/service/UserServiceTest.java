package cn.wzy.service;

import cn.wzy.dao.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Wzy
 * on 2018/6/4
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        System.out.println(userService.queryById(1));
        System.out.println(userService.queryById(1));
    }
}
