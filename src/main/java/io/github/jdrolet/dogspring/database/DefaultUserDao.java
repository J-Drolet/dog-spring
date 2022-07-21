package io.github.jdrolet.dogspring.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.github.jdrolet.dogspring.model.User;

@Repository("fakeUserDao")
public class DefaultUserDao implements UserDao {

    public static Map<String, User> DB = new HashMap<String, User>();

    @Override
    public boolean insertUser(User user) {
        if(DB.containsKey(user.getUsername()))
            return false;
        
        DB.put(user.getUsername(), user);
        return true;
    }

    @Override
    public Collection<User> selectAllUsers() {
        return DB.values();
    }

    @Override
    public boolean deleteUserByUsername(String username) {    
        return DB.remove(username) != null;
    }

    @Override
    public boolean updateUserByUsername(String username, User user) {
        Optional<User> optionalUser = selectUserByUsername(username);
        if(optionalUser.isEmpty())
            return false;
        
            DB.replace(username, user);
        return true;
    }

    @Override
    public Optional<User> selectUserByUsername(String username) {
        return DB.containsKey(username) ? Optional.of(DB.get(username)) : Optional.empty();
    }
}
