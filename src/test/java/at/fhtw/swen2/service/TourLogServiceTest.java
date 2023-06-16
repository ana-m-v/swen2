package at.fhtw.swen2.service;

import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.persistence.entity.TourEntity;
import at.fhtw.swen2.persistence.entity.TourLogEntity;
import at.fhtw.swen2.persistence.repository.TourLogRepository;
import at.fhtw.swen2.persistence.repository.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourLogServiceTest {
    @Autowired
    private TourLogService tourLogService;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourLogRepository tourLogRepository;

    private Long existingTourId;

    @BeforeEach
    public void setUp() {
        TourEntity tourEntity = new TourEntity();
        tourEntity.setName("Log Service Test Tour");
        tourEntity = tourRepository.save(tourEntity);
        existingTourId = tourEntity.getId();
    }

//    @AfterEach
//    public void tearDown() {
//        // Perform any necessary cleanup after each test
//        tourRepository.deleteAll();
//        tourLogRepository.deleteAll();
//    }

    @Test
    public void createTourLog_TourId_ReturnsCreatedTourLogDTO() {
        TourLogDTO tourLogDTO = new TourLogDTO();
        TourLogDTO createdTourLogDTO = tourLogService.createTourLog(tourLogDTO, existingTourId);
        assertNotNull(createdTourLogDTO);
        TourLogDTO savedTourLogDTO = tourLogService.getTourLogById(createdTourLogDTO.getId());
        assertNotNull(savedTourLogDTO);
    }

    @Test
    public void deleteTourLog_ExistingTourLogId_Success() {
        TourLogDTO tourLogDTO = new TourLogDTO();
        TourLogDTO createdTourLogDto = tourLogService.createTourLog(tourLogDTO, existingTourId);

        tourLogService.deleteTourLog(createdTourLogDto.getId());

        assertNull(tourLogService.getTourLogById(existingTourId));
    }

    @Test
    public void updateTourLog_ExistingTourLog_ReturnsUpdatedTourLogDTO() {
        TourLogDTO existingTourLogDTO = new TourLogDTO();
        existingTourLogDTO.setComment("old comment");
        TourLogDTO existingTourLogDTOSaved = tourLogService.createTourLog(existingTourLogDTO, existingTourId);
        TourLogDTO toUpdateTourLogDTO = new TourLogDTO();
        toUpdateTourLogDTO.setId(existingTourLogDTOSaved.getId());
        toUpdateTourLogDTO.setComment("new comment");

        TourLogDTO updatedTourLog = tourLogService.updateTourLog(toUpdateTourLogDTO);

        assertNotNull(updatedTourLog);
        assertEquals(updatedTourLog.getComment(), toUpdateTourLogDTO.getComment());

    }
}