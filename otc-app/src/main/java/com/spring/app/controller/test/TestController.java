package com.spring.app.controller.test;

import com.spring.app.mapper.test.UserMapper;
import com.spring.app.service.test.TestService;
import com.spring.dto.test.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */
@RestController
public class TestController {
    @Autowired
    private UserMapper testService;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public String test(){
        List<UserInfo> list = testService.selectAll();
        return  list.toString();
    }
}
