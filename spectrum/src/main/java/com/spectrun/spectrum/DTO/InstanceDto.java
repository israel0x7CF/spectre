package com.spectrun.spectrum.DTO;

import com.spectrun.spectrum.Enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
public class InstanceDto {
    private Long id;
    private String instanceName; // Changed from InstanceName to instanceName
    private String instancedbName;
    private String instancedbaddress;
    private String instanceaddress;
    private String supportedVersion;
    private Status status;
    private String configurationFileLocation;
    private String adminUserName;
    private String adminPassword;
    private long userId;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
