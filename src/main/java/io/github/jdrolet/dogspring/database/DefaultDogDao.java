package io.github.jdrolet.dogspring.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import io.github.jdrolet.dogspring.model.Dog;

@Repository("fakeDogDao")
public class DefaultDogDao implements DogDao {
    public static Map<UUID, Dog> DB = new HashMap<>();

    @Override
    public boolean insertDog(Dog dog) {
        if(DB.containsKey(dog.getId()))
            return false;
        
        DB.put(dog.getId(), dog);
        return true;
    }

    @Override
    public Collection<Dog> selectAllDogs() {
        return DB.values();
    }

    @Override
    public boolean deleteDogById(UUID id) {    
        return DB.remove(id) != null;
    }

    @Override
    public boolean updateDogById(UUID id, Dog dog) {
        Optional<Dog> optionalUser = selectDogById(id);
        if(optionalUser.isEmpty())
            return false;
        
            DB.replace(id, dog);
        return true;
    }

    @Override
    public Optional<Dog> selectDogById(UUID id) {
        return DB.containsKey(id) ? Optional.of(DB.get(id)) : Optional.empty();
    }
}
