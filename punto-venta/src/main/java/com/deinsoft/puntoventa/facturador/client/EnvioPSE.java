/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.facturador.client;

//import accesodatos.ConfiguracionADN;
//import Adicional.Util;
//import accesodatos.ParametrosADN;
//import entidades.ConsultaVentas;
//import entidades.ConsultaVentas2;
//import entidades.Detalle_Venta;
//import entidades.Formatos;
//import facturacionelectronica.util.Constantes;
import com.deinsoft.puntoventa.framework.util.Util;
import com.deinsoft.puntoventa.framework.util.Formatos;
import java.lang.reflect.Field;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.deinsoft.puntoventa.util.*;
import java.net.ConnectException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.util.UriComponentsBuilder;
/**
 *
 * @author EDWARD
 */
public class EnvioPSE {

    private String url = "";
    private String token = "";

    public EnvioPSE(String url,String token){
        this.url = url;
        this.token = token;
    }
    public String paramToJson( Map<String,Object> datosVenta) {
        try {
            Map<String,Object> cliente = (Map<String,Object>)datosVenta.get("cnf_maestro");
            Map<String,Object> tipoDocId = (Map<String,Object>)cliente.get("cnf_tipo_documento");
            //Map<String,Object> numComprobante = (Map<String,Object>)datosVenta.get("cnf_num_comprobante");
            Map<String,Object> tipoDoc = (Map<String,Object>)datosVenta.get("cnf_tipo_comprobante");
            Map<String,Object> formaPago = (Map<String,Object>)datosVenta.get("cnf_forma_pago");
            Map<String,Object> moneda = (Map<String,Object>)datosVenta.get("cnf_moneda");
            List<Map<String,Object>> listDetail = (List<Map<String,Object>>)datosVenta.get("list_act_comprobante_detalle");
            Cabecera servicio = new Cabecera(); 
            String tipoDocSunat = tipoDoc.get("codigo_sunat").toString();
            servicio.setTipo(tipoDocSunat);
            servicio.setSerie(datosVenta.get("serie").toString());
            servicio.setNumero(String.valueOf(datosVenta.get("numero")));
            servicio.setForma_pago(formaPago.get("nombre").toString());
            servicio.setFecha_emision(Formatos.sdfFecha.format(datosVenta.get("fecha")));
            servicio.setFecha_vencimiento(datosVenta.get("fecha_vencimiento") == null?
                    "":Formatos.sdfFecha.format(datosVenta.get("fecha_vencimiento")));
            servicio.setTipo_operacion(Constantes.TIPO_OPERACION_WS);
            servicio.setCliente_tipo(tipoDocId.get("codigo_sunat").toString());
            servicio.setCliente_documento(cliente.get("nro_doc").toString());
            servicio.setCliente_nombre(cliente.get("apellido_paterno").toString() + " " + 
                                        cliente.get("apellido_materno").toString() + " " +
                                        cliente.get("nombres").toString());
            servicio.setCliente_direccion(cliente.get("direccion").toString());
            servicio.setCliente_email(cliente.get("correo").toString());
            servicio.setCliente_telefono(null);
            servicio.setVendedor_nombre("admin");
            servicio.setObservaciones(null);
            servicio.setPlaca_vehiculo(null);
            servicio.setOrden_compra(null);
            servicio.setGuia_remision(null);
            servicio.setDescuento_global_porcentaje(Formatos.df.format(0));
            servicio.setMoneda(moneda.get("codigo").toString());
            servicio.setNota_tipo(null);
            servicio.setNota_motivo(null);
            servicio.setNota_referencia_tipo(null);
            servicio.setNota_referencia_serie(null);
            servicio.setNota_referencia_numero(null);
            servicio.setSerie_ref("");
            servicio.setNumero_ref("");
            servicio.setMonto_ref(Formatos.df.format(0));
            servicio.setFecha_ref("");
//            servicio.setIncluir_pdf(ConfiguracionADN.Datos().get(0).getFlagPDF().equals("1")?"true":"false");
//            servicio.setIncluir_xml(ConfiguracionADN.Datos().get(0).getFlagXML().equals("1")?"true":"false");
            List<Detalle> listaItemsEnvio = new ArrayList<Detalle>();
            int count = 0;
            BigDecimal sumTributo = BigDecimal.ZERO,sumBaseImponible = BigDecimal.ZERO;
            for (Map<String, Object> detalleVenta : listDetail) {
                Map<String,Object> producto = (Map<String,Object>)detalleVenta.get("cnf_producto");
                Map<String,Object> um = (Map<String,Object>)producto.get("cnf_unidad_medida");
                Map<String,Object> impuesto = (Map<String,Object>)detalleVenta.get("cnf_impuesto_condicion");
//                if((float)detalleVenta.get("precio") == 0){
//                    continue;
//                }
                Detalle items = new Detalle();
                items.setCodigo(producto.get("codigo").toString());
                items.setDescripcion(producto.get("nombre").toString());
                items.setDetalle_adicional(null);
                items.setUnidad_medida(um.get("nombre").toString());
                items.setCantidad(Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"cantidad")));
                items.setPrecio_unitario(Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"precio")));
                items.setDescuento_porcentaje(Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"descuento")));
                items.setTipo_igv(impuesto.get("codigo_sunat").toString());
                items.setAfectacion_igv(Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"afectacion_igv")));
                items.setMonto_referencial_unitario(Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"precio")==BigDecimal.ZERO?
                        Util.getBigDecimalValue(producto,"precio"):BigDecimal.ZERO));
                items.setCod_tributo_igv("1000");
                listaItemsEnvio.add(items);
                count++;
                
                sumTributo = sumTributo.add(new BigDecimal(items.getAfectacion_igv()));
                sumBaseImponible = sumBaseImponible.add(
                        Util.getBigDecimalValue(detalleVenta,"precio").multiply(
                                Util.getBigDecimalValue(detalleVenta,"cantidad")) 
                        .subtract(Util.getBigDecimalValue(detalleVenta,"afectacion_igv")));
            }
            List<Tributo> listaItemsTax = new ArrayList<Tributo>();
            Tributo tax = new Tributo();
            tax.setIde_tributo("1000");
            tax.setNom_tributo("IGV");
            tax.setCod_tip_tributo("VAT");
            tax.setMto_base_imponible(Formatos.df.format(sumBaseImponible));
            tax.setMto_tributo(Formatos.df.format(sumTributo));
            listaItemsTax.add(tax);
            
            servicio.setLista(listaItemsEnvio);
            servicio.setListaTax(listaItemsTax);
            String jsonBody;
            try {
                jsonBody = servicio.toJson(servicio);
                return jsonBody;
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(EnvioPSE.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            } catch (IllegalAccessException ex) {
                Logger.getLogger(EnvioPSE.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }
        } catch (Exception ex) {
            Logger.getLogger(EnvioPSE.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }

    }
//    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//        for(Map.Entry<String, String> entry : params.entrySet()){
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//        }
//
//        return result.toString();
//    }
    public Map<String,Object> getPDF(long id,int tipo) throws Exception  {
        Map<String,Object> respuesta = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", token);
        headers.add("Content-Type", "application/json");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("id", "{id}")
        .queryParam("tipo", "{tipo}")
        .encode()
        .toUriString();
        
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("tipo", String.valueOf(tipo));

        HttpEntity<MultiValueMap<String, String>> entityReq = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = getRestTemplate().exchange(urlTemplate, 
                HttpMethod.POST, entityReq,
                Map.class,params);
        if (response.hasBody() && (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED)) {
            respuesta = response.getBody();
            if (respuesta != null) {
//                this.logger
//                        .info("TokenResponse -> " + " - TokenType: " + tokenResponse.getTokenType() + " - ConsentedOn: "
//                                + tokenResponse.getConsentedOn() + " - ExpiresIn: " + tokenResponse.getExpiresIn()
//                                + " - RefreshTokenExpiresin: " + tokenResponse.getRefreshTokenExpiresIn());
                return respuesta;
            } else {
                String msg = "Respuesta vacía del API";
//                this.logger.warning(msg);
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
//    public Map<String,Object> getPDF(String id,int tipo) {
//        boolean result = false;
//        Map<String,Object> respuesta = null;
//        System.out.println("ticket: "+id);
//        try {
//            HashMap<String,String> map = new HashMap<>();
//            map.put("id", id);
//            map.put("tipo", String.valueOf(tipo));
//            URL url = new URL(this.url);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Accept", "application/json");
//            conn.setRequestProperty("Authorization", this.token);
//            conn.setRequestProperty("Content-Type", "application/json");
//            //conn.setRequestProperty("Content-Length", String.valueOf(getPostDataString(map).length));
//            //conn.getOutputStream().write(getPostDataString(map));
//            
//
//            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//            writer.write(getPostDataString(map));
//            writer.flush();
//            
//            
//            conn.connect();
//            BufferedReader br = null;
//            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
//                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//                result = true;
//            } else {
//                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
//                result = false;
//            }
//            String output, jsonString = "";
//            System.out.println("output is-----------------");
//
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//                jsonString = jsonString + output;
//            }
//            respuesta = new Gson().fromJson(
//                jsonString, new TypeToken<HashMap<String, Object>>() {}.getType()
//            );
//            writer.close();
//            //respuesta = new RespuestaPSE(jsonString, result);
//
//            return respuesta;
//        } catch (IOException e) {
////            System.out.println(Util.exceptionToString(e));
//            e.printStackTrace();
//            return respuesta;
//        } catch (Exception e) {
////            System.out.println(Util.exceptionToString(e));
//            e.printStackTrace();
//            return respuesta;
//        }
//    }
    public RespuestaPSE envioJsonPSE(String jsonBody) {
        boolean result = false;
        RespuestaPSE respuesta = null;
        System.out.println("jsonBody: "+jsonBody);
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", this.token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            writer.write(jsonBody);
            writer.close();

            BufferedReader br = null;
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                result = true;
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                result = false;
            }
            String output, jsonString = "";
            System.out.println("output is-----------------");

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                jsonString = jsonString + output;
            }
            respuesta = new RespuestaPSE(jsonString, result);

            return respuesta;
        } catch (ConnectException e) {
//            System.out.println(Util.exceptionToString(e));
            e.printStackTrace();
            return new RespuestaPSE("003", "Error de conexión: "+e.getMessage());
        }catch (IOException e) {
//            System.out.println(Util.exceptionToString(e));
            e.printStackTrace();
            return new RespuestaPSE("003", "Error en lectura de archivos: "+e.getMessage());
        }catch (Exception e) {
//            System.out.println(Util.exceptionToString(e));
            e.printStackTrace();
           return new RespuestaPSE("003", "Error inesperado: "+e.getMessage());
        }
    }
}
