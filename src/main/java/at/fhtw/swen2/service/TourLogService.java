package at.fhtw.swen2.service;

import at.fhtw.swen2.mapper.TourLogMapper;
import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.persistence.entity.TourLogEntity;
import at.fhtw.swen2.persistence.repository.TourLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourLogService {

    @Autowired
    private TourLogRepository tourLogRepository;
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
    public TourLogDTO createTourLog(TourLogDTO tourLogDTO) {
        System.out.println("in service create tourlog");
        TourLogEntity tourlog = new TourLogEntity(tourLogDTO);
        System.out.println("in service tour Log comment " + tourLogDTO.getComment());
        tourLogRepository.save(tourlog);
        tourLogDTO.setId(tourlog.getId());
        return tourLogDTO;
    }

    public void deleteTourLog(Long id) {
        System.out.println("in service delete : " + id);

        tourLogRepository.deleteById(id);
    }
}
