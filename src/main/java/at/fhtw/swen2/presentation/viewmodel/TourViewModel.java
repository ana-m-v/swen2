package at.fhtw.swen2.presentation.viewmodel;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TransportType;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TourViewModel {

    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);
    @Autowired
    TourListViewModel tourListViewModel;

    @Autowired
    TourLogListViewModel tourLogListViewModel;
    @Autowired
    TourLogViewModel tourLogViewModel;
    private TourDTO tourDTO;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/tours";
    private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty from = new SimpleStringProperty();
    private SimpleStringProperty to = new SimpleStringProperty();
    private SimpleObjectProperty<TransportType> transportType = new SimpleObjectProperty<>();
    private SimpleIntegerProperty time = new SimpleIntegerProperty();
    private SimpleDoubleProperty distance = new SimpleDoubleProperty();
    private SimpleIntegerProperty popularity = new SimpleIntegerProperty();
    private SimpleIntegerProperty childFriendly = new SimpleIntegerProperty();

    public int getPopularity() {
        return popularity.get();
    }

    public SimpleIntegerProperty popularityProperty() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity.set(popularity);
    }

    public int getChildFriendly() {
        return childFriendly.get();
    }

    public SimpleIntegerProperty childFriendlyProperty() {
        return childFriendly;
    }

    public void setChildFriendly(int childFriendly) {
        this.childFriendly.set(childFriendly);
    }

    private SimpleStringProperty routeImage = new SimpleStringProperty();

    private ObjectProperty<TourDTO> selectedTour = new SimpleObjectProperty<>();

    public TourViewModel(TourDTO tourDTO) {
        this.tourDTO = tourDTO;
        this.id = new SimpleLongProperty(tourDTO.getId());
        this.name = new SimpleStringProperty(tourDTO.getName());
        this.description = new SimpleStringProperty(tourDTO.getDescription());
        this.from = new SimpleStringProperty(tourDTO.getFrom());
        this.to = new SimpleStringProperty(tourDTO.getTo());
        this.time = new SimpleIntegerProperty(tourDTO.getTime());
        this.routeImage = new SimpleStringProperty(tourDTO.getRouteImage());
        this.distance = new SimpleDoubleProperty(tourDTO.getDistance());
        this.popularity = new SimpleIntegerProperty(tourDTO.getPopularity());
        this.childFriendly = new SimpleIntegerProperty(tourDTO.getChildFriendly());

    }

    // getter and setter for selectedTour
    public TourDTO getSelectedTour() {
        return selectedTour.get();
    }

    public void setSelectedTour(TourDTO tour) {
        selectedTour.set(tour);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getDistance() {
        return distance.get();
    }

    public SimpleDoubleProperty distanceProperty() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance.set(distance);
    }

    public String getFrom() {
        return from.get();
    }

    public SimpleStringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public String getTo() {
        return to.get();
    }

    public SimpleStringProperty toProperty() {
        return to;
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public TransportType getTransportType() {
        return transportType.get();
    }

    public SimpleObjectProperty<TransportType> transportTypeProperty() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType.set(transportType);
    }

    public TourDTO getTourDTO() {
        return tourDTO;
    }

    public void setTourDTO(TourDTO tourDTO) {
        this.tourDTO = tourDTO;
    }

    public int getTime() {
        return time.get();
    }

    public SimpleIntegerProperty timeProperty() {
        return time;
    }

    public void setTime(int time) {
        this.time.set(time);
    }

    public String getRouteImage() {
        return routeImage.get();
    }

    public SimpleStringProperty routeImageProperty() {
        return routeImage;
    }

    public void setRouteImage(String routeImage) {
        this.routeImage.set(routeImage);
    }

    public TourDTO createTour() {
        TourDTO tour = TourDTO.builder().name(getName()).description(getDescription()).distance(getDistance()).time(getTime()).from(getFrom()).to(getTo()).transportType(getTransportType()).routeImage(getRouteImage()).build();
        try {
            TourDTO response = restTemplate.postForObject(baseUrl, tour, TourDTO.class);
//            tourListViewModel.getTours().add(response);
            return response;
        } catch (RestClientException e) {
            logger.error("Error with createTour() in TourViewModel");
            e.printStackTrace();
        }
        return null;
    }
    public void updateEditedTour(TourDTO tour) {
        try {
            restTemplate.put(baseUrl + "/" + tour.getId(), tour);
            tourListViewModel.refreshTours();
        } catch (RestClientException e) {
            logger.error("Error with updateEditedTour() in TourViewModel");
            e.printStackTrace();
        }
    }

    public void deleteTour(TourDTO tourDTO) {
        try {
            System.out.println("in viewmodel delete : " + tourDTO.getId());
            restTemplate.delete(baseUrl + "/" + tourDTO.getId());
            tourListViewModel.getTours().remove(tourDTO);
            tourLogListViewModel.refreshTourLogs();
        } catch (RestClientException e) {
            logger.error("Error with deleteTour() in TourViewModel");
            e.printStackTrace();
        }
    }
}
