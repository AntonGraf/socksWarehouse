package ru.skypro.service;

import org.springframework.stereotype.Service;
import ru.skypro.dto.SocksDto;
import ru.skypro.entity.SocksEntity;
import ru.skypro.repository.SocksRepository;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SocksService {

    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public void income(SocksDto socksDto) {
        if (checkCottonPartParameter(socksDto.getCottonPart()) || socksDto.getQuantity() < 0) {
            throw new IllegalArgumentException("Не корректные значения");
        }
        SocksEntity socks = findSocksEntity(socksDto.getColor(), socksDto.getCottonPart());
        socks.setQuantity(socks.getQuantity() + socksDto.getQuantity());
        socksRepository.save(socks);
    }

    public void outcome(SocksDto socksDto) {
        if (checkCottonPartParameter(socksDto.getCottonPart()) || socksDto.getQuantity() < 0) {
            throw new IllegalArgumentException("Не корректные значения");
        }

        SocksEntity socks = socksRepository.findSocksEntityByColorAndCottonPart(socksDto.getColor(),
                socksDto.getCottonPart()).orElseThrow(IllegalArgumentException::new);

        if ((socks.getQuantity() - socksDto.getQuantity()) < 0) {
            throw new IllegalArgumentException("Не корректные значения");
        }

        socks.setQuantity(socks.getQuantity() - socksDto.getQuantity());
        socksRepository.save(socks);
    }

    public int getQuantitySocksByColorAndCottonPart(String color, String operation, short cottonPart) {

        if (checkCottonPartParameter(cottonPart)) {
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
        return (cottonPart < 0 || cottonPart > 100);
    }

    private SocksEntity findSocksEntity(String color, short cottonPart) {
        SocksEntity socks = socksRepository.findSocksEntityByColorAndCottonPart(color, cottonPart)
                .orElse(new SocksEntity());

        if (socks.getId() == 0) {
            socks.setColor(color);
            socks.setCottonPart(cottonPart);
            socks.setQuantity(0);
            return socksRepository.save(socks);
        }

        return socks;
    }

}
