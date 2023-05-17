package at.fhtw.swen2.model;

import at.fhtw.swen2.persistence.entity.TourEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Data
@Builder
@Component
public class TourDTO {
    private long id;
    private String name;
    private String description;
    private String from;
    private String to;
    private TransportType transportType;
    private double distance;
    private int time;
    private String routeImage;
//    private List<TourLogDTO> tourLogs;

    public TourDTO(){};

    public TourDTO(Long id, String name, String description, String from, String to, TransportType transportType, double distance, int time, String routeImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.distance = distance;
        this.time = time;
        this.routeImage = routeImage;
//        this.tourLogs = tourLogs;
    }

    public TourDTO(TourEntity tour) {
        this.id = tour.getId();
        this.name = tour.getName();
        this.description = tour.getDescription();
        this.from = tour.getFrom();
        this.to = tour.getTo();
        this.transportType = tour.getTransportType();
        this.distance = tour.getDistance();
        this.time = tour.getTime();
        this.routeImage = tour.getRouteImage();
//        this.tourLogs = tour.getTourLogs().stream()
//                .map(tourLogEntity -> new TourLogDTO(tourLogEntity.getDateTime(),
//                        tourLogEntity.getComment(),
//                        tourLogEntity.getDifficulty(),
//                        tourLogEntity.getTotalTime(),
//                        tourLogEntity.getRating()))
//                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    // Return an ObservableList<String> containing the transport types for checkBox autofill
 /*   public ObservableList<TransportType> getTransportTypeObservableList() {
        TransportType[] transportTypes = TransportType.values();
        return FXCollections.observableArrayList(transportTypes);
    }  */

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getRouteImage() {
        return routeImage;
    }

    public void setRouteImage(String routeImage) {
        this.routeImage = routeImage;
    }


}
