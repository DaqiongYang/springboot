package com.github.xuybin.springboot.model;

import java.io.Serializable;

public class Token implements Serializable {
    private String accessToken = null;

    private String tokenType = null;

    private Long expiresIn = null;

    private Long issuedAt = null;


    public Token accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public Token tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }




    public Token expiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }


    public Token issuedAt(Long issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public Long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Long issuedAt) {
        this.issuedAt = issuedAt;
    }
}
