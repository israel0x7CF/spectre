package com.spectrun.spectrum.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String hostname;
    private String sshUsername;
    private String sshPassword;
    private String mode;

}
