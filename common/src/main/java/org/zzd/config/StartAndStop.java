package org.zzd.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.zzd.utils.ApiUtils;

/**
 * @author :zzd
 * @apiNote :项目启动和停止的日志打印
 * @date : 2023-03-01 20:34
 */
@Slf4j
@Component
public class StartAndStop implements ApplicationRunner, DisposableBean {
    @Value("${server.port}")
    private String port;

    /**
     * @apiNote 运行时执行
     * @date 2023/3/2 10:48
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("==============项目启动成功！===================");
        log.info("接口文档地址：http://{}:{}/doc.html", ApiUtils.getHostIp(), port);
        log.info("============================================");
    }

    /**
     * @apiNote 关闭项目时执行
     * @date 2023/3/2 10:49
     */
    @Override
    public void destroy() {
        log.info("=======================================");
        log.info("==============程序已停止运行！============");
        log.info("=======================================");
    }
}