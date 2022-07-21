package io.github.jdrolet.dogspring.controller;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.jdrolet.dogspring.model.Dog;
import io.github.jdrolet.dogspring.model.User;
import io.github.jdrolet.dogspring.service.UserService;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Main API for accessing users.
 */
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    
    /** 
     * Add a user to the user database
     * @param user - User to be added
     * @return boolean whether or not the user was successfully added
     */
    @PostMapping
    public boolean addUser(@NonNull @RequestBody User user) {
        return userService.addUser(user);
    }

    
    /** 
     * Get all the users in the user database
     * @return List<User> of all users currently in the user database
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    
    /** 
     * Finds the user with the requested username
     * @param username - username of the requested user
     * @return User that has the username. Returns null if no user with the username exists
     */
    @GetMapping(path = "{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username).orElse(null);
    }

    
    /** 
     * Delete a user from the database
     * @param username - username of the user to be deleted
     */
    @DeleteMapping(path = "{username}")
    public void deleteUserByUsername(@PathVariable("username") String username) {
        userService.deleteUser(username);
    }

    
    /** 
     * Used to update a user in the database
     * @param username - username of the user to change
     * @param userToUpdate is the new user that will be associated with the username
     */
    @PutMapping(path = "{username}")
    public void updateUserByUsername(@PathVariable("username") String username, @NonNull @RequestBody User userToUpdate) {
        userService.updateUser(username, userToUpdate);
    }

    
    /** 
     * Gets all the dogs owned by a user
     * @param username - username to get the dogs from
     * @return Collection<Dog> is all the dogs owned by the user. Returns null if no user exists with that username
     */
    @GetMapping(path = "{username}/dogs")
    public Collection<Dog> getAllDogs(@PathVariable("username") String username) {
        return userService.getAllDogsForUsername(username);
    }

    
    /** 
     * Generates a dog for the user specified by the username
     * @param username - username of the user for the dog to be generated
     * @param dogName - name of the new dog
     */
    @PostMapping(path = "{username}/dogs/{dogName}")
    public void generateDog(@PathVariable("username") String username, @PathVariable("dogName") String dogName) {
        userService.generateDogForUsername(username, dogName);
    }
}
