package at.fhtw.swen2.persistence.repository;

import at.fhtw.swen2.persistence.entity.TourEntity;
import at.fhtw.swen2.persistence.entity.TourLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourLogRepository extends JpaRepository<TourLogEntity, Long> {
    void deleteByTourId(Long tourId);
    List<TourLogEntity> findByTourId(Long tourId);
    List<TourLogEntity> findByCommentContainingIgnoreCase(String comment);
}

