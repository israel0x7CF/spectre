package com.spectrun.spectrum.config.Storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {
    private String location = "/opt/modules";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
