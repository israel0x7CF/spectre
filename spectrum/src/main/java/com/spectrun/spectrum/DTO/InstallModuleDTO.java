package com.spectrun.spectrum.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//private String user;
//private String password;
//private String host;
//private Integer port;
//private String db;
//private String module;
@Builder
@ToString
@Getter
@Setter
public class InstallModuleDTO {
    private long Instance_id;
    private long module_id;


}

