package at.fhtw.swen2.service;

import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.persistence.entity.TourEntity;
import at.fhtw.swen2.persistence.entity.TourLogEntity;
import at.fhtw.swen2.persistence.repository.TourLogRepository;
import at.fhtw.swen2.persistence.repository.TourRepository;
import at.fhtw.swen2.presentation.events.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourLogService {

    @Autowired
    private TourLogRepository tourLogRepository;

    @Autowired
    private TourRepository tourRepository;

    public List<TourLogDTO> getAllTourLogs() {
        List<TourLogEntity> tours = tourLogRepository.findAll();
        return tours.stream().map(TourLogDTO::new).collect(Collectors.toList());
    }

    public TourLogDTO getTourLogById(Long id) {
        Optional<TourLogEntity> tour = tourLogRepository.findById(id);
        return tour.map(TourLogDTO::new).orElse(null);
    }

    public TourLogDTO createTourLog(TourLogDTO tourLogDto, long tourId) {
        TourEntity tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id: " + tourId));

        TourLogEntity tourLogEntity = new TourLogEntity(tourLogDto);
        tourLogEntity.setTour(tour);

        // Set other properties of tourLogEntity from tourLogDto

        TourLogEntity savedTourLogEntity = tourLogRepository.save(tourLogEntity);
        tourLogDto.setId(savedTourLogEntity.getId());
        return new TourLogDTO(savedTourLogEntity);
        // Map savedTourLogEntity to TourLogDto and return it
    }
    public void deleteTourLog(Long id) {
        System.out.println("in service delete tour log: " + id);
        tourLogRepository.deleteById(id);
    }

    public TourLogDTO updateTourLog(TourLogDTO existingTourLog) {

        System.out.println("in service update tourLog");

        // Check if the tour log already exists in the database
        Optional<TourLogEntity> optionalTourLog = tourLogRepository.findById(existingTourLog.getId());

        if (optionalTourLog.isPresent()) {
            TourLogEntity tourLog = optionalTourLog.get();

            // Update the existing tour log with the new data
            tourLog.setDateTime(existingTourLog.getDateTime());
            tourLog.setTotalTime(existingTourLog.getTotalTime());
            tourLog.setDifficulty(existingTourLog.getDifficulty());
            tourLog.setRating(existingTourLog.getRating());
            tourLog.setComment(existingTourLog.getComment());

            // Save the updated tour log entity
            tourLog = tourLogRepository.save(tourLog);

            // Set the id of the DTO object
            existingTourLog.setId(tourLog.getId());

            return existingTourLog;
        } else {
            // returns null if there is no tour log
            return null;
        }
    }


    public List<TourLogDTO> getTourLogsForTour(Long tourId) {
        // Implement the logic to fetch tour logs for the given tourId from your data source
        // Return the retrieved tour logs as a list of TourLogDTO objects

        // Assuming you have a data access object (DAO) or repository to interact with the data source
        List<TourLogEntity> tourLogs = tourLogRepository.findByTourId(tourId);

        // Convert TourLog objects to TourLogDTO objects
        List<TourLogDTO> tourLogDTOs = new ArrayList<>();
        for (TourLogEntity tourLog : tourLogs) {
            TourLogDTO tourLogDTO = new TourLogDTO(tourLog);
            tourLogDTOs.add(tourLogDTO);
        }
        return tourLogDTOs;
    }

    public List<TourLogDTO> searchTourLogsByComment(String comment) {
        List<TourLogEntity> matchingTourLogEntities = tourLogRepository.findByCommentContainingIgnoreCase(comment);
        List<TourLogDTO> matchingTourLogDTOs = new ArrayList<>();

        for (TourLogEntity tourLogEntity : matchingTourLogEntities) {
            TourLogDTO tourLogDTO = new TourLogDTO(tourLogEntity);
            System.out.println("match " + tourLogDTO.getComment());
            matchingTourLogDTOs.add(tourLogDTO);
        }
        return matchingTourLogDTOs;
    }
}
