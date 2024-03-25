/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
@Configuration
@ConfigurationProperties
public class AppConfig {
    
    @Value("${app.fileSystem.basePath}")
    private String fileSystemBasePath; 

//    @Value("${app.config.backend.preResources}")
//    private String folderPreResources; 
//    
    @Value("${app.config.backend.resources}")
    private String folderResources; 
    
    @Value("${app.config.backend.url}")
    private String urlBackend;

    @Value("${app.staticResourcesPath}")
    private String staticResourcesPath;
            
    @NotEmpty
    @Value("${app.config.mail.smtp.host}")
    private String mailHost;

    @NotEmpty
    @Value("${app.config.mail.smtp.port}")
    private String mailPort;

    @NotEmpty
    @Value("${app.config.mail.smtp.auth}")
    private String mailAuth;

    @NotEmpty
    @Value("${app.config.mail.user}")
    private String sendEmailEmail;

    @NotEmpty
    @Value("${app.config.mail.pass}")
    private String sendEmailPassword;
    
    public String getFileSystemBasePath() {
        return fileSystemBasePath;
    }

    public void setFileSystemBasePath(String fileSystemBasePath) {
        this.fileSystemBasePath = fileSystemBasePath;
    }

    public String getUrlBackend() {
        return urlBackend;
    }

    public void setUrlBackend(String urlBackend) {
        this.urlBackend = urlBackend;
    }

    public String getFolderResources() {
        return folderResources;
    }

    public void setFolderResources(String folderResources) {
        this.folderResources = folderResources;
    }

    public String getStaticResourcesPath() {
        return staticResourcesPath;
    }

    public void setStaticResourcesPath(String staticResourcesPath) {
        this.staticResourcesPath = staticResourcesPath;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getMailPort() {
        return mailPort;
    }

    public void setMailPort(String mailPort) {
        this.mailPort = mailPort;
    }

    public String getMailAuth() {
        return mailAuth;
    }

    public void setMailAuth(String mailAuth) {
        this.mailAuth = mailAuth;
    }

    public String getSendEmailEmail() {
        return sendEmailEmail;
    }

    public void setSendEmailEmail(String sendEmailEmail) {
        this.sendEmailEmail = sendEmailEmail;
    }

    public String getSendEmailPassword() {
        return sendEmailPassword;
    }

    public void setSendEmailPassword(String sendEmailPassword) {
        this.sendEmailPassword = sendEmailPassword;
    }
    
    
}
