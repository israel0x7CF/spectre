package com.spectrun.spectrum.utils.API.RequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RemoteInstanceModule {

    @JsonProperty("")
    String username;
    String Password;
    int port;
    String dbName;
    String address;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return Password;
    }

    public int getPort() {
        return port;
    }

    public String getDbName() {
        return  dbName;

    }

    public String getAddress() {
        return address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
