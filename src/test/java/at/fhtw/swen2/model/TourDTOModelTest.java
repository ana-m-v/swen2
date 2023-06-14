package at.fhtw.swen2.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import at.fhtw.swen2.persistence.entity.TourEntity;
import at.fhtw.swen2.persistence.entity.TourLogEntity;


public class TourDTOModelTest {
    private TourDTO tourDTO;
    private List<TourLogDTO> tourLogs;

    @BeforeEach
    public void setup() {
        // Initialize a TourDTO object with sample data for testing
        tourLogs = new ArrayList<>();
        tourLogs.add(new TourLogDTO(/* log data */));
        tourDTO = new TourDTO(0L, "Tour Name", "Tour Description", "From", "To",
                TransportType.shortest, 100.0, 60, "route_image.jpg", 1, 5, tourLogs);
    }

    @Test
    public void testConstructorWithTourEntity() {
        TourEntity tourEntity = new TourEntity();
        //needed to pass an empty list of Logs
        List<TourLogEntity> tourLogsEntity = new AbstractList<TourLogEntity>() {
            @Override
            public TourLogEntity get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };
        tourEntity.setId(1L);
        tourEntity.setName("Tour Name");
        tourEntity.setDescription("Tour Description");
        tourEntity.setFromDestination("From");
        tourEntity.setToDestination("To");
        tourEntity.setTransportType(TransportType.fastest);
        tourEntity.setDistance(100.0);
        tourEntity.setEstimatedTime(60);
        tourEntity.setRouteImage("route_image.jpg");
        tourEntity.setTourLogs(tourLogsEntity);

        TourDTO convertedTourDTO = new TourDTO(tourEntity);

        Assertions.assertEquals(tourEntity.getId(), convertedTourDTO.getId());
        Assertions.assertEquals(tourEntity.getName(), convertedTourDTO.getName());
    }
}