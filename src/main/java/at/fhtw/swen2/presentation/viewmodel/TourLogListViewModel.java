package at.fhtw.swen2.presentation.viewmodel;

import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class TourLogListViewModel {
    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/tourlogs";

    private final ObservableList<TourLogDTO> tourLogs = FXCollections.observableArrayList();
    private final ObjectProperty<TourLogDTO> selectedTourLog = new SimpleObjectProperty<>();

    public ObservableList<TourLogDTO> getTourLogs() {
        return tourLogs;
    }
    public ObjectProperty<TourLogDTO> selectedTourLogProperty() {
        return selectedTourLog;
    }

    // getter and setter for selectedTour
    public TourLogDTO getSelectedTourLog() {
        return selectedTourLog.get();
    }
    public void setSelectedTourLog(TourLogDTO tour) {
        selectedTourLog.set(tour);
    }
    public void refreshTourLogs() {
        try {
            ResponseEntity<TourLogDTO[]> response = restTemplate.getForEntity(baseUrl, TourLogDTO[].class);
            tourLogs.clear();
            tourLogs.addAll(Arrays.asList(response.getBody()));
        } catch (RestClientException e) {
            logger.error("Error in refreshTourLogs() in TourLogListViewModel");
            e.printStackTrace();
        }
    }

    public void onTourSelected(TourLogDTO tourLog) {
        selectedTourLog.set(tourLog);
    }

}
