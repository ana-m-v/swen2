package at.fhtw.swen2.jsonConverter;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.presentation.viewmodel.TourViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JSONConverter {
    @Autowired
    TourViewModel tourViewModel;
    @Autowired
    TourListViewModel tourListViewModel;
    public void writeJSONFile(String tours) throws IOException {
        // Get the current timestamp
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = currentTime.format(formatter);
        FileWriter file = new FileWriter("src/main/java/at/fhtw/swen2/jsonConverter/JSONFiles/tours_" + timestamp + ".json");
        file.write(tours);
        file.close();
        //user feedback
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JSON File created");
        alert.setHeaderText(null);
        alert.setContentText("JSON file generated successfully! \nView: src/main/java/at/fhtw/swen2/jsonConverter/JSONFiles/");
        alert.showAndWait();
    }
        //not working properly
    public void importJSONFile(String filename) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        filename = "src/main/java/at/fhtw/swen2/jsonConverter/JSONFiles/tours_2023-06-16_09-44-11.json";
        BufferedReader reader;
        String tours = "";
        try {
            reader = new BufferedReader(new FileReader(filename));
            tours = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //JSON from JSON String to array of tourDTOs
            List<TourDTO> tourList = Arrays.asList(mapper.readValue(tours, TourDTO[].class));
            System.out.println("tours from json file" + tours);
            // post tour by tour
            for (int i = 0; i < tourList.size(); i++) {
                TourDTO tour = tourList.get(i);
                tourViewModel = new TourViewModel(tour);
                tourViewModel.createTour();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
