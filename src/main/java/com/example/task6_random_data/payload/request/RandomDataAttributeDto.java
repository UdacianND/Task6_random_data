package com.example.task6_random_data.payload.request;

import lombok.Data;

@Data
public class RandomDataAttributeDto {
    private String country;
    private float errors;
    private int seed;
    private int page;
    private int rows;
}
