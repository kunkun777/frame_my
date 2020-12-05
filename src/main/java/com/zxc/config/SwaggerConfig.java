package com.zxc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enable;

    @Bean
    public Docket createDocket(){
        List<Parameter> parameters=new ArrayList<>();
        ParameterBuilder accessTokenBuilder=new ParameterBuilder();
        accessTokenBuilder.name("authorization")
                    .description("动态传入所携带的参数1：")
                    .modelRef(new ModelRef("String"))
                    .parameterType("authorization_")
                    .required(false)
                    ;
        ParameterBuilder refreshTokenBuilder=new ParameterBuilder();
        refreshTokenBuilder.name("refreshTokenBuilder")
                .description("动态传入所携带的参数2：")
                .modelRef(new ModelRef("String"))
                .parameterType("authorization_")
                .required(false)
                ;
        parameters.add(refreshTokenBuilder.build());
        parameters.add(accessTokenBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zxc.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .enable(enable)
                ;
    }

    /**
     *
     * @return 关于Api文档的详细信息
     */
    private ApiInfo apiInfo(){
        //创建者信息
        Contact contact=new Contact("zxc","www.github.com","3126297263@qq.com");
        return new ApiInfo("权限管理项目",
                "后端人员权限管理项目",
                "版本1.0",
                "www.baidu.com",
                contact,
                "许可证书",
                "www.baidu.com",
                new ArrayList<>()
        );
    }

}
