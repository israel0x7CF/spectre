package com.spectrun.spectrum.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UsageLimits {
    private String maxStorage;
    private String usedStorage;
    private Integer instanceLimit;
}
