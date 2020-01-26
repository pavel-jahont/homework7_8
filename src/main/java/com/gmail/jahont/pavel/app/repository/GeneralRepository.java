package com.gmail.jahont.pavel.app.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {

    T add(Connection connection, T t) throws SQLException;

    void deleteById(Connection connection, Serializable id) throws SQLException;

}
