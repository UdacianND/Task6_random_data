package com.example.task6_random_data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String id;
    private String fullName;
    private String address;
    private String phoneNumber;

    @Override
    public String toString() {
        return fullName;

    }
}
