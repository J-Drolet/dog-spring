package io.github.jdrolet.dogspring.database;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.github.jdrolet.dogspring.model.Dog;

public interface DogDao {
    boolean insertDog(Dog dog);

    Collection<Dog> selectAllDogs();

    boolean deleteDogById(UUID id);

    boolean updateDogById(UUID id, Dog dog);

    Optional<Dog> selectDogById(UUID id);
}
