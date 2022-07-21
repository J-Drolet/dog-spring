package io.github.jdrolet.dogspring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private final String userName;
    private String displayName;
    private List<UUID> ownedDogIds;
    private int gold;
    private LocalDateTime lastDogRequest;
    
    public User(@JsonProperty("username") String username, @JsonProperty("displayName") String displayname) {
        this.userName = username;
        this.displayName = displayname;
        this.ownedDogIds = new ArrayList<UUID>();
        this.gold = 0;
        this.lastDogRequest = LocalDateTime.MIN;
    }

    public String getUsername() { return userName; }

    public String getDisplayName() { return displayName; }

    public List<UUID> getOwnedDogs() { return ownedDogIds; }

    public int getGold() { return gold; }

    public LocalDateTime getLastDogRequest() { return lastDogRequest; }

    
    /** 
     * Adds a dog to the list of dogs owned by this user
     * @param id - UUID of the added dog
     */
    public void addDogId(UUID id) {
        ownedDogIds.add(id);
    }

    /**
     * Used to update the time when the user last generated a dog
     */
    public void updateLastDogRequest() { lastDogRequest = LocalDateTime.now(); }
}
