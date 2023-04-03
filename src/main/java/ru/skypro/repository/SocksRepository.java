package ru.skypro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.entity.SocksEntity;

import java.util.Optional;

/**
 * Репозиторий по хранению носков
 */
@Repository
public interface SocksRepository extends JpaRepository<SocksEntity, Integer> {

    /**
     * Получение количества носков указанных цвета и процентом хлопка
     * @param color         - цвет
     * @param cottonPart    - процент хлопка
     * @return              - количество носков, удовлетворяющих заданному условию
     */
    Optional<SocksEntity> findSocksEntityByColorAndCottonPart(String color, short cottonPart);
    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cotton_part = :cottonpart",
            nativeQuery = true)
    Optional<Integer> getSumSocksByColorAndCottonPartEquals(@Param("color") String color,
                                                            @Param("cottonpart") short cottonPart);

    /**
     * Получение количества носков указанного цвета и процентом хлопка меньше указанного
     * @param color         - цвет
     * @param cottonPart    - процент хлопка
     * @return              - количество носков, удовлетворяющих заданному условию
     */
    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cotton_part < :cottonpart",
            nativeQuery = true)
    Optional<Integer> getSumSocksByColorAndCottonPartLessThan(@Param("color") String color,
                                                              @Param("cottonpart") short cottonPart);

    /**
     * Получение количества носков указанного цвета и процентом хлопка больше указанного
     * @param color         - цвет
     * @param cottonPart    - процент хлопка
     * @return              - количество носков, удовлетворяющих заданному условию
     */
    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cotton_part > :cottonpart",
            nativeQuery = true)
    Optional<Integer> getSumSocksByColorAndCottonPartMoreThan(@Param("color") String color,
                                                              @Param("cottonpart") short cottonPart);
}
