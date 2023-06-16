package at.fhtw.swen2.controller;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


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
        TourDTO tour = new TourDTO(0L, "Create Tour Success", "bla", "Belgrade", "Vienna", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);

        TourDTO response = restTemplate.postForObject(BASE_URL, tour, TourDTO.class);
        System.out.println("create tour succ tour id " + response.getId());
        assertEquals(tour.getName(), response.getName());
    }
    @Test
    public void createTour_ReturnsInternalServerError() {
        // integer as destination and starting point should fail
        TourDTO tour = new TourDTO(0L, "Create Tour Internal Error", "bla", "123", "456", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);
        HttpStatusCodeException exception = assertThrows(HttpStatusCodeException.class, () ->
                restTemplate.postForObject(BASE_URL, tour, TourDTO.class));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }

    @Test
    public void createTour_ExceptionThrown() {
        assertThrows(RestClientException.class, () -> restTemplate.postForObject(BASE_URL, null, TourDTO.class));
    }
    @Test
    public void updateTour_ReturnsOkStatus() {
        // Arrange
        TourDTO tourDTOtest = new TourDTO(0L, "Update Tour Return Ok", "bla", "Bonn", "Wien", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);
        TourDTO response = restTemplate.postForObject(BASE_URL, tourDTOtest, TourDTO.class);
        TourDTO tourDTO = new TourDTO();
        // Set properties of the tour object
        tourDTO.setName("Updated");
        tourDTO.setDescription("test");
        tourDTO.setFrom("Oslo");
        tourDTO.setTo("Malme");
        tourDTO.setTransportType(TransportType.shortest);
        // Act & Assert
        assertDoesNotThrow(() -> {
            assert response != null;
            restTemplate.put(BASE_URL + "/" + response.getId(), tourDTO);
        });
    }


    @Test
    public void updateTour_NonExistingTour_ReturnsNotFoundStatus() {
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
    @Test
    public void deleteTour_Successful() {
        // create tour success
        TourDTO tour = new TourDTO(0L, "Tour to Delete", "bla", "Bonn", "Wien", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);

        TourDTO response = restTemplate.postForObject(BASE_URL, tour, TourDTO.class);
        assertEquals(tour.getName(), response.getName());
        //delete tour
        assertDoesNotThrow(() ->  restTemplate.delete(BASE_URL + "/" + response.getId()));
    }

}

