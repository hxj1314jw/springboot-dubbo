package com.spring.dto.test;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/15.
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name ;

    private String age;

    private String sex;

}
