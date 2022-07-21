package io.github.jdrolet.dogspring.database;

import java.util.Collection;
import java.util.Optional;

import io.github.jdrolet.dogspring.model.User;

public interface UserDao {

    boolean insertUser(User user);

    Collection<User> selectAllUsers();

    boolean deleteUserByUsername(String username);

    boolean updateUserByUsername(String username, User user);

    Optional<User> selectUserByUsername(String username);
}
