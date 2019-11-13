package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;

import java.util.List;

public interface UserRepository extends  BasicRepository {
    User find(long id);
    User findByLogin(String login);
    User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) throws ValidationException;
    List<User> findAll();
    void save(User user, String passwordSha);
    int findCount();
}
