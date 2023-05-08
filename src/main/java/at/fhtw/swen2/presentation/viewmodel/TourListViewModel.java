package at.fhtw.swen2.presentation.viewmodel;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.persistence.entity.TourEntity;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class TourListViewModel {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/tours";

    private final ObservableList<TourDTO> tours = FXCollections.observableArrayList();
    private final ObjectProperty<TourDTO> selectedTour = new SimpleObjectProperty<>();

    public ObservableList<TourDTO> getTours() {
        return tours;
    }
    public ObjectProperty<TourDTO> selectedTourProperty() {
        return selectedTour;
    }

    // getter and setter for selectedTour
    public TourDTO getSelectedTour() {
        return selectedTour.get();
    }
    public void setSelectedTour(TourDTO tour) {
        selectedTour.set(tour);
    }
    public void refreshTours() {
        try {
            ResponseEntity<TourDTO[]> response = restTemplate.getForEntity(baseUrl, TourDTO[].class);
            tours.clear();
            tours.addAll(Arrays.asList(response.getBody()));
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    public void onTourSelected(TourDTO tour) {
        selectedTour.set(tour);
    }

    public void deleteTour(TourDTO tourDTO) {
        try {
            System.out.println("in viewmodel delete : " + tourDTO.getId());
            restTemplate.delete(baseUrl + "/" + tourDTO.getId());
            tours.remove(tourDTO);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }
}
