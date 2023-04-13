package org.zzd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zzd.bean.LoginProperties;

/**
 * @author zzd
 * @apiNote 配置文件转换Pojo类的 统一配置 类
 * @date 2023/3/31 14:28
 */
@Configuration
public class ConfigBeanConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "login")
    public LoginProperties loginProperties() {
        return new LoginProperties();
    }

}
