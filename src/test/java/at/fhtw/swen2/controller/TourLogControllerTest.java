package at.fhtw.swen2.controller;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;
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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TourLogControllerTest {

    private static final String BASE_URL = "http://localhost:8080/tours";
    private static final String BASE_LOG_URL = "http://localhost:8080/tourlogs";
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void createTourWithLog_Successful() {
        // create Tour to attach Log
        TourDTO tour = new TourDTO(0L, "Tour for Logs", "bla", "Innsbruck", "London", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);

        TourDTO response = restTemplate.postForObject(BASE_URL, tour, TourDTO.class);
        assertEquals(tour.getName(), response.getName());
        //create Log
        TourLogDTO tourLog = new TourLogDTO(0L, Timestamp.valueOf("2022-09-01 09:01:15"), "that was magical", 7, 1200, 10);
        assertDoesNotThrow(() -> restTemplate.postForObject(BASE_LOG_URL+"?tourId=" + response.getId(), tourLog, TourLogDTO.class));
    }
    @Test
    public void deleteLogAndTour_Successful() {
        // create Tour to attach Log
        TourDTO tour = new TourDTO(0L, "Tour for Logs", "bla", "Innsbruck", "London", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);

        TourDTO response = restTemplate.postForObject(BASE_URL, tour, TourDTO.class);
        assertEquals(tour.getName(), response.getName());
        //create Log
        TourLogDTO tourLog = new TourLogDTO(999L, Timestamp.valueOf("2022-09-01 09:01:15"), "that was magical", 7, 1200, 10);
        assertDoesNotThrow(() -> restTemplate.postForObject(BASE_LOG_URL+"?tourId=" + response.getId(), tourLog, TourLogDTO.class));
        //delete Log
        assertDoesNotThrow(() -> restTemplate.delete(BASE_LOG_URL+"/999"));
        //delete Tour
        assertDoesNotThrow(() -> restTemplate.delete(BASE_URL+"/" + response.getId()));
    }

    @Test
    public void createLog_ExceptionThrown() {
        //send empty log -> fail
        assertThrows(RestClientException.class, () -> restTemplate.postForObject(BASE_LOG_URL, null, TourDTO.class));
    }
    @Test
    public void updateEditedTourLog_ValidTour_ReturnsOkStatus() {
        // create Tour to attach Log
        TourDTO tour = new TourDTO(0L, "Tour for Logs", "bla", "Innsbruck", "London", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);

        TourDTO response = restTemplate.postForObject(BASE_URL, tour, TourDTO.class);
        assertEquals(tour.getName(), response.getName());
        //create Log
        TourLogDTO tourLog = new TourLogDTO(399L, Timestamp.valueOf("2022-09-01 09:01:15"), "that was magical", 7, 1200, 10);
        assertDoesNotThrow(() -> restTemplate.postForObject(BASE_LOG_URL+"?tourId=" + response.getId(), tourLog, TourLogDTO.class));
        //set properties of the Log to edit
        tourLog.setComment("updated comment");
        tourLog.setDifficulty(8);
        tourLog.setRating(6);
        tourLog.setTotalTime(4312);
       // tourLog.setDateTime(Timestamp.valueOf("2022-09-01 09:01:15"));
        // edit Log
        assertDoesNotThrow(() -> restTemplate.put(BASE_LOG_URL + "/399", tourLog,TourLogDTO.class));
    }
}


