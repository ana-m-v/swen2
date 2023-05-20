package at.fhtw.swen2.service;

import at.fhtw.swen2.mapper.TourLogMapper;
import at.fhtw.swen2.model.TourDTO;
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
    @Autowired
    private TourLogMapper tourLogMapper;


    public List<TourLogDTO> getAllTourLogs() {
        List<TourLogEntity> tours = tourLogRepository.findAll();
        return tours.stream().map(TourLogDTO::new).collect(Collectors.toList());
    }

    public TourLogDTO getTourLogById(Long id) {
        Optional<TourLogEntity> tour = tourLogRepository.findById(id);
        return tour.map(TourLogDTO::new).orElse(null);
    }
    // to do? getTourLogByTour
//    public TourLogDTO createTourLog(TourLogDTO tourLogDTO) {
//        System.out.println("in service create tourlog");
//        TourLogEntity tourlog = new TourLogEntity(tourLogDTO);
//        System.out.println("in service tour Log comment " + tourLogDTO.getComment());
//        tourLogRepository.save(tourlog);
//        tourLogDTO.setId(tourlog.getId());
//        return tourLogDTO;
//    }

    public TourLogDTO createTourLog(TourLogDTO tourLogDto, long tourId) {
        TourEntity tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id: " + tourId));

        TourLogEntity tourLogEntity = new TourLogEntity(tourLogDto);
        tourLogEntity.setTour(tour);

        // Set other properties of tourLogEntity from tourLogDto

        TourLogEntity savedTourLogEntity = tourLogRepository.save(tourLogEntity);

        return new TourLogDTO(savedTourLogEntity);
        // Map savedTourLogEntity to TourLogDto and return it
    }
    public void deleteTourLog(Long id) {
        System.out.println("in service delete : " + id);

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
            // Set other properties accordingly

            // Save the updated tour log entity
            tourLog = tourLogRepository.save(tourLog);

            // Set the id of the DTO object
            existingTourLog.setId(tourLog.getId());

            return existingTourLog;
        } else {
            // Handle the case when the tour log does not exist
            // You can throw an exception or return null, depending on your requirement
            return null;
        }

//        System.out.println("in service update tour");
//        TourLogEntity tourLog = new TourLogEntity(existingTourLog);
////        System.out.println("in service tour dto " + tourDTO.getName() + " and tour entity: " + tour.getName());
//        tourLogRepository.save(tourLog);
//        existingTourLog.setId(tourLog.getId());
//        return existingTourLog;

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
