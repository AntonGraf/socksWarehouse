package ru.skypro.service;

import org.springframework.stereotype.Service;
import ru.skypro.repository.SocksRepository;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SocksService {

    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public int getSocks(String color, String operation, int cottonPart) {
        return color.length() + operation.length() + cottonPart;
    }

    public int getQuantitySocksByColorAndCottonPart(String color, String operation, short cottonPart) {

        if (!checkCottonPartParameter(cottonPart)) {
            throw new IllegalArgumentException(cottonPart + " - не корректное значение процента хлопка");
        }

        switch (operation) {
            case "equal": {
                return getQuantitySocksByColorAndCottonPartEquals(color, cottonPart);
            }
            case "lessThan": {
                return getQuantitySocksByColorAndCottonPartLessThan(color, cottonPart);
            }
            case "moreThan": {
                return getQuantitySocksByColorAndCottonPartMoreThan(color, cottonPart);
            }
        }
        throw  new IllegalArgumentException("Операция " + operation + " не поддерживается");
    }
    private int getQuantitySocksByColorAndCottonPartEquals(String color, short cottonPart) {
        return socksRepository.getSumSocksByColorAndCottonPartEquals(color, cottonPart).orElse(0);
    }

    private int getQuantitySocksByColorAndCottonPartLessThan(String color, short cottonPart) {
        return socksRepository.getSumSocksByColorAndCottonPartLessThan(color, cottonPart).orElse(0);
    }

    private int getQuantitySocksByColorAndCottonPartMoreThan(String color, short cottonPart) {
        return socksRepository.getSumSocksByColorAndCottonPartMoreThan(color, cottonPart).orElse(0);
    }

    private boolean checkCottonPartParameter(short cottonPart) {
        return (cottonPart >= 0 && cottonPart <= 100);
    }
}
