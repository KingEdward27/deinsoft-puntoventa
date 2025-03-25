package com.deinsoft.puntoventa.facturador.client;

import com.deinsoft.puntoventa.framework.util.GenericRestClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
public class FacturadorHttpClient {
    String url;
    String token;
    public FacturadorHttpClient(String url, String token) {
        this.url = url;
        this.token = token;
    }
    public void registerEmpresa (String jsonBody,File file) {
        try {
            GenericRestClient client = new GenericRestClient();


            // Crear headers
            Map<String, String> headers = new HashMap<>();
            headers.put(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

            // Crear el JSON como String
//            String jsonPayload = "{ \"nombre\": \"Juan\", \"edad\": 30 }";

            // Cargar el archivo
//            File file = new File("C:/ruta/del/archivo.pdf");
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(fileBytes);

            // Construir el cuerpo form-data
            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("empresa", jsonBody); // Parámetro tipo text con JSON
            requestBody.add("file", fileEntity); // Parámetro tipo file

            // Enviar la solicitud POST
            Map<String, Object> response = client.sendRequest(
                    url,
                    HttpMethod.POST,
                    headers,
                    requestBody,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    }
            );

            System.out.println("Respuesta: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
