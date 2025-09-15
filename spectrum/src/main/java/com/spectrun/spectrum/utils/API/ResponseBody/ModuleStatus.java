package com.spectrun.spectrum.utils.API.ResponseBody;


import com.spectrun.spectrum.utils.API.ResponseDTO.InstanceApplicaitonsDTO;

import java.util.List;
import java.util.Objects;

public class ModuleStatus {
    private List<InstanceApplicaitonsDTO> installed;
    private List<InstanceApplicaitonsDTO> uninstalled;

    public ModuleStatus() {
    }

    public ModuleStatus(List<InstanceApplicaitonsDTO> installed, List<InstanceApplicaitonsDTO> uninstalled) {
        this.installed = installed;
        this.uninstalled = uninstalled;
    }

    public List<InstanceApplicaitonsDTO> getInstalled() {
        return installed;
    }

    public void setInstalled(List<InstanceApplicaitonsDTO> installed) {
        this.installed = installed;
    }

    public List<InstanceApplicaitonsDTO> getUninstalled() {
        return uninstalled;
    }

    public void setUninstalled(List<InstanceApplicaitonsDTO> uninstalled) {
        this.uninstalled = uninstalled;
    }

    @Override
    public String toString() {
        return "ModuleStatus{" +
                "installed=" + installed +
                ", uninstalled=" + uninstalled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleStatus that = (ModuleStatus) o;
        return Objects.equals(installed, that.installed) &&
                Objects.equals(uninstalled, that.uninstalled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(installed, uninstalled);
    }
}