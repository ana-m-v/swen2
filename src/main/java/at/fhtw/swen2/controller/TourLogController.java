package at.fhtw.swen2.controller;

import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import at.fhtw.swen2.service.TourLogService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

@Controller
@RestController
@RequestMapping("/tourlogs")
public class TourLogController {

    @Autowired
    private TourLogService tourLogService;
    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);
   // private final String apiKey = "7qYmV178joYFWTKkfBejFaCYjjAJ0b90";

    @GetMapping
    public List<TourLogDTO> getAllTourLogs() {
        return tourLogService.getAllTourLogs();
    }

    @GetMapping("/{id}")
    public TourLogDTO getTourLogById(@PathVariable Long id) {
        return tourLogService.getTourLogById(id);
    }

    @PostMapping
    public ResponseEntity<TourLogDTO> createTourLog(@RequestBody TourLogDTO tourLogDto) {

        System.out.println("IN CREATE tourLog in viewmodel");
        int totalTime = tourLogDto.getTotalTime();
        Timestamp dateTime = tourLogDto.getDateTime();
        // Validate user input, maybe put in class later on, replaces everything except listed chars
        int rating = tourLogDto.getRating();
        System.out.println("User input rating: " + rating);
        int difficulty = tourLogDto.getDifficulty();
        String comment = tourLogDto.getComment();
        // replace needed for html parsing
     //   from = from.replace(" ", "%20");
     //   to = to.replace(" ", "%20");
/*
        try {
            double distance;
            int estimatedTime;
            String imgUrl;

            // Call getRouteInfo to get the distance and estimated time
            Map<String, Object> routeInfo = getRouteInfo(from, to, transportType);
            if (routeInfo != null) {
                distance = (double) routeInfo.get("distance");
                estimatedTime = (int) routeInfo.get("time");
                String session = (String) routeInfo.get("session");
                Double ulLat = (Double) routeInfo.get("ulLat");
                Double ulLng = (Double) routeInfo.get("ulLng");
                Double lrLat = (Double) routeInfo.get("lrLat");
                Double lrLng = (Double) routeInfo.get("lrLng");

                String ulLatString = ulLat.toString();
                String ulLngString = ulLng.toString();
                String lrLatString = lrLat.toString();
                String lrLngString = lrLng.toString();

                System.out.println("ULLAT " + ulLat);

                imgUrl = String.format("https://www.mapquestapi.com/staticmap/v5/map?key=%s&size=600,400&boundingBox=%s,%s,%s,%s&session=%s",
                        apiKey, ulLatString, ulLngString, lrLatString, lrLngString, session);
                System.out.println("route info " + routeInfo);
            } else {
                logger.error("Error getting route information");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            } */

            tourLogDto.setTotalTime(totalTime);
            tourLogDto.setDateTime(dateTime);
            tourLogDto.setRating(rating);
            tourLogDto.setDifficulty(difficulty);
            tourLogDto.setComment(comment);
            tourLogService.createTourLog(tourLogDto);
            //change http
            return new ResponseEntity<>(tourLogDto, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public void deleteTourLog(@PathVariable Long id) {
        System.out.println("in controller delete tourLog : " + id);

        tourLogService.deleteTourLog(id);
    }
}
