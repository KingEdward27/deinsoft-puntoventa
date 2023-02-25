/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.config;

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
    
    
}
