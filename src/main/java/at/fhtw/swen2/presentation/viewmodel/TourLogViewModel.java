package at.fhtw.swen2.presentation.viewmodel;

import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.model.TransportType;
import at.fhtw.swen2.persistence.entity.TourEntity;
import javafx.beans.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Component
public class TourLogViewModel {

    @Autowired
    TourLogListViewModel tourLogListViewModel;
    private TourLogDTO tourLogDTO;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/tourlogs";
    private SimpleLongProperty id = new SimpleLongProperty();
  //  private SimpleLongProperty tourId = new SimpleLongProperty();

    private SimpleIntegerProperty totalTime = new SimpleIntegerProperty();
    private SimpleIntegerProperty rating = new SimpleIntegerProperty();
    private SimpleIntegerProperty difficulty = new SimpleIntegerProperty();
    private SimpleStringProperty comment = new SimpleStringProperty();

    private SimpleObjectProperty<Timestamp> dateTime = new SimpleObjectProperty<>();

 //   private SimpleStringProperty routeImage = new SimpleStringProperty();

    public Timestamp getDateTime() {
        return dateTime.get();
    }

    public SimpleObjectProperty<Timestamp> dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime.set(dateTime);
    }

    private ObjectProperty<TourLogDTO> selectedTourLog = new SimpleObjectProperty<>();

    public TourLogViewModel(TourLogDTO tourLogDTO) {
        this.tourLogDTO = tourLogDTO;
        this.id = new SimpleLongProperty(tourLogDTO.getId());
        this.totalTime = new SimpleIntegerProperty(tourLogDTO.getTotalTime());
        this.rating = new SimpleIntegerProperty(tourLogDTO.getRating());
        this.difficulty = new SimpleIntegerProperty(tourLogDTO.getDifficulty());
        this.comment = new SimpleStringProperty(tourLogDTO.getComment());
       // this.tourId = new SimpleLongProperty(tourLogDTO.getTourId());

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

  /*  public long getTourId() {
        return tourId.get();
    }

    public SimpleLongProperty tourIdProperty() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId.set(tourId);
    }
*/
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
    public void createTourLog() {
        TourLogDTO tour = TourLogDTO.builder().dateTime(getDateTime()).comment(getComment())
                .difficulty(getDifficulty()).rating(getRating()).totalTime(getTotalTime()).build();
        try {
            TourLogDTO response = restTemplate.postForObject(baseUrl, tour, TourLogDTO.class);
            tourLogListViewModel.getTourLogs().add(response); // Add the newly created tour to the list of tours
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }
}
