package at.fhtw.swen2.controller;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TransportType;
import at.fhtw.swen2.persistence.entity.TourEntity;
import at.fhtw.swen2.service.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TourControllerTest {

    private static final String BASE_URL = "http://localhost:8080/tours";
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void createTour_Successful() {
        // Arrange
        TourDTO tour = new TourDTO(1L, "Tour 1", "bla", "Belgrade", "Vienna", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);

        TourDTO response = restTemplate.postForObject(BASE_URL, tour, TourDTO.class);
        assertEquals(tour.getName(), response.getName());
    }

    @Test
    public void createTour_ExceptionThrown() {
        assertThrows(RestClientException.class, () -> restTemplate.postForObject(BASE_URL, null, TourDTO.class));
    }
    @Test
    public void updateEditedTour_ValidTour_ReturnsOkStatus() {
        // Arrange
        long tourId = 6L;
        TourDTO tourDTO = new TourDTO();
        // Set properties of the tour object
        tourDTO.setName("Updated");
        tourDTO.setDescription("test");
        tourDTO.setFrom("Oslo");
        tourDTO.setTo("Malme");
        tourDTO.setTransportType(TransportType.shortest);
        // Act & Assert
        assertDoesNotThrow(() -> restTemplate.put(BASE_URL + "/" + tourId, tourDTO));
    }

    @Test
    public void updateEditedTour_NonExistingTour_ReturnsNotFoundStatus() {
        // Arrange
        long tourId = 999L;
        TourDTO tourDTO = new TourDTO();
        tourDTO.setName("Updated");
        tourDTO.setDescription("test");
        tourDTO.setFrom("Oslo");
        tourDTO.setTo("Malme");
        tourDTO.setTransportType(TransportType.shortest);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
                restTemplate.put(BASE_URL + "/" + tourId, tourDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void deleteTour_NonExistingTour_ReturnsNotFoundStatus() {
        // Arrange
        long tourId = 999L;
        TourDTO tourDTO = new TourDTO();
        tourDTO.setName("Updated");
        tourDTO.setDescription("test");
        tourDTO.setFrom("Oslo");
        tourDTO.setTo("Malme");
        tourDTO.setTransportType(TransportType.shortest);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
                restTemplate.put(BASE_URL + "/" + tourId, tourDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

}