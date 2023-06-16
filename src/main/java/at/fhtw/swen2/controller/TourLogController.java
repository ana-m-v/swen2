package at.fhtw.swen2.controller;

import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.presentation.Swen2ApplicationFX;
import at.fhtw.swen2.presentation.viewmodel.TourLogListViewModel;
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
    @Autowired
    TourLogListViewModel tourLogListViewModel;
    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);

    @GetMapping
    public ResponseEntity<List<TourLogDTO>> getAllTourLogs() {
        try {
            List<TourLogDTO> tourLogs = tourLogService.getAllTourLogs();
            return ResponseEntity.ok(tourLogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<TourLogDTO> getTourLogById(@PathVariable Long id) {
        try {
            TourLogDTO tourLog = tourLogService.getTourLogById(id);
            if (tourLog != null) {
                return ResponseEntity.ok(tourLog);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/tours/{id}")
    public ResponseEntity<List<TourLogDTO>> getTourLogsForTour(@PathVariable("id") Long tourId) {
        try {
            List<TourLogDTO> tourLogs = tourLogService.getTourLogsForTour(tourId);
            return ResponseEntity.ok(tourLogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping
    public ResponseEntity<TourLogDTO> createTourLog(@RequestParam Long tourId, @RequestBody TourLogDTO tourLogDto) {
        try {
            int totalTime = tourLogDto.getTotalTime();
            Timestamp dateTime = tourLogDto.getDateTime();
            int rating = tourLogDto.getRating();
            int difficulty = tourLogDto.getDifficulty();
            String comment = tourLogDto.getComment();

            tourLogDto.setTotalTime(totalTime);
            tourLogDto.setDateTime(dateTime);
            tourLogDto.setRating(rating);
            tourLogDto.setDifficulty(difficulty);
            tourLogDto.setComment(comment);

            tourLogService.createTourLog(tourLogDto, tourId);
            tourLogListViewModel.refreshTourLogs();
            return new ResponseEntity<>(tourLogDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTourLog(@PathVariable Long id) {
        try {
            tourLogService.deleteTourLog(id);
            tourLogListViewModel.refreshTourLogs();
            return ResponseEntity.ok("Tour log deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete tour log: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourLogDTO> updateTourLog(@PathVariable("id") String tourLogId, @RequestBody TourLogDTO tourLogDto) {

        TourLogDTO existingTourLog = tourLogService.getTourLogById(Long.valueOf(tourLogId));
        if (existingTourLog != null) {
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
            tourLogListViewModel.refreshTourLogs();
            return new ResponseEntity<>(tourLogDto, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TourLogDTO>> searchToursByComment(@RequestParam("forSearchString") String comment) {
        try {
            List<TourLogDTO> matchingTourLogs = tourLogService.searchTourLogsByComment(comment);
            return ResponseEntity.ok(matchingTourLogs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

//@Controller
//@RestController
//@RequestMapping("/tourlogs")
//public class TourLogController {
//
//    @Autowired
//    private TourLogService tourLogService;
//    private Logger logger = LoggerFactory.getLogger(Swen2ApplicationFX.class);
//
//    @GetMapping
//    public List<TourLogDTO> getAllTourLogs() {
//        return tourLogService.getAllTourLogs();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<TourLogDTO> getTourLogById(@PathVariable Long id) {
//        try {
//            TourLogDTO tourLog = tourLogService.getTourLogById(id);
//            if (tourLog != null) {
//                return ResponseEntity.ok(tourLog);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping("/tours/{id}")
//    public ResponseEntity<List<TourLogDTO>> getTourLogsForTour(@PathVariable("id") Long tourId) {
//        List<TourLogDTO> tourLogs = tourLogService.getTourLogsForTour(tourId);
//        return ResponseEntity.ok(tourLogs);
//    }
//    @PostMapping
//    public ResponseEntity<TourLogDTO> createTourLog(@RequestParam Long tourId, @RequestBody TourLogDTO tourLogDto) {
//
//        int totalTime = tourLogDto.getTotalTime();
//        Timestamp dateTime = tourLogDto.getDateTime();
//        int rating = tourLogDto.getRating();
//        int difficulty = tourLogDto.getDifficulty();
//        String comment = tourLogDto.getComment();
//
//        tourLogDto.setTotalTime(totalTime);
//        tourLogDto.setDateTime(dateTime);
//        tourLogDto.setRating(rating);
//        tourLogDto.setDifficulty(difficulty);
//        tourLogDto.setComment(comment);
//
//        tourLogService.createTourLog(tourLogDto, tourId);
//
//        return new ResponseEntity<>(tourLogDto, HttpStatus.CREATED);
//
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteTourLog(@PathVariable Long id) {
//        tourLogService.deleteTourLog(id);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<TourLogDTO> updateTourLog(@PathVariable("id") String tourLogId, @RequestBody TourLogDTO tourLogDto) {
//
//        TourLogDTO existingTourLog = tourLogService.getTourLogById(Long.valueOf(tourLogId));
//        if (existingTourLog != null) {
//            existingTourLog.setDateTime(tourLogDto.getDateTime());
//            existingTourLog.setTotalTime(tourLogDto.getTotalTime());
//            existingTourLog.setRating(tourLogDto.getRating());
//            existingTourLog.setDifficulty(tourLogDto.getDifficulty());
//            existingTourLog.setComment(tourLogDto.getComment());
//            try {
//                tourLogService.updateTourLog(existingTourLog);
//            } catch (Exception e) {
//                logger.error("Error updating tour", e);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//            }
//            return new ResponseEntity<>(tourLogDto, HttpStatus.OK);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<TourLogDTO>> searchToursByComment(@RequestParam("forSearchString") String comment) {
//        List<TourLogDTO> matchingTourLogs = tourLogService.searchTourLogsByComment(forSearchString);
//        return ResponseEntity.ok(matchingTourLogs);
//    }
//}


