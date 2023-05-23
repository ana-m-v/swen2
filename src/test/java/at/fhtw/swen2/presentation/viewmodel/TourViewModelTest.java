//package at.fhtw.swen2.presentation.viewmodel;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import at.fhtw.swen2.model.TourDTO;
//import at.fhtw.swen2.model.TransportType;
//import at.fhtw.swen2.persistence.repository.TourRepository;
//import at.fhtw.swen2.service.TourService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.*;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//import org.mockito.Mock;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.mockito.MockitoAnnotations;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//class TourViewModelTest {
//
//    private TourViewModel tourViewModel;
//    private TourListViewModel tourListViewModel;
//    @Mock
//    private RestTemplate restTemplate;
//    private MockRestServiceServer mockServer;
//
//    @BeforeEach
//    public void setup() {
//        restTemplate = new RestTemplate();
//        mockServer = MockRestServiceServer.createServer(restTemplate);
//        tourViewModel = new TourViewModel(new TourDTO());
////        restTemplate = new RestTemplate();
////        tourViewModel = new TourViewModel(new TourDTO());
//
//    }
//
//    @Test
//    public void createTour_Successful() {
//        // Arrange
//        String baseUrl = "http://localhost:8080/tours";
//        TourDTO expectedResponse = new TourDTO();
//        //restTemplate.setBaseUrl(baseUrl);
//        //restTemplate.setResponse(expectedResponse);
//        // Set up the mock response
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<TourDTO> responseEntity = new ResponseEntity<>(expectedResponse, headers, HttpStatus.OK);
//
//        mockServer.expect(MockRestRequestCreators.request(HttpMethod.POST, baseUrl))
//                .andRespond(MockRestResponseCreators.withEntity(responseEntity));
//
//        tourViewModel.createTour();
//
//        // Assert
//        List<TourDTO> tours = tourListViewModel.getTours();
//        assertEquals(1, tours.size());
//        assertEquals(expectedResponse, tours.get(0));
//    }
//
////    @Test
////    public void createTour_ExceptionThrown() {
////        // Arrange
////        String baseUrl = "http://localhost:8080/tours";
////        restTemplate.setBaseUrl(baseUrl);
////        restTemplate.setExceptionToThrow(new RestClientException("Failed to create tour"));
////
////        // Act and Assert
////        assertThrows(RestClientException.class, () -> tourViewModel.createTour());
////        List<TourDTO> tours = tourListViewModel.getTours();
////        assertEquals(0, tours.size());
////    }
////
////    @Test
////    public void updateEditedTour_Successful() {
////        // Arrange
////        String baseUrl = "http://localhost:8080/tours";
////        TourDTO tour = new TourDTO();
////        restTemplate.setBaseUrl(baseUrl);
////
////
////        // Act
////        tourViewModel.updateEditedTour(tour);
////
////        // Assert
////        List<TourDTO> tours = tourListViewModel.getTours();
////        assertEquals(1, tours.size());
////        assertEquals(tour, tours.get(0));
////    }
////
////    @Test
////    public void updateEditedTour_ExceptionThrown() {
////        // Arrange
////        String baseUrl = "http://localhost:8080/tours";
////        TourDTO tour = new TourDTO();
////        restTemplate.setBaseUrl(baseUrl);
////        restTemplate.setExceptionToThrow(new RestClientException("Failed to update tour"));
////
////        // Act and Assert
////        assertThrows(RestClientException.class, () -> tourViewModel.updateEditedTour(tour));
////        List<TourDTO> tours = tourListViewModel.getTours();
////        assertEquals(0, tours.size());
////    }
//
//}
