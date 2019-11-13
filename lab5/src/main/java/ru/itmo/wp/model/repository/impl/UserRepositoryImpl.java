package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends BasicRepositoryImpl implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public Class<?> getRepositoryClass() {
        return User.class;
    }

    @Override
    public User find(long id) {
        return (User) findObject(id);
    }

    @Override
    public User findByLogin(String login) {
        return (User) findObject("login", login);
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) throws ValidationException {
        String columnType;

        if (loginOrEmail.length() - loginOrEmail.replaceAll("@", "").length() == 1) {
            columnType = "email";
        } else {
            columnType = "login";
        }

        List<Object> result = findAllObjects(String.format("SELECT * FROM User WHERE %s=? AND passwordSha=?", columnType), loginOrEmail, passwordSha);

        if (result.isEmpty()) {
            throw new ValidationException("Invalid login or password");
        } else {
            return (User)result.get(0);
        }
    }

    @Override
    public List<User> findAll() {
        List<Object> objectsArray = findAllObjects("SELECT * FROM User ORDER BY id DESC");
        List<User> userArray = new ArrayList<>();
        for (Object obj : objectsArray) {
            userArray.add((User) obj);
        }

        return  userArray;
    }

    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return (User) toObject(metaData, resultSet);
    }

    @Override
    public void save(User user, String passwordSha) {
        ResultSet resultSet = saveObject("INSERT INTO `User` (`login`, `passwordSha`, `creationTime`, `email`) VALUES (?, ?, NOW(), ?)",
                user.getLogin(),
                passwordSha,
                user.getEmail());

        try {
            user.setId(resultSet.getLong(1));
            user.setCreationTime(find(user.getId()).getCreationTime());
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User", e);
        }
    }

    @Override
    public int findCount() {
        return findCount("SELECT COUNT(id) FROM `User`");
    }
}
