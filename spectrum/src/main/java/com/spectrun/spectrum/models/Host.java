package com.spectrun.spectrum.models;

import jakarta.persistence.*;
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
    @Column(unique = true)
    private String hostname;
    private String sshUsername;
    private String sshPassword;
    private String mode;
    private String jobId;
    private String callBackUrl;

}
