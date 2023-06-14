package at.fhtw.swen2.presentation.view;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TourViewTest {
    private TourDTO tourDTO = new TourDTO();
    private TourLogDTO tourLogDTO = new TourLogDTO();
    private List<TourLogDTO> tourLogs;
    private TourView tourView;
    @BeforeEach
    void setUp() {
        tourLogs = new ArrayList<>();
        tourLogs.add(new TourLogDTO(/* log data */));
        tourDTO = new TourDTO(0L, "Tour Name", "Tour Description", "From", "To",
                TransportType.shortest, 100.0, 60, "route_image.jpg", 1, 5, tourLogs);
        tourView = new TourView(tourDTO);
    }

    @Test
    void initialize() {
    }

    @Test
    void TestSubmitButtonAction() {
    }
}