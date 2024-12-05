package com.spectrun.spectrum.utils.API.ResponseDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class moduleInstallResponseDTO {
    private String user;
    private String password;
    private String host;
    private String port;
    private String db;
    private String module;
}
