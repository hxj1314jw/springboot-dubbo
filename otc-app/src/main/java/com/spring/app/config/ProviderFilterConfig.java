package com.spring.app.config;

import com.alibaba.dubbo.rpc.Result;
import com.reger.dubbo.rpc.filter.JoinPoint;
import com.reger.dubbo.rpc.filter.ProviderFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Date: 2018/1/9
 * Time: 1:22
 * Description:
 */
@Configuration
@Slf4j
public class ProviderFilterConfig {
    @Bean
    public ProviderFilter providerFilter1() {
        return new ProviderFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("1.方法{}.{}被调用 ", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }

    @Bean
    public ProviderFilter providerFilter2() {
        return new ProviderFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("2.方法{}.{}被调用 ", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }

    @Bean
    public ProviderFilter providerFilter3() {
        return new ProviderFilter() {
            @Override
            public Result invoke(JoinPoint<?> joinPoint) {
                log.info("3.方法{}.{}被调用 ", joinPoint.getInterface(),joinPoint.getMethodName());
                return joinPoint.proceed();
            }
        };
    }

}
