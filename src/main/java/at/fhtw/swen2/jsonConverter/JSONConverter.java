package at.fhtw.swen2.jsonConverter;

import at.fhtw.swen2.controller.TourController;
import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class JSONConverter {
    TourController tourController = new TourController();
    public void writeJSONFile(String tours, String tourLogs) throws IOException {
        // Get the current timestamp
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = currentTime.format(formatter);
        FileWriter file = new FileWriter("src/main/java/at/fhtw/swen2/JSONconverter/JSONFiles/tours_" + timestamp + ".json");
        file.write(tours);
        file.write("\n");
        file.write(tourLogs);
        file.close();
    }
        //
    public void importJSONFile(String filename) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        filename = "src/main/java/at/fhtw/swen2/jsonConverter/JSONFiles/tours_2023-06-14_15-23-17.json";
        BufferedReader reader;
        String tourLogs = "";
        String tours = "";
        try {
            reader = new BufferedReader(new FileReader(filename));
            tours = reader.readLine();
            //System.out.println("Readed: " + tours);
            while (tours != null) {
                System.out.println(tours);
                // read next line = tourLogs
                tourLogs = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
//JSON from file to Object
            List<TourDTO> tourList = Collections.singletonList(mapper.readValue(tours, TourDTO.class));
          // needed?  List <TourLogDTO> tourLogList = Collections.singletonList(mapper.readValue(tourLogs, TourLogDTO.class));

           // System.out.println(tourList);
            // post tour by tour
            for (int i = 0; i < tourList.size(); i++) {


                //System.out.println(tourList.get(i));
                tourController.createTour(tourList.get(i));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//JSON from String to Object
        // = mapper.readValue(tourLogs, TourLogDTO.class)
    }
}
