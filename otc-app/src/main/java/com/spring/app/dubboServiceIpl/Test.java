package com.spring.app.dubboServiceIpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hundsun.t2sdk.impl.util.AbstractLogAdapter;
import com.hundsun.t2sdk.impl.util.CommonLogAdapter;
import com.spring.api.t2sdk.TestApi;
import com.spring.app.common.T2SdkTool;
import com.spring.app.config.cache.RedisService;
import com.spring.app.mapper.test.TestMapper;
import com.spring.app.service.test.TestService;
import com.spring.common.utils.UtilsMap;
import com.spring.dto.OutDto;
import com.spring.dto.T2SdkSet;
import com.spring.dto.test.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/9.
 */
@Service(protocol="dubbo-hessian")
@EnableCaching
public class Test implements TestApi  {
    @Autowired
    private T2SdkTool t2SdkTool;
    private static AbstractLogAdapter logAdapter = new CommonLogAdapter();
    @Autowired
    private TestService testService;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private RedisService redisService;
    @Override
    public String test(String s) {
        return "测试成功1====" + s;
    }

    @Override
    public String testMap(Map<String, String> map) {
        return UtilsMap.MapToSting(map);
    }

    @Override
    public T2SdkSet t2sdk(String function_id, Map<Object, Object> map){
        return t2SdkTool.getT2SdkInfo(function_id,map);
    }

    @Override
    public OutDto t2sdkOutDto(String function_id, Map<Object, Object> map) {
        T2SdkSet t2SdkSet = t2SdkTool.getT2SdkInfo(function_id,map);
        OutDto outDto = new OutDto();
        outDto.setSuccess(t2SdkSet.isSuccess());
        outDto.setData(t2SdkSet.getDataset());
        outDto.setErrorInfo(t2SdkSet.getErrorInfo());
        outDto.setErrorNo(t2SdkSet.getErrorNo());
        outDto.setErrorPathInfo(t2SdkSet.getErrorPathInfo());
        return outDto;
    }

    @Override
    public List<UserInfo> selectUserInfo() {
        List<UserInfo> list = testMapper.listUser();
        redisService.put("aaaaa",list,10000);
        return  list;
    }
    @Cacheable
    @Override
    public List<UserInfo> selectUserInfoService() {
        List<UserInfo> list = testService.selectListAll();
        return  list;
    }

}
