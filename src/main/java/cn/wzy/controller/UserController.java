package cn.wzy.controller;

import cn.wzy.entity.User;
import cn.wzy.service.UserService;
import org.cn.wzy.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Wzy
 * on 2018/6/4
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/query.do", method = RequestMethod.GET)
    public User queryById(int userId) {
        return userService.queryById(userId);
    }
}
