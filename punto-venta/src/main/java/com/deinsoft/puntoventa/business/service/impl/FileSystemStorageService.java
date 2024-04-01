
package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.service.StorageService;
import com.deinsoft.puntoventa.business.exception.StorageException;
import com.deinsoft.puntoventa.business.exception.StorageFileNotFoundException;
import com.deinsoft.puntoventa.config.AppConfig;
import com.deinsoft.puntoventa.framework.security.JWTAuthenticationEntryPoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author EDWARD-PC
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    AppConfig appConfig;
    
    
    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    @Autowired
    public FileSystemStorageService(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.rootLocation = Paths.get(appConfig.getFileSystemBasePath());
    }

    /**
     * Class: FileSystemStorageService Method: storeToRepo Params;
     * filePathNameOrig, filePathDest, fileNameDest Description: save from temp
     * folder to categorized folder with data from form
     */
//    @Override
//    public void storeToRepo(String filePathNameOrig, String filePathDest, String fileNameDest) {
//        try {
//            File file = new File(this.rootLocationTemp + "/" + filePathNameOrig);
//
//            Path path = Paths.get(this.rootLocation + "/" + filePathDest);
//
//            Files.createDirectories(path);
//            Path destinationFile = this.rootLocation.resolve(
//                    Paths.get(this.rootLocation + filePathDest + "/" + fileNameDest))
//                    .normalize().toAbsolutePath();
//
//            try ( InputStream inputStream = new FileInputStream(file)) {
//                Files.copy(inputStream, destinationFile,
//                        StandardCopyOption.REPLACE_EXISTING);
//            }
//        } catch (IOException e) {
//            throw new StorageException("Failed to store file.", e);
//        }
//    }

    /**
     * Class: FileSystemStorageService Method: storeToRepo Params; MultipartFile
     * file Description: save in temp folder
     */
    @Override
    public void storeTemp(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get("temp/" + file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            logger.info(this.rootLocation.toString());
            logger.info(destinationFile.toAbsolutePath().toString());
//            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
//                // This is a security check
//                throw new StorageException(
//                        "Cannot store file outside current directory.");
//            }
            try ( InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                
            }
            
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
    
    @Override
    public void store(MultipartFile file, String name) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(name))
                    .normalize().toAbsolutePath();
//            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath()) 
//                    && !destinationFile.getParent().equals(Paths.get(this.rootLocation + "\temp"))) {
//                // This is a security check
//                throw new StorageException(
//                        "Cannot store file outside current directory.");
//            }
            try ( InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    /**
     * Class: FileSystemStorageService Method: storeToRepo Params; Path
     * resourcesFolder Description: load all respurces from resources folder as
     * param
     */
    @Override
    public Stream<Path> loadAll(Path location) {
        try {
            return Files.walk(location, 1000)
                    .filter(path -> !path.equals(location))
                    .map(location::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(Path location, String filename) {
        return location.resolve(filename);
    }

    @Override
    public byte[] loadAsBytes(Path resourcesFolder, String filename) {
        try {
            Path file = load(resourcesFolder, appConfig.getFileSystemBasePath() + "/" + filename);
            Resource resource = new UrlResource(file.toUri()); 
            if (resource.exists() || resource.isReadable()) {
                return resource.getInputStream().readAllBytes();
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        } catch (IOException ex) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, ex);
        }
    }

    @Override
    public String getPath(Path location, Path resourcesFolder, String fileName, long fileSize) {
        return appConfig.getUrlBackend() + resourcesFolder.toString() ;
//        try {
//            return loadAll(location)
//                    .filter(predicate -> {
//                        try {
//                            if (predicate.getFileName().endsWith(fileName)) {
//                                Resource file = loadAsResource(location, resourcesFolder, predicate.toString());
//                                
//                                return predicate.getFileName().endsWith(fileName)
//                                        && file.getInputStream().available() == fileSize;
//                            } else {
//                                return false;
//                            }
//
//                        } catch (IOException ex) {
//
//                            java.util.logging.Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
//                            return false;
//                        }
//                    })
//                    .map(path -> {
//                        System.out.println("path " + path);
//                        return appConfig.getUrlBackend() + resourcesFolder.toString() + "/" + path.toString();
//                    })
//                    .findFirst().orElse("");
//        } catch (RuntimeException ex) {
//            java.util.logging.Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
//            return "";
//        }
    }

    @Override
    public String getPathFromResourcesForValidate(Path location, Path resourcesFolder, String fileName, long fileSize) {
        try {
            return loadAll(location)
                    .filter(predicate -> {
                        try {
                            if (predicate.getFileName().toString().contains(".")) {
                                Resource file = loadAsResource(location, resourcesFolder, predicate.toString());
                                return file.getInputStream().available() == fileSize;
                            } else {
                                return false;
                            }

                        } catch (IOException ex) {

                            java.util.logging.Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
                            return false;
                        }
                    })
                    .map(path -> {
                        System.out.println("path " + path);
                        return path.toString();
                    })
                    .findFirst().orElse("");
        } catch (RuntimeException ex) {
            java.util.logging.Logger.getLogger(FileSystemStorageService.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    @Override
    public Resource loadAsResource(Path location, Path resourcesFolder, String filename) {
        try {
            Path file = load(location, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

//    @Override
//    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(rootLocationTemp.toFile());
//    }

//    @Override
//    public void init() {
//        try {
//            File directory = rootLocationTemp.toFile();
//            if (!directory.exists()) {
//                Files.createDirectories(rootLocationTemp);
//            }
//        } catch (IOException e) {
//            throw new StorageException("Could not initialize storage", e);
//        }
//    }
}
