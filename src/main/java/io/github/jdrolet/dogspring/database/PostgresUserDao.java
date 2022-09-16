package io.github.jdrolet.dogspring.database;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.github.jdrolet.dogspring.model.User;

@Repository("postgresUserDao")
public class PostgresUserDao implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    UserRowMapper mapper = new UserRowMapper();

    @Override
    public boolean insertUser(User user) {
        UUID[] dogIds = new UUID[user.getOwnedDogIds().size()];
        for(int i = 0; i < user.getOwnedDogIds().size(); i++) {
            dogIds[i] = user.getOwnedDogIds().get(i);
        }

        return jdbcTemplate.update("INSERT INTO users (username, displayname, owneddogids, gold, lastdogrequest) VALUES(?,?,?,?,?)",
            new Object[] {user.getUsername(), user.getDisplayName(), dogIds, user.getGold(), user.getLastDogRequest()}) > 0;
    }

    @Override
    public Collection<User> selectAllUsers() {
        return jdbcTemplate.query("SELECT * from users", mapper);
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        return jdbcTemplate.update("DELETE FROM users WHERE username=?", username) > 0;
    }

    @Override
    public boolean updateUserByUsername(String username, User user) {
        UUID[] dogIds = new UUID[user.getOwnedDogIds().size()];
        for(int i = 0; i < user.getOwnedDogIds().size(); i++) {
            dogIds[i] = user.getOwnedDogIds().get(i);
        }

        return jdbcTemplate.update("UPDATE users SET displayname=?, owneddogids=?, gold=?, lastdogrequest=? WHERE username=?",
        new Object[] { user.getDisplayName(), dogIds, user.getGold(), user.getLastDogRequest(), username }) > 0;
    }

    @Override
    public Optional<User> selectUserByUsername(String username) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=?", mapper, username);
      
            return Optional.of(user);
        } 
        catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }


    

    public class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setDisplayName(rs.getString("displayname"));

            Array arr = rs.getArray("owneddogids");
            UUID[] uuids = (UUID[]) arr.getArray();
            user.setOwnedDogIds(new ArrayList<UUID>(Arrays.asList(uuids)));
    
            
            user.setGold(rs.getInt("gold"));
            user.setLastDogRequest(rs.getTimestamp("lastdogrequest").toLocalDateTime());

            return user;
        }
        
    }

}
