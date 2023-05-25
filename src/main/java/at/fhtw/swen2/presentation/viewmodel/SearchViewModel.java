package at.fhtw.swen2.presentation.viewmodel;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class SearchViewModel {

    @Autowired
    TourListViewModel tourListViewModel;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseTourUrl = "http://localhost:8080/tours";
    private final String baseTourLogUrl = "http://localhost:8080/tourlogs";

    private SimpleStringProperty searchString = new SimpleStringProperty();

    public String getSearchString() {
        return searchString.get();
    }

    public SimpleStringProperty searchStringProperty() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString.set(searchString);
    }

    private final ObservableList<TourDTO> tours = FXCollections.observableArrayList();

    public ObservableList<TourDTO> getTours() {
        return tours;
    }

    private final ObservableList<TourLogDTO> tourLogs = FXCollections.observableArrayList();

    public ObservableList<TourLogDTO> getTourLogs() {
        return tourLogs;
    }

//    public void search() {
//        try {
//            String url = baseUrl + "/search?name=" + searchString.get();
//            ResponseEntity<TourDTO[]> response = restTemplate.getForEntity(url, TourDTO[].class);
//            List<TourDTO> matchingTours = Arrays.asList(response.getBody());
//            tours.addAll(matchingTours);
////            tourTable.getItems().setAll(matchingTours);
//        } catch (RestClientException e) {
//            e.printStackTrace();
//        }
//    }

    public void search() {
        try {
            tours.clear();
            String tourUrl = baseTourUrl + "/search?forSearchString=" + searchString.get();
            ResponseEntity<TourDTO[]> tourResponse = restTemplate.getForEntity(tourUrl, TourDTO[].class);
            List<TourDTO> matchingTours = Arrays.asList(tourResponse.getBody());
            tours.addAll(matchingTours);

            String tourLogUrl = baseTourLogUrl + "/search?comment=" + searchString.get();
            ResponseEntity<TourLogDTO[]> tourLogResponse = restTemplate.getForEntity(tourLogUrl, TourLogDTO[].class);
            List<TourLogDTO> matchingTourLogs = Arrays.asList(tourLogResponse.getBody());
            tourLogs.addAll(matchingTourLogs);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }
}
