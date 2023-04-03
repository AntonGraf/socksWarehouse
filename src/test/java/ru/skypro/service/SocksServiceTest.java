package ru.skypro.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.dto.SocksDto;
import ru.skypro.entity.SocksEntity;
import ru.skypro.repository.SocksRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class SocksServiceTest {

    @Mock
    private SocksRepository socksRepository;
    @InjectMocks
    private SocksService socksService;


    @Test
    void incomePositive() {
        SocksEntity socksEntity = getSocksEntity();
        lenient().when(socksRepository.findSocksEntityByColorAndCottonPart("черный", (short) 100))
                .thenReturn(Optional.of(socksEntity));
        SocksDto socksDto = getSocksDto();

        SocksEntity savedSockEntity = new SocksEntity();
        savedSockEntity.setId(socksEntity.getId());
        savedSockEntity.setColor(socksEntity.getColor());
        savedSockEntity.setCottonPart(socksEntity.getCottonPart());
        savedSockEntity.setQuantity(socksEntity.getQuantity() + socksDto.getQuantity());

        lenient().when(socksRepository.save(savedSockEntity)).thenReturn(savedSockEntity);
        assertDoesNotThrow(() -> socksService.income(socksDto));
    }

    @Test
    void incomePositiveWhenNewSocks() {
        lenient().when(socksRepository.findSocksEntityByColorAndCottonPart("черный", (short) 100))
                .thenReturn(Optional.of(new SocksEntity()));
        SocksDto socksDto = getSocksDto();
        SocksEntity socksEntity = new SocksEntity();
        socksEntity.setId(1);
        socksEntity.setColor(socksDto.getColor());
        socksEntity.setCottonPart(socksDto.getCottonPart());
        socksEntity.setQuantity(0);
        lenient().when(socksRepository.save(any(SocksEntity.class))).thenReturn(socksEntity);

        SocksEntity savedSockEntity = new SocksEntity();
        savedSockEntity.setId(1);
        savedSockEntity.setColor(socksDto.getColor());
        savedSockEntity.setCottonPart(socksDto.getCottonPart());
        savedSockEntity.setQuantity(socksDto.getQuantity());

        lenient().when(socksRepository.save(savedSockEntity)).thenReturn(savedSockEntity);
        assertDoesNotThrow(() -> socksService.income(socksDto));
    }

    @Test
    void incomeNegativeQuantity() {
        SocksDto socksDto = new SocksDto("черный", (short) 100, -1);
        assertThrows(IllegalArgumentException.class, () -> socksService.income(socksDto));
    }

    @Test
    void incomeNegativeCottonPart() {
        SocksDto socksDto = new SocksDto("черный", (short) 102, 10);
        assertThrows(IllegalArgumentException.class, () -> socksService.income(socksDto));
        SocksDto socksDto2 = new SocksDto("черный", (short) -1, 10);
        assertThrows(IllegalArgumentException.class, () -> socksService.income(socksDto2));
    }

    @Test
    void outcomePositive() {
        SocksDto socksDto = getSocksDto();
        SocksEntity socksEntity = getSocksEntity();
        lenient().when(socksRepository
                .findSocksEntityByColorAndCottonPart(socksEntity.getColor(), socksDto.getCottonPart()))
                .thenReturn(Optional.of(socksEntity));

        SocksEntity savedSockEntity = new SocksEntity();
        savedSockEntity.setId(socksEntity.getId());
        savedSockEntity.setColor(socksEntity.getColor());
        savedSockEntity.setCottonPart(socksEntity.getCottonPart());
        savedSockEntity.setQuantity(socksEntity.getQuantity() - socksDto.getQuantity());

        lenient().when(socksRepository.save(savedSockEntity)).thenReturn(savedSockEntity);
        assertDoesNotThrow(() -> socksService.outcome(socksDto));
    }

    @Test
    void outcomeNegativeQuantity() {
        SocksDto socksDto = new SocksDto("черный", (short) 100, -1);
        assertThrows(IllegalArgumentException.class, () -> socksService.outcome(socksDto));
    }

    @Test
    void outcomeNegativeQuantityInWarehouse() {
        SocksDto socksDto = new SocksDto("черный", (short) 100, 24);
        SocksEntity socksEntity = getSocksEntity();
        lenient().when(socksRepository
                        .findSocksEntityByColorAndCottonPart(socksEntity.getColor(), socksDto.getCottonPart()))
                .thenReturn(Optional.of(socksEntity));

        SocksEntity savedSockEntity = new SocksEntity();
        savedSockEntity.setId(socksEntity.getId());
        savedSockEntity.setColor(socksEntity.getColor());
        savedSockEntity.setCottonPart(socksEntity.getCottonPart());
        savedSockEntity.setQuantity(socksEntity.getQuantity() - socksDto.getQuantity());
        lenient().when(socksRepository.save(savedSockEntity)).thenReturn(savedSockEntity);


        assertThrows(IllegalArgumentException.class, () -> socksService.outcome(socksDto));
    }

    @Test
    void outcomeNegativeCottonPart() {
        SocksDto socksDto = new SocksDto("черный", (short) 102, 10);
        assertThrows(IllegalArgumentException.class, () -> socksService.outcome(socksDto));
        SocksDto socksDto2 = new SocksDto("черный", (short) -1, 10);
        assertThrows(IllegalArgumentException.class, () -> socksService.outcome(socksDto2));
    }

    @Test
    void outcomeNegativeSocks() {
        SocksDto socksDto = getSocksDto();
        lenient().when(socksRepository.findSocksEntityByColorAndCottonPart(socksDto.getColor(), socksDto.getCottonPart()))
                .thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> socksService.outcome(socksDto));
    }

    @Test
    void getQuantitySocksByColorAndCottonPartEqual() {
        String color = "черный";
        short cottonPart = 100;
        String operation = "equal";
        lenient().when(socksRepository.getSumSocksByColorAndCottonPartEquals(color, cottonPart))
                .thenReturn(Optional.of(10));
        assertEquals(socksService.getQuantitySocksByColorAndCottonPart(color, operation, cottonPart), 10);
    }

    @Test
    void getQuantitySocksByColorAndCottonPartLessThan() {
        String color = "черный";
        short cottonPart = 100;
        String operation = "lessThan";
        lenient().when(socksRepository.getSumSocksByColorAndCottonPartLessThan(color, cottonPart))
                .thenReturn(Optional.of(10));
        assertEquals(socksService.getQuantitySocksByColorAndCottonPart(color, operation, cottonPart), 10);
    }

    @Test
    void getQuantitySocksByColorAndCottonPartMoreThan() {
        String color = "черный";
        short cottonPart = 100;
        String operation = "moreThan";
        lenient().when(socksRepository.getSumSocksByColorAndCottonPartMoreThan(color, cottonPart))
                .thenReturn(Optional.of(10));
        assertEquals(socksService.getQuantitySocksByColorAndCottonPart(color, operation, cottonPart), 10);
    }
    @Test
    void getQuantitySocksByColorAndCottonPartNegativeOperation() {
        String color = "черный";
        short cottonPart = 100;
        String operation = "moreEqual";
        assertThrows(IllegalArgumentException.class, () -> socksService.getQuantitySocksByColorAndCottonPart(color,
                operation, cottonPart));
    }

    @Test
    void getQuantitySocksByColorAndCottonPartNegativeCottonPart() {
        String color = "черный";
        short cottonPart = 101;
        short cottonPartNegative = -1;
        String operation = "moreThan";
        assertThrows(IllegalArgumentException.class, () -> socksService.getQuantitySocksByColorAndCottonPart(color,
                operation, cottonPart));
        assertThrows(IllegalArgumentException.class, () -> socksService.getQuantitySocksByColorAndCottonPart(color,
                operation, cottonPartNegative));
    }
    private SocksDto getSocksDto() {
        return new SocksDto("черный", (short) 100, 6);
    }

    private SocksEntity getSocksEntity() {
        SocksEntity socksEntity = new SocksEntity();
        socksEntity.setId(1);
        socksEntity.setColor("черный");
        socksEntity.setCottonPart((short) 100);
        socksEntity.setQuantity(8);
        return socksEntity;
    }
}