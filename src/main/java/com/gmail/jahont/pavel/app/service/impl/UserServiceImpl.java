package com.gmail.jahont.pavel.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.jahont.pavel.app.repository.ConnectionRepository;
import com.gmail.jahont.pavel.app.repository.UserInformationRepository;
import com.gmail.jahont.pavel.app.repository.UserRepository;
import com.gmail.jahont.pavel.app.repository.impl.ConnectionRepositoryImpl;
import com.gmail.jahont.pavel.app.repository.impl.UserInformationRepositoryImpl;
import com.gmail.jahont.pavel.app.repository.impl.UserRepositoryImpl;
import com.gmail.jahont.pavel.app.repository.model.User;
import com.gmail.jahont.pavel.app.repository.model.UserInformation;
import com.gmail.jahont.pavel.app.service.UserService;
import com.gmail.jahont.pavel.app.service.model.UpdateUserDTO;
import com.gmail.jahont.pavel.app.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserService instance;
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private UserInformationRepository userInformationRepository = UserInformationRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<UserDTO> userDTOs = new ArrayList<>();
                List<User> users = userRepository.findAll(connection);
                for (User u : users) {
                    UserDTO userDTO = convertDatabaseUserToDTO(u);
                    userDTOs.add(userDTO);
                }
                connection.commit();
                return userDTOs;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User dbUser = convertDTOToDatabaseUser(userDTO);
                User addedUser = userRepository.add(connection, dbUser);
                dbUser.getUserInformation().setUserId(addedUser.getId());
                userInformationRepository.add(connection, dbUser.getUserInformation());
                connection.commit();
                return convertDatabaseUserToDTO(addedUser);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void update(UpdateUserDTO updateUserDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                UserInformation databaseUserInformation = convertDTOToDbUpdateUserInformation(updateUserDTO);
                userInformationRepository.update(connection, databaseUserInformation);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteUserById(Integer idInt) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userInformationRepository.deleteById(connection, idInt);
                userRepository.deleteById(connection, idInt);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private UserDTO convertDatabaseUserToDTO(User dbUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(dbUser.getId());
        userDTO.setUserName(dbUser.getUsername());
        userDTO.setPassword(dbUser.getPassword());
        userDTO.setActive(dbUser.getActive());
        userDTO.setAge(dbUser.getAge());
        userDTO.setAddress(dbUser.getUserInformation().getAddress());
        userDTO.setTelephone(dbUser.getUserInformation().getTelephone());
        return userDTO;
    }

    private User convertDTOToDatabaseUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setActive(userDTO.isActive());
        user.setAge(userDTO.getAge());
        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(userDTO.getAddress());
        userInformation.setTelephone(userDTO.getTelephone());
        user.setUserInformation(userInformation);
        return user;
    }

    private UserInformation convertDTOToDbUpdateUserInformation(UpdateUserDTO updateUserDTO) {
        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(updateUserDTO.getAddress());
        userInformation.setUserId(updateUserDTO.getId());
        return userInformation;
    }
}