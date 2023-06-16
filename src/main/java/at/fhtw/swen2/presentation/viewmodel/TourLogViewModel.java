package at.fhtw.swen2.presentation.viewmodel;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Component
public class TourLogViewModel {

    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);
    @Autowired
    TourLogListViewModel tourLogListViewModel;

    @Autowired
    TourListViewModel tourListViewModel;
    private TourLogDTO tourLogDTO;
    private ObjectProperty<TourLogDTO> selectedTourLog = new SimpleObjectProperty<>();

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/tourlogs";
    private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleIntegerProperty totalTime = new SimpleIntegerProperty();
    private SimpleIntegerProperty rating = new SimpleIntegerProperty();
    private SimpleIntegerProperty difficulty = new SimpleIntegerProperty();
    private SimpleStringProperty comment = new SimpleStringProperty();
    private SimpleStringProperty dateTime = new SimpleStringProperty();

    public String getDateTime() {
        return dateTime.get();
    }

    public SimpleStringProperty dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime.set(dateTime);
    }
//    private SimpleObjectProperty<Timestamp> dateTime = new SimpleObjectProperty<>();

    private SimpleLongProperty tourId = new SimpleLongProperty();

    public void setSelectedTourLog(TourLogDTO tourLog) {
        selectedTourLog.set(tourLog);
    }

    public long getTourId() {
        return tourId.get();
    }

    public SimpleLongProperty tourIdProperty() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId.set(tourId);
    }



    public TourLogViewModel(TourLogDTO tourLogDTO) {
        this.tourLogDTO = tourLogDTO;
        this.id = new SimpleLongProperty(tourLogDTO.getId());
        this.totalTime = new SimpleIntegerProperty(tourLogDTO.getTotalTime());
        this.rating = new SimpleIntegerProperty(tourLogDTO.getRating());
        this.difficulty = new SimpleIntegerProperty(tourLogDTO.getDifficulty());
        this.comment = new SimpleStringProperty(tourLogDTO.getComment());
    }

    // getter and setter for selectedTour
    public TourLogDTO getSelectedTourLog() {
        return selectedTourLog.get();
    }

    public void setSelectedTour(TourLogDTO tourLog) {
        selectedTourLog.set(tourLog);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public int getTotalTime() {
        return totalTime.get();
    }

    public SimpleIntegerProperty totalTimeProperty() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime.set(totalTime);
    }

    public TourLogDTO getTourDTO() {
        return tourLogDTO;
    }

    public void setTourLogDTO(TourLogDTO tourLogDTO) {
        this.tourLogDTO = tourLogDTO;
    }

    public int getRating() {
        return rating.get();
    }

    public SimpleIntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public int getDifficulty() {
        return difficulty.get();
    }

    public SimpleIntegerProperty difficultyProperty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty.set(difficulty);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }
    public void createTourLog(TourDTO tour) {

        TourLogDTO tourLogDTO = TourLogDTO.builder()
                .dateTime(Timestamp.valueOf(getDateTime()))
                .comment(getComment())
                .difficulty(getDifficulty())
                .rating(getRating())
                .totalTime(getTotalTime())
                .build();

        try {
            TourLogDTO response = restTemplate.postForObject(baseUrl + "?tourId=" + tour.getId(), tourLogDTO, TourLogDTO.class);
            tourLogListViewModel.getTourLogs().add(response);
            tourListViewModel.refreshTours();
        } catch (RestClientException e) {
            logger.error("Error in createTourLog() in TourLogViewModel");
            e.printStackTrace();
        }
    }

    public void deleteTourLog(TourLogDTO selectedTourLog) {
        try {
            restTemplate.delete(baseUrl + "/" + selectedTourLog.getId());
            tourLogListViewModel.getTourLogs().remove(selectedTourLog);
            tourLogListViewModel.refreshTourLogs();
            tourListViewModel.refreshTours();
        } catch (RestClientException e) {
            logger.error("Error in deleteTourLog() in TourLogViewModel");
            e.printStackTrace();
        }
    }

    public void updateTourLog(TourLogDTO tourLog) {
        try {
            restTemplate.put(baseUrl + "/" + tourLog.getId(), tourLog);
            //tourListViewModel.getTours().add(response); // Add the newly created tour to the list of tours
            tourLogListViewModel.refreshTourLogs();
            tourListViewModel.refreshTours();
        } catch (RestClientException e) {
            logger.error("Error in updateTourLog() in TourLogViewModel");
            e.printStackTrace();
        }
    }
}
