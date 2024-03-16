/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.framework.util;

import static com.deinsoft.puntoventa.util.Util.toByteArray;
import com.google.common.io.Files;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author EDWARD-PC
 */
public class Util {

    public static BigDecimal getBigDecimalValue(Map<String, Object> map, String key) {
        if (map.get(key) == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(map.get(key).toString());
    }

    public static String getStringValue(Map<String, Object> map, String key) {
        if (map.get(key) == null) {
            return null;
        }
        return map.get(key).toString();
    }

    public static boolean isNullOrEmpty(Object o) {
        try {
            if (o == null) {
                return true;
            }
            if (String.valueOf(o).equals("")) {
                return true;
            }
            return false;
        } catch (NullPointerException e) {
            return true;
        }

    }

    public static Map<String, Object> toMap(Object object, String[] visibles) {
        Map<String, Object> map = new HashMap<>();
        try {

            for (Field f : object.getClass().getDeclaredFields()) {
//            System.out.println(f.toString());
//                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getType());
                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getModifiers());
                if (visibles == null) {
                    if (f.toString().contains("final") || f.toString().contains("list")) {
                        continue;
                    }
                } else {
                    if (f.toString().contains("final") || f.toString().contains("list")
                            || !Arrays.asList(visibles).contains(f.getName())) {
                        continue;
                    }
                }

                map.put(f.getName(), f.get(object));

//                objectBuilder.add(f.getName(), f.get(facturaElectronica).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static byte[] OutputStreamToByteArray(OutputStream myOutputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeTo(myOutputStream);
        byte[] x = baos.toByteArray();
        return x;
    }

    public Map<String, Object> simpleGet(HttpMethod httpMethod, String url, String token, Map<String, String> params) throws Exception {
        Map<String, Object> respuesta = null;
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", token);
        headers.add("Content-Type", "application/json");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("numero", "{numero}")
                .encode()
                .toUriString();

        HttpEntity<MultiValueMap<String, String>> entityReq = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = getRestTemplate().exchange(urlTemplate,
                httpMethod, entityReq,
                Map.class, params);
        if (response.hasBody() && (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED)) {
            respuesta = response.getBody();
            if (respuesta != null) {
                return respuesta;
            } else {
                String msg = "Respuesta vacía del API";
                throw new Exception(msg);
            }
        } else {
            String msg = "Llamada fallida al API, HttpStatus: " + response.getStatusCode().value();
//            this.logger.warning(msg);
            throw new Exception(msg);
        }
    }

    private RestTemplate getRestTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {

            @Override
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        };
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectionRequestTimeout(5000);
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(10000);
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }

    public static byte[] comprimirArchivo(InputStream entrada, String nombre) throws Exception {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        ZipEntry ze = new ZipEntry(nombre);
        zos.putNextEntry(ze);

        int len;
        while ((len = entrada.read(buffer)) > 0) {
            zos.write(buffer, 0, len);
        }

        entrada.close();
        zos.closeEntry();

        zos.close();
        return bos.toByteArray();
    }
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
}
