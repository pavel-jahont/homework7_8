package com.gmail.jahont.pavel.app.service;

import java.util.List;

import com.gmail.jahont.pavel.app.service.model.UpdateUserDTO;
import com.gmail.jahont.pavel.app.service.model.UserDTO;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO add(UserDTO userDTO);

    void update(UpdateUserDTO updateUserDTO);

    void deleteUserById(Integer idInt);

}
