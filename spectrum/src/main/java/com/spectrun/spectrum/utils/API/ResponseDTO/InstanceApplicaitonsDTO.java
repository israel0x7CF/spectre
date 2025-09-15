package com.spectrun.spectrum.utils.API.ResponseDTO;
import java.util.List;
import java.util.Objects;



public class InstanceApplicaitonsDTO {
    private int id;
    private String name;
    private String state;

    public InstanceApplicaitonsDTO() {
    }

    public InstanceApplicaitonsDTO(int id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceApplicaitonsDTO module = (InstanceApplicaitonsDTO) o;
        return id == module.id &&
                Objects.equals(name, module.name) &&
                Objects.equals(state, module.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, state);
    }
}

