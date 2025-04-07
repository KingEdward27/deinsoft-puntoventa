package com.cew.willax.puntoventaclient;

import com.cew.willax.puntoventaclient.config.StaticResourceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class PuntoVentaClientApplication extends WebMvcConfigurerAdapter {

//    @Autowired
//    AppConfig appConfig;

    private static final Logger log = LoggerFactory.getLogger(PuntoVentaClientApplication.class);
    
    public static void main(String[] args) {
        
        SpringApplication.run(PuntoVentaClientApplication.class, args);
//        System.out.println("asdasfd");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info(appConfig.getFileSystemBasePath());
//        registry.addResourceHandler("/**")
//                .addResourceLocations("file:///" + appConfig.getFileSystemBasePath() + "/");
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        allowedMethods("*").
                        allowedOrigins("http://localhost:4200").
                        allowedMethods("*");
            }
        };
    }
}
