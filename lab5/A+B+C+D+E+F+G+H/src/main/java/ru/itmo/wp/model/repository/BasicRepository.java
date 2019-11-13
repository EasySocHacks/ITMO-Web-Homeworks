package ru.itmo.wp.model.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public interface BasicRepository {
    Class<?> getRepositoryClass();

    ResultSet saveObject(String statementString, Object... args);
    Object findObject(String findBy, Object key);
    Object findObject(long id);
    Object toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
    List<Object> findAllObjects(String query, Object... args);
    int findCount(String query);
}
