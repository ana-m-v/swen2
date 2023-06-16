package at.fhtw.swen2.service;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.persistence.repository.TourRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourServiceTest {

    @Autowired
    private TourService tourService;

    @Test
    public void createTour_ValidTour_ReturnsCreatedTourDTO() {
        // Create a TourDTO object
        TourDTO tourDTO = new TourDTO();
        tourDTO.setName("Service Test Tour Create");

        // Call the createTour method in the tour service
        TourDTO createdTourDTO = tourService.createTour(tourDTO);

        // Verify that the createdTourDTO is not null
        assertNotNull(createdTourDTO);

        // Verify that the tour name matches the original tourDTO name
        assertEquals(tourDTO.getName(), createdTourDTO.getName());
    }

    @Test
    void updateTour() {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setName("Service Test Tour Create");

        // Call the createTour method in the tour service
        TourDTO createdTourDTO = tourService.createTour(tourDTO);

        // Verify that the createdTourDTO is not null
        assertNotNull(createdTourDTO);

        // Verify that the tour name matches the original tourDTO name
        assertEquals(tourDTO.getName(), createdTourDTO.getName());

        TourDTO toUpdateTourDTO = new TourDTO();
        toUpdateTourDTO.setName("Updated Service");
        TourDTO updatedTourDTO = tourService.updateTour(toUpdateTourDTO);

        assertEquals(updatedTourDTO.getName(), toUpdateTourDTO.getName());

    }

    @Test
    void deleteExistingTour() {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setName("Service Test Tour Delete");

        // Call the createTour method in the tour service
        TourDTO createdTourDTO = tourService.createTour(tourDTO);

        // Verify that the createdTourDTO is not null
        assertNotNull(createdTourDTO);

        // Verify that the tour name matches the original tourDTO name
        assertEquals(tourDTO.getName(), createdTourDTO.getName());

        tourService.deleteTour(createdTourDTO.getId());

        assertNull(tourService.getTourById(createdTourDTO.getId()));
    }


    @Test
    void searchQuery() {
    }
}