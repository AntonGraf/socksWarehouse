package ru.skypro.service;

import org.springframework.stereotype.Service;

@Service
public class SocksService {
    public int getSocks(String color, String operation, int cottonPart) {
        return color.length() + operation.length() + cottonPart;
    }
}
