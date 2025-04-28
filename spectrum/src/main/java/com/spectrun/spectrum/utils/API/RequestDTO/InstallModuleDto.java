package com.spectrun.spectrum.utils.API.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstallModuleDto {

    @JsonProperty("user")
    private String user;

    @JsonProperty("password")
    private String password;

    @JsonProperty("host")
    private String host;

    @JsonProperty("port")
    private String port;

    @JsonProperty("db")
    private String db;

    @JsonProperty("module")
    private String module;
    private boolean isActive;

    // Default constructor
    public InstallModuleDto() {}

    // Getters and Setters
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getPort() { return port; }
    public void setPort(String port) { this.port = port; }

    public String getDb() { return db; }
    public void setDb(String db) { this.db = db; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
