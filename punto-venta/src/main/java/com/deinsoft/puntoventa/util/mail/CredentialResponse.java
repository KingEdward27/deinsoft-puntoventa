/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.util.mail;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author user
 */
public class CredentialResponse {
    
    @JsonProperty("token_type") 
    private String tokenType;
    
    @JsonProperty("expires_in") 
    private Integer expiresIn;
    
    @JsonProperty("access_token") 
    private String accesToken;

    public CredentialResponse() {
    }

    public CredentialResponse(Integer expiresIn, String accesToken) {
        this.expiresIn = expiresIn;
        this.accesToken = accesToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    
    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }
    
    
}