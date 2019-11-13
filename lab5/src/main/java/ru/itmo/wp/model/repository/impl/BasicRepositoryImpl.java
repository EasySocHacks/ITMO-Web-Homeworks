package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.BasicRepository;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BasicRepositoryImpl extends DatabaseUtils implements BasicRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    private final String PASSWORD_FIELD_NAME = "passwordSha";

    @Override
    public ResultSet saveObject(String statementString, Object... args) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(statementString, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 1; i <= args.length; i++) {
                    statement.setObject(i, args[i - 1]);
                }

                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save '" + getRepositoryClass().getSimpleName() + "'");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return  generatedKeys;
                    }

                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save '" + getRepositoryClass().getSimpleName() + "'", e);
        }
    }

    @Override
    public Object findObject(String findBy, Object key) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            String statementString = String.format("SELECT * FROM `%s` WHERE %s=?", getRepositoryClass().getSimpleName(), findBy);

            try (PreparedStatement statement = connection.prepareStatement(statementString)) {
                statement.setObject(1, key);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toObject(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(String.format("Can't find %s.", getRepositoryClass().getSimpleName()), e);
        }
    }

    @Override
    public Object findObject(long id) {
        return findObject("id", id);
    }

    @Override
    public Object toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Class<?> repositoryClass = getRepositoryClass();

        Object obj = null;
        try {
            obj = getRepositoryClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RepositoryException("Cannot create new instance of '" + getRepositoryClass().getSimpleName() + "'", e);
        }

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (metaData.getColumnName(i).equals(PASSWORD_FIELD_NAME)) {
                continue;
            }

            Method getter;
            try {
                getter = repositoryClass.getDeclaredMethod("get" + Character.toUpperCase(metaData.getColumnName(i).charAt(0)) + metaData.getColumnName(i).substring(1));
            } catch (NoSuchMethodException e) {
                throw new RepositoryException("Cannot find getter for field '" + metaData.getColumnName(i) + "' in class '" + repositoryClass.getSimpleName() + "'", e);
            }

            Class<?> fieldType = getter.getReturnType();

            Method setter;
            try {
                setter = repositoryClass.getDeclaredMethod("set" + Character.toUpperCase(metaData.getColumnName(i).charAt(0)) + metaData.getColumnName(i).substring(1), fieldType);
            } catch (NoSuchMethodException e) {
                throw new RepositoryException("Cannot find setter for field '" + metaData.getColumnName(i) + "' in class '" + repositoryClass.getSimpleName() + "'", e);
            }

            try {
                if (fieldType == long.class || fieldType == Long.class) {
                    setter.invoke(obj, resultSet.getLong(i));
                }

                if (fieldType == String.class) {
                    setter.invoke(obj, resultSet.getString(i));
                }

                if (fieldType == Date.class) {
                    setter.invoke(obj, resultSet.getTimestamp(i));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RepositoryException("Cannot invoke method '" + setter.getName() + "' in class '" + repositoryClass.getSimpleName() + "'", e);
            }
        }

        return obj;
    }

    public List<Object> findAllObjects(String query, Object... args) {
        List<Object> objs = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (int i = 1; i <= args.length; i++) {
                    statement.setObject(i, args[i - 1]);
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    Object obj;
                    while ((obj = toObject(statement.getMetaData(), resultSet)) != null) {
                        objs.add(obj);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + getRepositoryClass().getSimpleName(), e);
        }
        return objs;
    }

    public int findCount(String query) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return (int) resultSet.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + getRepositoryClass().getSimpleName(), e);
        }

        return 0;
    }
}
