package com.spring.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/11.
 */
@Data
public class OutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean isSuccess;

    private String errorNo;

    private String errorInfo;

    private String errorPathInfo;

    private Object data;


}
