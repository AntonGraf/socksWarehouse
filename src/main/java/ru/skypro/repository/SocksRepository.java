package ru.skypro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.entity.Socks;

import java.util.Optional;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Integer> {
    Optional<Socks> findSocksByColorAndCottonPart(String color, int cottonPart);
}
