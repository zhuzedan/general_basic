package org.zzd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zzd
 * @apiNote :接口文档加强
 * @date : 2023-02-27 21:33
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {
    private final static String headerName = "Authorization";
    @Bean
    public Docket adminApiConfig(){

        Docket adminApi = new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示admin路径下的页面
                .apis(RequestHandlerSelectors.basePackage("org.zzd"))
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .securitySchemes(securitySchemes())//配置安全方案
                .securityContexts(securityContexts())//配置安全方案所实现的上下文
                ;
        return adminApi;
    }
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        //配置header头1
        ApiKey token_access = new ApiKey(headerName, headerName, "header");
        apiKeyList.add(token_access);
        return apiKeyList;
    }
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        //为每个api添加请求头
        securityReferenceList.add(new SecurityReference(headerName, scopes()));
        securityContextList.add(SecurityContext
                .builder()
                .securityReferences(securityReferenceList)
                .forPaths(PathSelectors.any())
                .build()
        );
        return securityContextList;
    }
    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessAnything")};//作用域为全局
    }
    private ApiInfo adminApiInfo(){

        return new ApiInfoBuilder()
                .title("通用后台管理系统-API文档")
                .description("zzd写的接口文档")
                .version("1.0")
                .contact(new Contact("zzd", "http://zhuzedan.githubi.io", "1031155817@qq.com"))
                .build();
    }
}