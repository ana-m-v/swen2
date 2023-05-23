//package at.fhtw.swen2.controller;
//
//import at.fhtw.swen2.model.TourDTO;
//import at.fhtw.swen2.model.TourLogDTO;
//
//import at.fhtw.swen2.model.TransportType;
//import at.fhtw.swen2.service.TourService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@RunWith(SpringRunner.class) // or use @ExtendWith(SpringExtension.class) in JUnit 5
//@SpringBootTest
//class TourControllerTest {
//    @Autowired
//    private TourController tourController;
//
//    @Test
//    public void testGetAllTours() {
//        // Create some dummy tours for testing
//        List<TourDTO> dummyTours = new ArrayList<>();
//        List<TourLogDTO> dummyTourLogs = new ArrayList<>();
//
//        dummyTours.add(new TourDTO(1L, "Tour 1", "bla", "Belgrade", "Vienna",
//                TransportType.shortest, 1234.0, 1234, "image.jpg", 5,5, dummyTourLogs));
//        dummyTours.add(new TourDTO(2L, "Tour 2", "bla", "Vienna", "Belgrade",
//                TransportType.fastest, 1234.0, 1234, "image.jpg", 2, 2, dummyTourLogs));
//
//        // Set up the TourService to return the dummy tours
////        tourService.setAllTours(dummyTours);
//
//        // Invoke the getAllTours method on the controller
//        List<TourDTO> result = tourController.getAllTours();
//
//        // Assert that the result is not null and matches the dummy tours
//        assertNotNull(result);
//        assertEquals(dummyTours.size(), result.size());
//        assertEquals(dummyTours.get(0).getName(), result.get(0).getName());
//        assertEquals(dummyTours.get(1).getName(), result.get(1).getName());
//    }
//
//    @Test
//    public void testCreateTour() {
//        // Create a dummy tour for testing
//        List<TourLogDTO> dummyTourLogs = new ArrayList<>();
//
//        TourDTO dummyTour = new TourDTO(1L, "Tour 1", "bla", "Belgrade", "Vienna",
//                TransportType.shortest, 1234.0, 1234, "image.jpg", 5,5, dummyTourLogs);
//
//        // Invoke the createTour method on the controller
//        ResponseEntity<TourDTO> response = tourController.createTour(dummyTour);
//
//        // Assert that the response is not null and has a status of CREATED
//        assertNotNull(response);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//
//        // Assert that the response body matches the dummy tour
//        TourDTO createdTour = response.getBody();
//        assertNotNull(createdTour);
//        assertEquals(dummyTour.getName(), createdTour.getName());
//
//        // Assert that the tour was added to the TourService
//        TourDTO checkTour = tourController.getTourById(3L);
//        assertNotNull(checkTour);
//        assertEquals(dummyTour.getName(), checkTour.getName());
//    }
//
//}
