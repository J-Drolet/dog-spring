package io.github.jdrolet.dogspring.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import io.github.jdrolet.dogspring.model.Dog;
import io.github.jdrolet.dogspring.model.DogForm;
import io.github.jdrolet.dogspring.model.LoginForm;
import io.github.jdrolet.dogspring.model.User;

/**
 * This controller handles front-facing interactions with a browser
 */
@Controller
public class DisplayController {
    @Autowired
    UserController userController;

    @Autowired
    DogController dogController;
    
    @GetMapping
    public ModelAndView getHomepage() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("test", "Welcome. Thymeleaf attribute test");
        mav.addObject("loginForm", new LoginForm());
        return mav;
    }

    @GetMapping(path = "users/{username}")
    public ModelAndView getUserPage(@PathVariable("username") String username) {
        User user = userController.getUserByUsername(username);
        ModelAndView mav = new ModelAndView("userPage");
        mav.addObject("user", user);
        mav.addObject("dogs", userController.getAllDogs(username));
        mav.addObject("dogForm", new DogForm());

        return mav;
    }

    @PostMapping("/new")
    public ModelAndView addUser(@ModelAttribute LoginForm userForm, ModelAndView mav) {
        if(userController.addUser(new User(userForm.getUsername(), userForm.getDisplayName())))
            return new ModelAndView("redirect:/users/" + userForm.getUsername());
        // @TODO add proper error message
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/login")
    public RedirectView attemptLogin(@ModelAttribute LoginForm userForm) {
        User user = userController.getUserByUsername(userForm.getUsername());
        if(user == null) {
            // return error
            return new RedirectView("/error");
        }
        return new RedirectView("/users/" + user.getUsername());
    }

    
    @GetMapping(path = "dogs/{id}")
    public ModelAndView getDog(@PathVariable("id") UUID dogId) {
        Dog dog = dogController.getDogById(dogId);
        ModelAndView mav = new ModelAndView("dogPage");
        mav.addObject("dog", dog);

        return mav;
    }

    @PostMapping(path = "users/{username}/dogs")
    public RedirectView generateDog(@ModelAttribute DogForm dogForm, @PathVariable("username") String username) {
        userController.generateDog(username, dogForm.getDogName());

        return new RedirectView("/users/" + username);
    }
}
