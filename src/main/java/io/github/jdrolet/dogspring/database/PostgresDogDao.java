package io.github.jdrolet.dogspring.database;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import io.github.jdrolet.dogspring.model.Dog;

@Repository("postgresDogDao")
public class PostgresDogDao implements DogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insertDog(Dog dog) {
        return jdbcTemplate.update("INSERT INTO dogs (id, name, happiness, energy, hunger, imgLink, birthdate, ownerusername) VALUES(?,?,?,?,?,?,?,?)",
            new Object[] { dog.getId(), dog.getName(), dog.getHappiness(), dog.getEnergy(), dog.getHunger(), dog.getImgLink(), dog.getBirthdate(), dog.getOwnerUsername() }) > 0;
    }

    @Override
    public Collection<Dog> selectAllDogs() {
        return jdbcTemplate.query("SELECT * from dogs", BeanPropertyRowMapper.newInstance(Dog.class));
    }

    @Override
    public boolean deleteDogById(UUID id) {
        return jdbcTemplate.update("DELETE FROM dogs WHERE id=?", id) > 0;
    }

    @Override
    public boolean updateDogById(UUID id, Dog dog) {
        return jdbcTemplate.update("UPDATE dogs SET name=?, happiness=?, energy=?, hunger=?, imgLink=?, bithdate=?, ownerusername=? WHERE id=?",
        new Object[] { dog.getName(), dog.getHappiness(), dog.getEnergy(), dog.getHunger(), dog.getImgLink(), dog.getBirthdate(), dog.getOwnerUsername(), id }) > 0;
    }

    @Override
    public Optional<Dog> selectDogById(UUID id) {
        try {
            Dog dog = jdbcTemplate.queryForObject("SELECT * FROM dogs WHERE id=?",
                BeanPropertyRowMapper.newInstance(Dog.class), id);
      
            return Optional.of(dog);
        } 
        catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }
}