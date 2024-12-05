package com.spectrun.spectrum.MessageTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createInstanceResponse {
    private String instanceName;
    private Boolean instanceCreationStatus;
    private String instanceAddress;

    @JsonProperty("instanceDbAdress")
    private String instanceDbAddress;

    @JsonProperty("db_name")
    private String instanceDbName;

    private String configurationFileLocation;

    @JsonProperty("custom_addons_path")
    private String customAddonsPath;

    @JsonProperty("adminPassword")
    private String adminPassword;

    @JsonProperty("adminUserName")
    private String adminUserName;

    // Default constructor
    public createInstanceResponse() {}

    @Override
    public String toString() {
        return "createInstanceResponse{" +
                "instanceName='" + instanceName + '\'' +
                ", instanceCreationStatus=" + instanceCreationStatus +
                ", instanceAddress='" + instanceAddress + '\'' +
                ", instanceDbAddress='" + instanceDbAddress + '\'' +
                ", instanceDbName='" + instanceDbName + '\'' +
                ", configurationFileLocation='" + configurationFileLocation + '\'' +
                ", customAddonsPath='" + customAddonsPath + '\'' +
                ", adminUserName='" + adminUserName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
