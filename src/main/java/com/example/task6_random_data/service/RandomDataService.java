package com.example.task6_random_data.service;

import com.example.task6_random_data.payload.request.RandomDataAttributeDto;
import com.example.task6_random_data.payload.response.UserInfoDto;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class RandomDataService {

    private Random random;
    private Faker faker;

    public List<UserInfoDto> getUserInfo(RandomDataAttributeDto dataAttributeDto){
        int SEED_PAGING_KEY = 1147349;
        double errors = dataAttributeDto.getErrors();
        int min = (int) Math.floor(errors);
        int max = (int) Math.ceil(errors);
        int pageSeed = dataAttributeDto.getSeed() + (dataAttributeDto.getPage()-1)*SEED_PAGING_KEY;
        random = new Random(pageSeed);
        faker = new Faker(new Locale(dataAttributeDto.getCountry()), new Random(pageSeed));

        List<UserInfoDto> users = new ArrayList<>();
        for (int i = 0; i < dataAttributeDto.getRows(); i++) {
            int errorCount = i % 2==0 ? max : min;
            users.add(newUser(errorCount));
        }
        return users;
    }

    private UserInfoDto newUser(int errors){
        String id = faker.regexify("[0-9]{10}");
        String fullName =faker.name().fullName();
        String address = faker.address().fullAddress();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        UserInfoDto user = new UserInfoDto(id,fullName,address,phoneNumber);
        System.out.println(user);
        invalidateUserInfo(user, errors);
        return user;
    }

    private void invalidateUserInfo(UserInfoDto userInfo, int errors){
        for (int i = 0; i < errors; i++) {
            int fieldOrder = i % 4;
            int methodOrder = (i / 4) % 3;
            chooseFieldToAddError(userInfo, fieldOrder, methodOrder);
        }
    }

    private void chooseFieldToAddError(UserInfoDto userInfo, int fieldOrder, int methodOrder){
        switch (fieldOrder){
            case 0 -> userInfo.setId(chooseMethodToAddError(userInfo.getId(), methodOrder));
            case 1 -> userInfo.setFullName(chooseMethodToAddError(userInfo.getFullName(), methodOrder));
            case 2 -> userInfo.setAddress(chooseMethodToAddError(userInfo.getAddress(), methodOrder));
            default -> userInfo.setPhoneNumber(chooseMethodToAddError(userInfo.getPhoneNumber(), methodOrder));
        }
    }

    private String chooseMethodToAddError(String data, int methodOrder){
        String invalidData;
        switch (methodOrder) {
            case 0 -> invalidData = addRandomChar(data);
            case 1 -> invalidData = swap(data);
            default -> invalidData = removeCharInRandomPosition(data);
        }
        return invalidData;
    }

    private String swap(String data){
        if (data.length() == 0)
            return data;
        char[] dataChars = data.toCharArray();
        int charIndex1 = random.nextInt(dataChars.length);
        int charIndex2 = random.nextInt( dataChars.length);
        char temp = dataChars[charIndex1];
        dataChars[charIndex1] = dataChars[charIndex2];
        dataChars[charIndex2] = temp;
        return new String(dataChars);
    }

    private String addRandomChar(String data){
        char randomChar = (char)(random.nextInt(26) + 'a');
        if(data.length() == 0)
            return randomChar + data;
        int randomIndex = random.nextInt(data.length());
        return data.substring(0,randomIndex) + randomChar + data.substring(randomIndex);
    }

    private String removeCharInRandomPosition(String data){
        if (data.length() == 0)
            return data;
        int randomIndex = random.nextInt(data.length());
        return data.substring(0,randomIndex) + data.substring(randomIndex+1);
    }

}
