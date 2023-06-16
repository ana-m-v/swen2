package at.fhtw.swen2.controller;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

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
    public void createTourLog_Successful() {
        // create Tour to attach Log
        TourDTO tour = new TourDTO(0L, "Tour for Logs", "created tour log test", "Innsbruck", "London", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);

        TourDTO response = restTemplate.postForObject(BASE_URL, tour, TourDTO.class);
        assertEquals(tour.getName(), response.getName());
        //create Log
        TourLogDTO tourLog = new TourLogDTO(0L, Timestamp.valueOf("2022-09-01 09:01:15"), "that was magical", 7, 1200, 10);
        assertDoesNotThrow(() -> restTemplate.postForObject(BASE_LOG_URL+"?tourId=" + response.getId(), tourLog, TourLogDTO.class));
    }

    @Test
    public void createTourLog_TourNotExist_NotSuccessful() {
        TourLogDTO tourLog = new TourLogDTO(0L, Timestamp.valueOf("2022-09-01 09:01:15"), "that was magical", 7, 1200, 10);
        assertThrows(RestClientException.class, () -> restTemplate.postForObject(BASE_LOG_URL+"?tourId=" + 99999, tourLog, TourDTO.class));
    }

    @Test
    public void createTourLog_TourLogNull_ExceptionThrown() {
        //send empty log -> fail
        assertThrows(RestClientException.class, () -> restTemplate.postForObject(BASE_LOG_URL, null, TourDTO.class));
    }
    @Test
    public void deleteTourLog_Successful() {
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
    public void updateTourLog_ReturnsOkStatus() {
        // create Tour to attach Log
        TourDTO tour = new TourDTO(0L, "Tour for Logs", "bla", "Innsbruck", "London", TransportType.shortest, 1234, 1234, "image.jpg", 5, 5, null);

        TourDTO response = restTemplate.postForObject(BASE_URL, tour, TourDTO.class);
        assertEquals(tour.getName(), response.getName());

        //create Log
        TourLogDTO tourLog = new TourLogDTO();
        tourLog.setDateTime(Timestamp.valueOf("2022-09-01 09:01:15"));
        tourLog.setComment("that was magical");
        tourLog.setTotalTime(3444);
        tourLog.setDifficulty(7);
        tourLog.setRating(10);

        // Add the tour log for the tour

        TourLogDTO tourLogResponse = restTemplate.postForObject(BASE_LOG_URL+"?tourId=" + response.getId(), tourLog, TourLogDTO.class);
        assert tourLogResponse != null;
        System.out.println("update tourlog response comment " + tourLogResponse.getComment());

        //set properties of the Log to edit
        tourLog.setComment("updated comment");
        tourLog.setDifficulty(8);
        tourLog.setRating(6);
        tourLog.setTotalTime(4312);

        // edit Log
        assertDoesNotThrow(() -> {
            assert tourLogResponse != null;
            restTemplate.put(BASE_LOG_URL + "/" + tourLogResponse.getId(), tourLog,TourLogDTO.class);
        });
    }
}


