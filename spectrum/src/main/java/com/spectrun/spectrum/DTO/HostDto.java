package com.spectrun.spectrum.DTO;

import lombok.Data;

@Data
public class HostDto {
    private long id;
    private String hostname;
    private String sshUsername;
    private String sshPassword;
    private String mode;
}
