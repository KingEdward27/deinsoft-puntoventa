/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.common.io.Files;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *
 * @author user
 */
public class Util {

    public static byte[] generateFile(String fileName, String content) {
        try {
            File tempDir = Files.createTempDir();
            File file = Paths.get(tempDir.getAbsolutePath(), fileName).toFile();
            try ( FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.append(content).flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return toByteArray(new FileInputStream(file));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[in.available()];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }

    
    
    
}
