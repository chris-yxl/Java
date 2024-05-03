package com.example.demo.util;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chris
 * @className demo
 * @date 2024/4/30 18:06
 * @description:
 */
@Configuration
@EnableOpenApi //注解启动用Swagger的使用，同时在配置类中对Swagger的通用参数进行配置
public class Swagger3Config implements WebMvcConfigurer {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
                //.apis(scanBasePackage(packageNames))
                .paths(PathSelectors.any())
                .build();
        //.directModelSubstitute(Timestamp.class, Date.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger3接口文档").version("1.0").build();
    }

    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
            WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier,
            ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes,
            CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = webEndpointProperties.getDiscovery().isEnabled() &&
                (org.springframework.util.StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }
}
