package com.spring.app.mapper.test;

import com.spring.dto.test.UserInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2018/1/17.
 */
@Repository
public interface UserMapper extends Mapper<UserInfo> {
}
