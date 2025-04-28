package com.spectrun.spectrum.DTO;

import com.spectrun.spectrum.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstanceCreateRequestDto {
    private String instanceName;
//    private String instancedbName;
//    private String instancedbaddress;
//    private String instanceaddress;
//    private String supportedVersion;
//    private Status status;
//    private String configurationFileLocation;
//    private String adminUserName;
//    private String adminPassword;
}
