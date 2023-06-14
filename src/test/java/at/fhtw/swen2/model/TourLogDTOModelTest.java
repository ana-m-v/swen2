package at.fhtw.swen2.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import at.fhtw.swen2.persistence.entity.TourLogEntity;


public class TourLogDTOModelTest {
    private TourLogDTO tourLogDTO;

    @BeforeEach
    public void setup() {
        // Initialize a TourLogDTO object with sample data for testing
        tourLogDTO = new TourLogDTO(1L, Timestamp.valueOf("2023-05-01 12:00:00"), "imagine all the people", 3, 120, 4);
    }
    @Test
    public void testConstructorWithTourLogEntity() {
        TourLogEntity tourLogEntity = new TourLogEntity();
        tourLogEntity.setId(1L);
        tourLogEntity.setDateTime(Timestamp.valueOf("2023-05-01 12:00:00"));
        tourLogEntity.setComment("Test comment");
        tourLogEntity.setDifficulty(3);
        tourLogEntity.setTotalTime(120);
        tourLogEntity.setRating(4);

        TourLogDTO convertedTourLogDTO = new TourLogDTO(tourLogEntity);

        Assertions.assertEquals(tourLogEntity.getId(), convertedTourLogDTO.getId());
        Assertions.assertEquals(tourLogEntity.getDateTime(), convertedTourLogDTO.getDateTime());
        Assertions.assertEquals(tourLogEntity.getComment(), convertedTourLogDTO.getComment());
        Assertions.assertEquals(tourLogEntity.getDifficulty(), convertedTourLogDTO.getDifficulty());
        Assertions.assertEquals(tourLogEntity.getTotalTime(), convertedTourLogDTO.getTotalTime());
        Assertions.assertEquals(tourLogEntity.getRating(), convertedTourLogDTO.getRating());
    }
}