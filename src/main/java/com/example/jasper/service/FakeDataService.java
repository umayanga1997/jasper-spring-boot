package com.example.jasper.service;
import com.example.jasper.entity.UserEntity;
import com.example.jasper.model.UserModel;
import com.example.jasper.repository.UserRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FakeDataService {

    @Autowired
    UserRepo userRepo;

    public ResponseEntity<String> generateFakeData(int count) {
        Faker faker = new Faker();
        List<UserEntity> userList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            UserModel user = new UserModel();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            // add more fields if needed
            userList.add(UserEntity.builder().firstName(user.getFirstName()).lastName(user.getLastName()).email(user.getEmail()).build());
        }

        userRepo.saveAll(userList);

        return ResponseEntity.ok().body("Fake data collection was saved successful!");
    }

}
