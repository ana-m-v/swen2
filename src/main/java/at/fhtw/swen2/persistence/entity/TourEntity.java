package at.fhtw.swen2.persistence.entity;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TransportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private  String name;
    @Column
    private String description;
    @Column
    private String fromDestination ;
    @Column
    private String toDestination;
    @Column
    @Enumerated(EnumType.STRING)
    private TransportType transportType;
    @Column
    private double distance;
    @Column
    private int estimatedTime;
    @Column(length = Integer.MAX_VALUE)
    private String routeImage;

    public TourEntity(TourDTO tourDTO) {
        this.id = tourDTO.getId();
        this.name = tourDTO.getName();
        this.description = tourDTO.getDescription();
        this.fromDestination = tourDTO.getFrom();
        this.toDestination = tourDTO.getTo();
        this.transportType = tourDTO.getTransportType();
        this.distance = tourDTO.getDistance();
        this.estimatedTime = tourDTO.getTime();
        this.routeImage = tourDTO.getRouteImage();
    }

    // getters and setters
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public TourEntity(String name, String description, String from, String to, TransportType transportType, double distance, int time, String routeImage) {
        this.name = name;
        this.description = description;
        this.fromDestination = from;
        this.toDestination = to;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = time;
        this.routeImage = routeImage;
    }

    ;

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
        return fromDestination;
    }

    public void setFrom(String from) {
        this.fromDestination = from;
    }

    public String getTo() {
        return toDestination;
    }

    public void setTo(String to) {
        this.toDestination = to;
    }

    public TransportType getTransportType() {
        return transportType;
    }

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
        return estimatedTime;
    }

    public void setTime(int time) {
        this.estimatedTime = time;
    }

    public String getRouteImage() {
        return routeImage;
    }

    public void setRouteImage(String routeImage) {
        this.routeImage = routeImage;
    }
}
