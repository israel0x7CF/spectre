package com.spectrun.spectrum.DTO;

import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Subscriptions;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDTO {
    private long id;
    private String company;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Subscriptions subscription;
    private List<Instances> instances;
}
