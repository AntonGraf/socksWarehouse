package ru.skypro.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * DTO с информацией о носках
 */
@Getter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocksDto {

    //цвет
    String color;
    //процент хлопка
    short cottonPart;
    //количество
    int quantity;

}
