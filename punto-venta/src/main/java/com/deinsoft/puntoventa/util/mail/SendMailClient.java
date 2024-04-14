/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.util.mail;

import com.deinsoft.puntoventa.framework.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpMethod;

/**
 *
 * @author user
 */
public class SendMailClient {

    //get token
    private EmailRequest emailRequest;
    private final static ObjectWriter OW = new ObjectMapper().writer().withDefaultPrettyPrinter();
    
    public SendMailClient(EmailRequest emailRequest) {
        this.emailRequest = emailRequest;
    }

    public EmailResponse send() throws JsonProcessingException {
        
        //get token
        String credentialJsonRequest = OW.writeValueAsString(
                new CredentialRequest("client_credentials", "cc168c6d8777af26d675678d3663f73e", "e279c6cacc84765ceed714dc1cf87704")
        );

        String credentialJsonResponse = Util.simpleApi("https://api.sendpulse.com/oauth/access_token",
                credentialJsonRequest, HttpMethod.POST,
                "", "application/json", "application/json");

        ObjectMapper mapper = new ObjectMapper();
        CredentialResponse credentialResponse = mapper.readValue(credentialJsonResponse, CredentialResponse.class);

        //prepare for send email
        String json = OW.writeValueAsString(
                new EmailRequestRoot(
                        new EmailRequest(emailRequest.getHtml(), "", "Recuperación de contraseña", emailRequest.getFrom(),
                        emailRequest.getTo(), emailRequest.getAttachments_binary())
                )
                
        );
        String mailJsonResult = Util.simpleApi("https://api.sendpulse.com/smtp/emails", json, HttpMethod.POST,
                "Bearer " + credentialResponse.getAccesToken(), "application/json", "application/json");

        return mapper.readValue(mailJsonResult, EmailResponse.class);
    }

}
