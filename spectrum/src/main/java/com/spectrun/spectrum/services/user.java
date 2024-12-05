package com.spectrun.spectrum.services;

import com.spectrun.spectrum.DTO.InstanceDto;
import com.spectrun.spectrum.DTO.UserDTO;
import com.spectrun.spectrum.models.Instances;
import com.spectrun.spectrum.models.Users;

import java.util.List;

public interface user {
    public List<UserDTO> getUsers();
    public UserDTO  getUser(long UserId);
    public UserDTO getUserByEmail(String email);
    public UserDTO addSubscriptionToUserAccount(long Userid,long SubscriptionId);
}
