package com.example.demo.Dati;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StorageNodeRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String ip;

    @NotNull
    private Integer port;

    @NotBlank
    private String token;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
