package at.fhtw.swen2.mapper;

import at.fhtw.swen2.model.TourLogDTO;
import at.fhtw.swen2.persistence.entity.TourLogEntity;
import org.springframework.stereotype.Component;


@Component
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLogDTO> {

    @Override
    public TourLogDTO fromEntity(TourLogEntity entity) {
        return TourLogDTO.builder()
                .id(entity.getId())
                .dateTime(entity.getDateTime())
                .comment(entity.getComment())
                .difficulty(entity.getDifficulty())
                .totalTime(entity.getTotalTime())
                .rating(entity.getRating())

                .build();
    }

    @Override
    public TourLogEntity toEntity(TourLogDTO tourLogDTO) {
        return TourLogEntity.builder()
                .id(tourLogDTO.getId())
                .dateTime(tourLogDTO.getDateTime())
                .comment(tourLogDTO.getComment())
                .difficulty(tourLogDTO.getDifficulty())
                .totalTime(tourLogDTO.getTotalTime())
                .rating(tourLogDTO.getRating())
                .build();
    }

}
