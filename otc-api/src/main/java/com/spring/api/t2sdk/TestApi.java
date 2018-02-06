package com.spring.api.t2sdk;

import com.spring.dto.OutDto;
import com.spring.dto.T2SdkSet;
import com.spring.dto.test.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/9.
 */
public interface TestApi {
    String test(String s);

    String testMap(Map<String,String> map);

    T2SdkSet t2sdk(String function_id, Map<Object, Object> map);

    OutDto t2sdkOutDto(String function_id, Map<Object, Object> map);

    List<UserInfo> selectUserInfo();

    List<UserInfo> selectUserInfoService();
}
