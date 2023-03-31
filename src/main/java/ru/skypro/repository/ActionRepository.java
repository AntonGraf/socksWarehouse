package ru.skypro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.entity.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {
}
