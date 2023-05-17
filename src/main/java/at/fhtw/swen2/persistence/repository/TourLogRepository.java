package at.fhtw.swen2.persistence.repository;

import at.fhtw.swen2.persistence.entity.TourLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourLogRepository extends JpaRepository<TourLogEntity, Long> {
}

