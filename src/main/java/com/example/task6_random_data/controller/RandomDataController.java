package com.example.task6_random_data.controller;

import com.example.task6_random_data.payload.request.RandomDataAttributeDto;
import com.example.task6_random_data.payload.response.UserInfoDto;
import com.example.task6_random_data.service.RandomDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RandomDataController {

    private final RandomDataService randomDataService;

    @PostMapping("api/random-data")
    public List<UserInfoDto> getRandomData(
            @RequestBody RandomDataAttributeDto dataAttributeDto
            ){
        return randomDataService.getUserInfo(dataAttributeDto);
    }
}
