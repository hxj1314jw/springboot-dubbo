# spring boot项目


## 项目结构
```
sgchou
├── bin -- 命令行工具
├── blockchian -- 区块链SDK
├── generator -- 代码生成器
├── sg-common -- 公共模块
├── sg-entity -- 实体类
├── sg-api -- 核心业务
├── sg-web -- 前端工程
├── sg-server -- 接口服务
     ├── config -- 项目配置
     ├── controller -- API接口
     ├── interceptor -- 拦截器
     ├── mapper -- dao层
     ├── service -- service层
     ├── utils -- 帮助模块
     └── Application.java -- 项目入口
└── sg-monitor -- 监控模块
```

### generator
> 自动生成代码

### otc-common
> 一个定义了各种各样工具类的块。待到用的时候再看.

### otc-dto
>实体类存放块。

### otc-app
>接口服务，为前端以及后台提供接口

### blockchian
>区块链相关的SDK

### sg-monitor
>利用spring-boot admin监控服务

## 项目部署
### 本地开发环境搭建
1. 安装配置jdk、maven环境,安装IDEA,安装lombok、Free Mybatis plugin插件,安装MySQL，安装redis.
2. 修改generator，sg-server项目中的application.yml文件中的数据库以及redis配置。
3. 新建caritas数据库
4. 本地启动zookeeper，默认端口2181
5. 首先启动sg-monitor的MonitorApplication.java主函数，然后运行sg-server的Application.java启动项目，最后启动sg-web前端工程
6. 访问localhost:9003

#服务启动 指定jdk1.8 启动不需要修改环境变量
/usr/local/jdk1.8.0_40/bin/java -Xms2048m -Xmx2048m -Duser.timezone=GMT+8 -jar otc-app.jar --spring.profiles.active=dev &
/usr/local/jdk1.8.0_144/bin/java -Xms2048m -Xmx2048m -Duser.timezone=GMT+8 -jar otc-app.jar --spring.profiles.active=dev &

