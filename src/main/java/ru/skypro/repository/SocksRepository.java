package ru.skypro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.entity.SocksEntity;

import java.util.Optional;

@Repository
public interface SocksRepository extends JpaRepository<SocksEntity, Integer> {

//    Collection<Socks> findSocksByColorAndCottonPartEquals(String color, short cottonPart);
    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cotton_part = :cottonpart",
            nativeQuery = true)
    Optional<Integer> getSumSocksByColorAndCottonPartEquals(@Param("color") String color, @Param("cottonpart") short cottonPart);

//    Collection<Socks> findSocksByColorAndCottonPartLessThan(String color, short cottonPart);

    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cotton_part < :cottonpart",
            nativeQuery = true)
    Optional<Integer> getSumSocksByColorAndCottonPartLessThan(@Param("color") String color, @Param("cottonpart") short cottonPart);

//    Collection<Socks> findSocksByColorAndCottonPartGreaterThan(String color, short cottonPart);

    @Query(value = "SELECT SUM(quantity) FROM socks s WHERE s.color = :color AND s.cotton_part > :cottonpart",
            nativeQuery = true)
    Optional<Integer> getSumSocksByColorAndCottonPartMoreThan(@Param("color") String color, @Param("cottonpart") short cottonPart);
}
