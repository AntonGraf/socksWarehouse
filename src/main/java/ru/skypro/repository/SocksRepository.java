package ru.skypro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.entity.Socks;

import java.util.Collection;

public interface SocksRepository extends JpaRepository<Socks, Integer> {

    Collection<Socks> findSocksByColorAndCottonPartEquals(String color, short cottonPart);
    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cottonpart = :cottonpart",
            nativeQuery = true)
    Integer getSumSocksByColorAndCottonPartEquals(@Param("color") String color, @Param("cottonpart") short cottonPart);

    Collection<Socks> findSocksByColorAndCottonPartLessThan(String color, short cottonPart);

    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cottonpart < :cottonpart",
            nativeQuery = true)
    Integer getSumSocksByColorAndCottonPartLessThan(@Param("color") String color, @Param("cottonpart") short cottonPart);

    Collection<Socks> findSocksByColorAndCottonPartGreaterThan(String color, short cottonPart);

    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cottonpart > :cottonpart",
            nativeQuery = true)
    Integer getSumSocksByColorAndCottonPartMoreThan(@Param("color") String color, @Param("cottonpart") short cottonPart);
}
