package io.github.jdrolet.dogspring.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dog {
    private String name;
    private final UUID id;
    private int happiness;
    private int energy;
    private int hunger;
    private String imgLink;
    private final LocalDateTime birthdate;
    private final String ownerUsername;

    public Dog(@JsonProperty("name") String name, @JsonProperty("id") UUID id, @JsonProperty("img") String imgLink, String ownerUsername) {
        this.name = name;
        this.id = id;
        this.happiness = 100;
        this.energy = 100;
        this.hunger = 0;
        this.imgLink = imgLink;
        this.birthdate = LocalDateTime.now();
        this.ownerUsername = ownerUsername;
    }

    public String getName() { return name; }

    public UUID getId() { return id; }

    public int getHappiness() { return happiness; }

    public int getEnergy() { return energy; }

    public int getHunger() { return hunger; }
    
    public String getImgLink() { return imgLink; }
    
    public LocalDateTime getBirthdate() { return birthdate; }

    public String getOwnerUsername() { return ownerUsername; }
}
