package io.github.jdrolet.dogspring.controller;

import io.github.jdrolet.dogspring.model.Dog;
import io.github.jdrolet.dogspring.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

/**
 * Acts as the main API for getting dogs from the database
 */
@RequestMapping("/api/dogs")
@RestController
public class DogController {
    private final DogService dogService;

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }
    
    /** 
     * Returns the dog specified by the dogID from the dog database.
     * @param id - UUID of the dog requested
     * @return Dog that is specified by the id. Returns null if not existant
     */
    @GetMapping(path = "{dogId}")
    public Dog getDogById(@PathVariable("dogId") UUID id) {
        return dogService.getDogById(id).orElse(null);
    }
}
