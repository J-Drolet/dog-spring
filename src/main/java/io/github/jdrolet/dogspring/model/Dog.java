package io.github.jdrolet.dogspring.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dog {
    private String name;
    private UUID id;
    private int happiness;
    private int energy;
    private int hunger;
    private String imgLink;
    private LocalDateTime birthdate;
    private String ownerUsername;

    public Dog() {
    }

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

    public Dog(UUID id, String name, int happiness, int energy, int hunger, String imgLink, LocalDateTime birthdate, String ownerUsername) {
        this.name = name;
        this.id = id;
        this.happiness = happiness;
        this.energy = energy;
        this.hunger = hunger;
        this.imgLink = imgLink;
        this.birthdate = birthdate;
        this.ownerUsername = ownerUsername;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setId(UUID id) { this.id = id; }

    public UUID getId() { return id; }

    public void setHappiness(int happiness) { this.happiness = happiness; }

    public int getHappiness() { return happiness; }

    public void setEnergy(int energy) { this.energy = energy; }

    public int getEnergy() { return energy; }

    public void setHunger(int hunger) { this.hunger = hunger; }

    public int getHunger() { return hunger; }

    public void setImgLink(String imgLink) { this.imgLink = imgLink; }
    
    public String getImgLink() { return imgLink; }

    public void setBirthdate(LocalDateTime birthdate) { this.birthdate = birthdate; }
    
    public LocalDateTime getBirthdate() { return birthdate; }

    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }

    public String getOwnerUsername() { return ownerUsername; }
}
