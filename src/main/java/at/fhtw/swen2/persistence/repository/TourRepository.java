package at.fhtw.swen2.persistence.repository;

import at.fhtw.swen2.persistence.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
    List<TourEntity> findByNameContainingIgnoreCase(String name);
}

