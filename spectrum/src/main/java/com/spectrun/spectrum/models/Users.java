package com.spectrun.spectrum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="Users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String company;
    private String email;
    private String phone;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id",referencedColumnName = "id")
    @JsonIgnore
    private Subscriptions subscription;

    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Instances> instances;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();  // Return authorities if you have any roles
    }

    @Override
    public String getUsername() {
        return this.email;  // Use email as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Customize if necessary
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Customize if necessary
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Customize if necessary
    }

    @Override
    public boolean isEnabled() {
        return true;  // Return true for active users
    }
}
