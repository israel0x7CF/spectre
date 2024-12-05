package com.spectrun.spectrum.models;

import com.spectrun.spectrum.Enums.Status;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="instances")
@ToString()
public class Instances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String instanceName; // Changed from InstanceName to instanceName
    private String instancedbName;
    private String instancedbaddress;
    private String instanceaddress;
    private String supportedVersion;
    private Status status;
    private String configurationFileLocation;
    private String adminUserName;
    private String adminPassword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user; // An instance belongs to one user
    @Nullable
    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<installModules> installModules;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
