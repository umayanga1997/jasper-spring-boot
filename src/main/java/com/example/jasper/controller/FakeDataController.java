package com.example.jasper.controller;

import com.example.jasper.service.FakeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fake-data")
public class FakeDataController {

    @Autowired
    private FakeDataService fakeDataService;

    @GetMapping("/users")
    public ResponseEntity<String> generateFakeUserData(@RequestParam(name = "count") int count) {
        return fakeDataService.generateFakeData(count);
    }

}
