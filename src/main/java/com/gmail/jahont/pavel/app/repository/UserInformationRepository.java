package com.gmail.jahont.pavel.app.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.jahont.pavel.app.repository.model.UserInformation;

public interface UserInformationRepository extends GeneralRepository<UserInformation> {

    void update(Connection connection, UserInformation userInformation) throws SQLException;

}
