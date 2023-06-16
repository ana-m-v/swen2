package at.fhtw.swen2.controller;

import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import at.fhtw.swen2.service.TourLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;


@Controller
@RestController
@RequestMapping("/tourlogs")
public class TourLogController {

    @Autowired
    private TourLogService tourLogService;
    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);

    @GetMapping
    public List<TourLogDTO> getAllTourLogs() {
        return tourLogService.getAllTourLogs();
    }

    @GetMapping("/{id}")
    public TourLogDTO getTourLogById(@PathVariable Long id) {
        return tourLogService.getTourLogById(id);
    }

    @GetMapping("/tours/{id}")
    public ResponseEntity<List<TourLogDTO>> getTourLogsForTour(@PathVariable("id") Long tourId) {
        List<TourLogDTO> tourLogs = tourLogService.getTourLogsForTour(tourId);
        return ResponseEntity.ok(tourLogs);
    }
    @PostMapping
    public ResponseEntity<TourLogDTO> createTourLog(@RequestParam Long tourId, @RequestBody TourLogDTO tourLogDto) {

        System.out.println("IN CREATE tourLog in viewmodel");
        int totalTime = tourLogDto.getTotalTime();
        Timestamp dateTime = tourLogDto.getDateTime();
        int rating = tourLogDto.getRating();
        System.out.println("User input rating: " + rating);
        int difficulty = tourLogDto.getDifficulty();
        String comment = tourLogDto.getComment();

        tourLogDto.setTotalTime(totalTime);
        tourLogDto.setDateTime(dateTime);
        tourLogDto.setRating(rating);
        tourLogDto.setDifficulty(difficulty);
        tourLogDto.setComment(comment);

        tourLogService.createTourLog(tourLogDto, tourId);
        //change http
        return new ResponseEntity<>(tourLogDto, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public void deleteTourLog(@PathVariable Long id) {
        System.out.println("in controller delete tourLog : " + id);

        tourLogService.deleteTourLog(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourLogDTO> updateTourLog(@PathVariable("id") String tourLogId, @RequestBody TourLogDTO tourLogDto) {

        // find that tour to be edited
        TourLogDTO existingTourLog = tourLogService.getTourLogById(Long.valueOf(tourLogId));
        if (existingTourLog != null) {
            // Update the tour with the new data
            existingTourLog.setDateTime(tourLogDto.getDateTime());
            existingTourLog.setTotalTime(tourLogDto.getTotalTime());
            existingTourLog.setRating(tourLogDto.getRating());
            existingTourLog.setDifficulty(tourLogDto.getDifficulty());
            existingTourLog.setComment(tourLogDto.getComment());
            try {
                tourLogService.updateTourLog(existingTourLog);
            } catch (Exception e) {
                logger.error("Error updating tour", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            // Return a success response
            return new ResponseEntity<>(tourLogDto, HttpStatus.OK);
        } else {
            // If the tour with the given ID doesn't exist, return an error response
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TourLogDTO>> searchToursByComment(@RequestParam("comment") String comment) {
        List<TourLogDTO> matchingTourLogs = tourLogService.searchTourLogsByComment(comment);
        return ResponseEntity.ok(matchingTourLogs);
    }
}
