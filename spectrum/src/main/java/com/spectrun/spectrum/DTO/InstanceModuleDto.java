package com.spectrun.spectrum.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstanceModuleDto {
    //handle Multiple Module Installation Issue
    ModuleDto installationModule;
    InstanceDto installationInstance;

}
