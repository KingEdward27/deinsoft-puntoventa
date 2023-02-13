package com.deinsoft.puntoventa.framework;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AppConfig
{
  @NotEmpty
  @Value("${app.mysqlDatabase}")
  private String mysqlDatabase;
  

    public String getMysqlDatabase() {
        return mysqlDatabase;
    }

    public void setMysqlDatabase(String mysqlDatabase) {
        this.mysqlDatabase = mysqlDatabase;
    }

}
