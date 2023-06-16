package at.fhtw.swen2.service;

import at.fhtw.swen2.mapper.TourMapper;
import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.persistence.entity.TourEntity;
import at.fhtw.swen2.persistence.entity.TourLogEntity;
import at.fhtw.swen2.persistence.repository.TourLogRepository;
import at.fhtw.swen2.persistence.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourLogRepository tourLogRepository;
    @Autowired
    private TourMapper tourMapper;

    public TourService() {};

    public List<TourDTO> getAllTours() {
        List<TourEntity> tours = tourRepository.findAll();
        return tours.stream().map(TourDTO::new).collect(Collectors.toList());
    }

    public TourDTO getTourById(Long id) {
        Optional<TourEntity> tour = tourRepository.findById(id);
        return tour.map(TourDTO::new).orElse(null);
    }

    public TourDTO createTour(TourDTO tourDTO) {
        System.out.println("in service create tour");
        TourEntity tour = new TourEntity(tourDTO);
        System.out.println("in service tour dto " + tourDTO.getName() + " and tour entity: " + tour.getName());
        tour = tourRepository.save(tour);
        System.out.println("in service after save " + tourDTO.getName() + " and tour entity id: " + tour.getId());
        tourDTO.setId(tour.getId());
        return tourDTO;
    }
    public TourDTO updateTour(TourDTO tourDTO) {
        System.out.println("in service update tour");
        Optional<TourEntity> optionalTour = tourRepository.findById(tourDTO.getId());
        if (optionalTour.isPresent()) {
            TourEntity tour = optionalTour.get();
            tour.setName(tourDTO.getName());
            tour.setDescription(tourDTO.getDescription());
            tour.setFrom(tourDTO.getFrom());
            tour.setTo(tourDTO.getTo());
            tour.setTransportType(tourDTO.getTransportType());
            tour = tourRepository.save(tour);
            List<TourLogEntity> tourLogs = tour.getTourLogs();
            tourLogRepository.deleteAll(tourLogs);
            tourDTO.setId(tour.getId());
        }
        return tourDTO;
    }

    public void deleteTour(Long id) {
        System.out.println("in service delete : " + id);

        tourRepository.deleteById(id);
    }

    public List<TourDTO> searchQuery(String searchString) {
        List<TourEntity> matchingTourEntities = tourRepository.findByForSearchStringContaining(searchString);
        List<TourDTO> matchingTourDTOs = new ArrayList<>();

        for (TourEntity tourEntity : matchingTourEntities) {
            TourDTO tourDTO = new TourDTO(tourEntity);
            System.out.println("match " + tourDTO.getName());
            matchingTourDTOs.add(tourDTO);
        }
        return matchingTourDTOs;
    }
}
