package com.spring.app.common;

import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;

import java.util.logging.Logger;

public class T2ClientService {
    private final static Logger logger = Logger.getLogger("linkstec.T2ClientService");
    private static T2Services sdkServices = null;

    private static IClient client = null;

    // 私有的构造方法，避免外部创建实例
    private static IClient getClient() throws Exception {

        if (sdkServices == null){
            // 获取T2Services实例
            sdkServices = T2Services.getInstance();

            // 初始化t2sdk-config.xml文件
            sdkServices.init();

            sdkServices.start();
        }

        client = sdkServices.getClient("clientLogin");

        return client;
    }

    synchronized public static IClient getInstance() throws Exception {
        if (client == null) {
            client = getClient();
        }
        return client;
    }

    public static void destroy() throws Exception {

    }

    public static void destroyNull() {
        client = null;
    }
}
