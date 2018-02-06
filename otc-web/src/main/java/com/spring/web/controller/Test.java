package com.spring.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.remoting.exchange.Response;
import com.alibaba.fastjson.JSONObject;
import com.hundsun.t2sdk.impl.util.AbstractLogAdapter;
import com.hundsun.t2sdk.impl.util.CommonLogAdapter;
import com.spring.api.t2sdk.TestApi;
import com.spring.common.utils.UtilsMap;
import com.spring.dto.OutDto;
import com.spring.dto.T2SdkSet;
import com.spring.dto.test.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/9.
 */
@RestController
@RequestMapping("/springboot")
public class Test {
    @Reference
    private TestApi testApi;
    private static AbstractLogAdapter logAdapter = new CommonLogAdapter();
    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(HttpServletRequest request) {
        String String = request.getParameter("String").toLowerCase();
        return testApi.test(String);
    }

    @RequestMapping(value = "/mapTest", method = RequestMethod.POST)
    @ResponseBody
    public String mapTest(HttpServletRequest request) {
        String param = request.getParameter("param");
        JSONObject json = JSONObject.parseObject(param);
        Map<Object, Object> map = new HashMap<>();
        try {
            map = UtilsMap.getParam(param);
        } catch (Exception e) {
            return e.getMessage();
        }
        return testApi.testMap(UtilsMap.objectToString(map));
    }

    @RequestMapping(value = "/t2sdkTest", method = RequestMethod.POST)
    @ResponseBody
    public Response t2sdkTest(HttpServletRequest request) {
        String param = request.getParameter("param");
        String function_id = request.getParameter("function_id");
        Response response = new Response();
        Map<Object, Object> map = new HashMap<>();
        try {
            map = UtilsMap.getParam(param);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
        }
        T2SdkSet t2SdkSet = testApi.t2sdk(function_id,map);
        response.setResult(t2SdkSet);
        response.setStatus(Response.OK);
        return response;
    }

    @RequestMapping(value = "/t2sdkOutDto", method = RequestMethod.POST)
    @ResponseBody
    public Response t2sdkOutDto(HttpServletRequest request) {
        String param = request.getParameter("param");
        logAdapter.log("param入参：----"+param);
        String function_id = request.getParameter("function_id");
        Response response = new Response();
        Map<Object, Object> map = new HashMap<>();
        try {
            map = UtilsMap.getParam(param);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
        }
        OutDto outDto = testApi.t2sdkOutDto(function_id,map);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",outDto.isSuccess());
        jsonObject.put("data",outDto.getData());
        jsonObject.put("error",outDto.getErrorInfo()+outDto.getErrorNo()+outDto.getErrorPathInfo());
        response.setResult(jsonObject);
        response.setStatus(Response.OK);
        return response;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public Response user(HttpServletRequest request) {
        Response response = new Response();
        List<UserInfo> userInfos = testApi.selectUserInfo();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",userInfos);
        response.setResult(jsonObject);
        response.setStatus(Response.OK);
        return response;
    }

    @RequestMapping(value = "/userService", method = RequestMethod.POST)
    public Response userService(HttpServletRequest request) {
        Response response = new Response();
        List<UserInfo> userInfos = testApi.selectUserInfoService();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",userInfos);
        response.setResult(jsonObject);
        response.setStatus(Response.OK);
        return response;
    }
}
