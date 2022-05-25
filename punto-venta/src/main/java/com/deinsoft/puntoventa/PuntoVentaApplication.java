package com.deinsoft.puntoventa;

import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PuntoVentaApplication {

    @Autowired
    JdbcRepository jdbcRepository;
    
    public static void main(String[] args) {
        
        SpringApplication.run(PuntoVentaApplication.class, args);
    }

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
