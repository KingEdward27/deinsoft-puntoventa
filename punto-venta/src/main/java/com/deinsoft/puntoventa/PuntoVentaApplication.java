package com.deinsoft.puntoventa;

import com.deinsoft.puntoventa.business.service.ActPagoProgramacionService;
import com.deinsoft.puntoventa.config.AppConfig;
import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AppConfig.class)
public class PuntoVentaApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {

    @Autowired
    JdbcRepository jdbcRepository;
    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    ActPagoProgramacionService actPagoProgramacionService;
    
    @Autowired
    AppConfig appConfig;
    
    public static void main(String[] args) {
        
        SpringApplication.run(PuntoVentaApplication.class, args);
    }
    
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").
//                        allowedMethods("*").
//                        allowedOrigins("http://localhost:4200").
//                        allowedMethods("*");
//            }
//        };
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + appConfig.getFolderResources() + "/**")
                .addResourceLocations("file:///" + appConfig.getFileSystemBasePath() + "/",
                        "file:///" + appConfig.getFileSystemBasePath() + "/temp/");

    }

//    @Bean
//    CommandLineRunner init(StorageService storageService) {
//        return (args) -> {
////            storageService.deleteAll();
//            storageService.init();
//        };
//    }
    
    @Scheduled(cron = "0 49 23 * * *")
    void refreshProgramacionPagos() {
        System.out.println("init refreshProgramacionPagos()");
        actPagoProgramacionService.refreshProgramacionPagos();
    }
    
    @Override
    public void run(String... args) throws Exception {
        String password = "123456";

        for (int i = 0; i < 2; i++) {
            String bcryptPassword = passwordEncoder.encode(password);
            System.out.println(bcryptPassword);
        }
    }
    
}
