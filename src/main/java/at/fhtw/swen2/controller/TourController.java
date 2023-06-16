package at.fhtw.swen2.controller;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import at.fhtw.swen2.service.TourService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

@Controller
@RestController
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private TourService tourService;
    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);
    private final String apiKey = "7qYmV178joYFWTKkfBejFaCYjjAJ0b90";

    @GetMapping
    public List<TourDTO> getAllTours() {
        return tourService.getAllTours();
    }

    @GetMapping("/{id}")
    public TourDTO getTourById(@PathVariable Long id) {
        return tourService.getTourById(id);
    }

    @PostMapping
    public ResponseEntity<TourDTO> createTour(@RequestBody TourDTO tourDto) {

        System.out.println("IN CREATE in viewmodel");
        String from = tourDto.getFrom();
        String to = tourDto.getTo();
        String transportType = valueOf(tourDto.getTransportType());
        System.out.println("Transport type in Post " + transportType);
        // replace needed for html parsing
        from = from.replace(" ", "%20");
        to = to.replace(" ", "%20");

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
            }

            tourDto.setDistance(distance);
            tourDto.setTime(estimatedTime);
            tourDto.setRouteImage(imgUrl);

            tourService.createTour(tourDto);

            return new ResponseEntity<>(tourDto, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating tour", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateEditedTour(@PathVariable("id") String tourId, @RequestBody TourDTO tourDto) {

        // find that tour to be edited
        TourDTO existingTour = tourService.getTourById(Long.valueOf(tourId));
        if (existingTour != null) {
            // Update the tour with the new data
            existingTour.setName(tourDto.getName());
            existingTour.setDescription(tourDto.getDescription());
            existingTour.setFrom(tourDto.getFrom());
            existingTour.setTo(tourDto.getTo());
            // Update other properties new map

            System.out.println("IN CREATE in viewmodel");
            String transportType = valueOf(tourDto.getTransportType());
            String from = tourDto.getFrom();
            String to = tourDto.getTo();

            // replace needed for html parsing
            from = from.replace(" ", "%20");
            to = to.replace(" ", "%20");

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
                }
                existingTour.setDistance(distance);
                existingTour.setTime(estimatedTime);
                existingTour.setRouteImage(imgUrl);

                tourService.updateTour(existingTour);
            } catch (Exception e) {
                logger.error("Error updating tour", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            // Return a success response
            return new ResponseEntity<>(tourDto, HttpStatus.OK);
        } else {
            // If the tour with the given ID doesn't exist, return an error response
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/routeinfo")
    public Map<String, Object> getRouteInfo(@RequestParam String from, @RequestParam String to, @RequestParam String transportType) {
        from = from.replace(" ", "%20");
        to = to.replace(" ", "%20");
        String apiUrl = String.format("http://www.mapquestapi.com/directions/v2/route?key=%s&from=%s&to=%s&routeType=%s",
                apiKey, from, to, transportType);
        logger.debug("IN GET ROUTE INFO STRING" + apiUrl);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

            logger.debug("IN GET ROUTE INFO");

            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONObject route = jsonObject.getJSONObject("route");

            double distance = route.getDouble("distance");
            logger.debug("distance " + distance);

            int estimatedTime = route.getInt("time");
            logger.debug("time " + estimatedTime);

            String session = route.getString("sessionId");

            JSONObject box = route.getJSONObject("boundingBox");

            System.out.println("bounding box " + box);

            JSONObject ul = box.getJSONObject("ul");
            JSONObject lr = box.getJSONObject("lr");

            double ulLng = ul.getDouble("lng");
            double ulLat = ul.getDouble("lat");
            double lrLng = lr.getDouble("lng");
            double lrLat = lr.getDouble("lat");

            String transport = route.getString("options");

            Map<String, Object> routeInfo = new HashMap<>();
            routeInfo.put("distance", distance);
            routeInfo.put("time", estimatedTime);
            routeInfo.put("options", transport);
            routeInfo.put("lrLng", lrLng);
            routeInfo.put("lrLat", lrLat);
            routeInfo.put("ulLng", ulLng);
            routeInfo.put("ulLat", ulLat);
            routeInfo.put("session", session);

            return routeInfo;
        } catch (Exception e) {
            logger.error("Error getting route information", e);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) {
        System.out.println("in controller delete : " + id);
        tourService.deleteTour(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TourDTO>> searchToursByName(@RequestParam("forSearchString") String string) {
        List<TourDTO> matchingTours = tourService.searchQuery(string);
        return ResponseEntity.ok(matchingTours);
    }

    @GetMapping("/saveaspdf/{id}")
    public ResponseEntity<TourDTO> createPDF(@PathVariable Long id) {
        TourDTO tour = tourService.getTourById(id);
        return ResponseEntity.ok(tour);
    }

    @GetMapping("/createstats")
    public List<TourDTO> createPDFStatistic() {
        return tourService.getAllTours();
    }

    @GetMapping("/export")
    public ResponseEntity<List<TourDTO>> exportToursWithLogs() {
        List<TourDTO> tours = tourService.getAllTours();
        return ResponseEntity.ok(tours);
    }
}
