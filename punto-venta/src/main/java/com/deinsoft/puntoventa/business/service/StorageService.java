/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;
/**
 *
 * @author EDWARD-PC
 */
public interface StorageService {

//    void init();

    void store(MultipartFile file, String name);

    Stream<Path> loadAll(Path resourcesFolder);

    Path load(Path folderResources, String filename);

    byte[] loadAsBytes(Path resourcesFolder, String filename);

//    void deleteAll();
    
    public Resource loadAsResource(Path location, Path resourcesFolder, String filename);
    
    public String getPath(Path location, Path resourcesFolder, String fileName, long fileSize);
    
    public String getPathFromResourcesForValidate(Path location, Path resourcesFolder, String fileName, long fileSize);
    
}
