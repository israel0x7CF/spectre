package com.spectrun.spectrum.services;

import com.spectrun.spectrum.models.installModules;

import java.util.List;

public interface IInstallModule {
    public List<installModules> getInstalledModules(long instanceId);
    public installModules installNewModule(long moduleId,long instanceId);
    public installModules unInstallModules(long moduleId);
}
