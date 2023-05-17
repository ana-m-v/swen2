package at.fhtw.swen2.service;

import at.fhtw.swen2.mapper.TourMapper;
import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.persistence.entity.TourEntity;
import at.fhtw.swen2.persistence.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourMapper tourMapper;


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
        tourRepository.save(tour);
        tourDTO.setId(tour.getId());
        return tourDTO;
    }
    public TourDTO updateTour(TourDTO tourDTO) {
        System.out.println("in service update tour");
        TourEntity tour = new TourEntity(tourDTO);
        System.out.println("in service tour dto " + tourDTO.getName() + " and tour entity: " + tour.getName());
        tourRepository.save(tour);
        tourDTO.setId(tour.getId());
        return tourDTO;
    }
    public void saveTour(TourDTO savedTour) {
        TourEntity tour = new TourEntity(savedTour);
     //   TourDTO tourDTO = tourRepository.findById(tour.getId());

        if(tour != null) {
            tour.setId(savedTour.getId());
            tour.setName(savedTour.getName());
            tour.setDescription(savedTour.getDescription());
            tour.setFrom(savedTour.getFrom());
            tour.setTo(savedTour.getTo());
            tour.setDistance(savedTour.getDistance());
            tour.setTime(savedTour.getTime());
        }
        tourRepository.save(tour);
    }
    public void deleteTour(Long id) {
        System.out.println("in service delete : " + id);

        tourRepository.deleteById(id);
    }
}
