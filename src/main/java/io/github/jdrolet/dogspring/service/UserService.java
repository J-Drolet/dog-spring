package io.github.jdrolet.dogspring.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.jdrolet.dogspring.database.UserDao;
import io.github.jdrolet.dogspring.model.Dog;
import io.github.jdrolet.dogspring.model.User;

@Service
public class UserService {
    private final UserDao userDao;
    private final DogService dogService;
    private static final Duration timeBeweenBirths = Duration.ofSeconds(1);

    @Autowired
    public UserService(@Qualifier("fakeUserDao") UserDao userDao, DogService dogService) {
        this.userDao = userDao;
        this.dogService = dogService;
    }
    
    public boolean addUser(User user) {
        return userDao.insertUser(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(userDao.selectAllUsers());
    }

    public Optional<User> getUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }

    public boolean deleteUser(String username) {
        return userDao.deleteUserByUsername(username);
    }

    public boolean updateUser(String username, User newUser) {
        return userDao.updateUserByUsername(username, newUser);
    }

    public List<Dog> getAllDogsForUsername(String username) {
        List<Dog> dogs = new ArrayList<>();

        Optional<User> user = getUserByUsername(username);
        if(!user.isPresent())
            return null;

        for (UUID dogId : user.get().getOwnedDogIds()) {
            Optional<Dog> foundDog = dogService.getDogById(dogId);
            if(foundDog.isPresent())
                dogs.add(foundDog.get());
        }
        return dogs;
    }

    public boolean generateDogForUsername(String username, String dogName) {

        Optional<User> obtainedUser = getUserByUsername(username);
        if(!obtainedUser.isPresent())
            return false;
        
        User user = obtainedUser.get();

        if(Duration.between(user.getLastDogRequest(), LocalDateTime.now()).compareTo(timeBeweenBirths) < 0)
            return false;
        
        Dog generated = dogService.generateDog(dogName, username);
        if(generated == null)
            return false;
        
        user.addDogId(generated.getId());
        user.updateLastDogRequest();
        updateUser(username, user); // update in database
        return true;
    }
}
