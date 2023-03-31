package ru.skypro.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.dto.SocksDto;
import ru.skypro.entity.SocksEntity;
import ru.skypro.repository.SocksRepository;

/**
 * Сервис выполняет функции получения количества носков, поступления и выдача носков со склада
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SocksService {

    //Репозиторий с данными о носках
    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    /**
     * Поступление носков
     * @param socksDto - dto, содержащий данные о цвете, количестве и содержании хлопка носков
     */
    @Transactional
    public void income(SocksDto socksDto) {
        if (checkCottonPartParameter(socksDto.getCottonPart()) || socksDto.getQuantity() < 0) {
            throw new IllegalArgumentException("Не корректные значения");
        }
        SocksEntity socks = findSocksEntity(socksDto.getColor(), socksDto.getCottonPart());
        socks.setQuantity(socks.getQuantity() + socksDto.getQuantity());
        socksRepository.save(socks);
    }

    /**
     * Выдача носков
     * @param socksDto - dto, содержащий данные о цвете, количестве и содержании хлопка носков
     */
    @Transactional
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

    /**
     * Получение количества носков по заданным критериям
     * @param color         - цвет
     * @param operation     - операция сравнение (moreThan, lessThan, equal)
     * @param cottonPart    - процент хлопка
     * @return              - количество пар носков, отвечающих заданным критериям
     */
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

    /**
     * Получение количества носков по заданным критериям с указанным содержанием хлопка
     * @param color         - цвет
     * @param cottonPart    - процент хлопка
     * @return              - количество пар носков, отвечающих заданным критериям
     */
    private int getQuantitySocksByColorAndCottonPartEquals(String color, short cottonPart) {
        return socksRepository.getSumSocksByColorAndCottonPartEquals(color, cottonPart).orElse(0);
    }

    /**
     * Получение количества носков по заданным критериям с содержанием хлопка меньше указанного
     * @param color         - цвет
     * @param cottonPart    - процент хлопка
     * @return              - количество пар носков, отвечающих заданным критериям
     */
    private int getQuantitySocksByColorAndCottonPartLessThan(String color, short cottonPart) {
        return socksRepository.getSumSocksByColorAndCottonPartLessThan(color, cottonPart).orElse(0);
    }

    /**
     * Получение количества носков по заданным критериям с содержанием хлопка больше указанного
     * @param color         - цвет
     * @param cottonPart    - процент хлопка
     * @return              - количество пар носков, отвечающих заданным критериям
     */
    private int getQuantitySocksByColorAndCottonPartMoreThan(String color, short cottonPart) {
        return socksRepository.getSumSocksByColorAndCottonPartMoreThan(color, cottonPart).orElse(0);
    }

    /**
     * Проверяет что процент содержания хлопка от 0 до 100
     * @param cottonPart    - процент хлопка
     * @return              - true, если процент хлопка от 0 до 100
     */
    private boolean checkCottonPartParameter(short cottonPart) {
        return (cottonPart < 0 || cottonPart > 100);
    }

    /**
     * Поиск типа носка по указанным параметрам
     * @param color         - цвет
     * @param cottonPart    - процент хлопка
     * @return              - сущность найденного носка
     */
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
