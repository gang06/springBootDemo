package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * 需要生成接口的包路径
     */
    public static String packagePath = "com.example.demo.controller";

    /**
     * 创建rest风格API
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(packagePath))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API具体信息内容
     * @return
     */
    private ApiInfo apiInfo() {

        // API作者信息
        Contact contact = new Contact("姜小白", "blog.csdn.net/magi1201", "magi1201@126.com");

        return new ApiInfoBuilder()
                .title("springboot jpa项目API文档")
                .description("jpa项目接口文档")
                .termsOfServiceUrl("http://localhost:8080/user")
                .version("1.0")
                .contact(contact)
                .build();
    }
}