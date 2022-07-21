package io.github.jdrolet.dogspring.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jdrolet.dogspring.database.DogDao;
import io.github.jdrolet.dogspring.model.Dog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class DogService {
    private final DogDao dogDao;
    private static String DOG_ENDPOINT = "https://dog.ceo/api/breeds/image/random"; // the provider of dog images

    @Autowired
    public DogService(@Qualifier("fakeDogDao") DogDao dogDao) {
        this.dogDao = dogDao;
    }

    public Optional<Dog> getDogById(UUID id) {
        return dogDao.selectDogById(id);
    }

    public Dog generateDog(String dogName, String ownerUsername) {
        try {
            Dog generated = new Dog(dogName, UUID.randomUUID(), getRandomDogImage().toString(), ownerUsername);
            dogDao.insertDog(generated);
            return generated;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getRandomDogImage() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(DOG_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        // convert JSON string to Map
        Map<String, String> map = mapper.readValue(result.toString(), Map.class);

        return new URL(map.get("message"));
    }
}
