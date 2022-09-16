package io.github.jdrolet.dogspring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String userName;
    private String displayName;
    private ArrayList<UUID> ownedDogIds;
    private int gold;
    private LocalDateTime lastDogRequest;

    public User() {

    }
    
    public User(@JsonProperty("username") String username, @JsonProperty("displayName") String displayname) {
        this.userName = username;
        this.displayName = displayname;
        this.ownedDogIds = new ArrayList<UUID>();
        this.gold = 0;
        this.lastDogRequest = LocalDateTime.of(1900, 1, 1, 0, 0);
    }

    public void setUsername(String username) { this.userName = username; }

    public String getUsername() { return userName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return displayName; }

    public void setOwnedDogIds(ArrayList<UUID> ownedDogIds) { this.ownedDogIds = ownedDogIds; }

    public List<UUID> getOwnedDogIds() { return ownedDogIds; }

    public void setGold(int gold) { this.gold = gold;}

    public int getGold() { return gold; }

    public void setLastDogRequest(LocalDateTime lastDogRequest) { this.lastDogRequest = lastDogRequest; }

    public LocalDateTime getLastDogRequest() { return lastDogRequest; }

    
    /** 
     * Adds a dog to the list of dogs owned by this user
     * @param id - UUID of the added dog
     */
    public void addDogId(UUID id) {
        System.out.println(id);
        ownedDogIds.add(id);
    }

    /**
     * Used to update the time when the user last generated a dog
     */
    public void updateLastDogRequest() { lastDogRequest = LocalDateTime.now(); }
}
