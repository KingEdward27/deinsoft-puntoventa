package com.deinsoft.puntoventa;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.setProperty("server.servlet.context-path", "/ventas-backend");
        return application.sources(PuntoVentaApplication.class);
    }

}
