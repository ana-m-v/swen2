package at.fhtw.swen2.mapper;

import at.fhtw.swen2.model.TourDTO;
import at.fhtw.swen2.persistence.entity.TourEntity;
import org.springframework.stereotype.Component;

@Component
public class TourMapper extends AbstractMapper<TourEntity, TourDTO> {

    @Override
    public TourDTO fromEntity(TourEntity entity) {
        return TourDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .distance(entity.getDistance())
                .time(entity.getTime())
                .from(entity.getFrom())
                .to(entity.getTo())
                .transportType(entity.getTransportType())
                .routeImage(entity.getRouteImage())
                .build();
    }

    @Override
    public TourEntity toEntity(TourDTO tourDTO) {
        return TourEntity.builder()
                .id(tourDTO.getId())
                .name(tourDTO.getName())
                .description(tourDTO.getDescription())
                .distance(tourDTO.getDistance())
                .estimatedTime(tourDTO.getTime())
                .fromDestination(tourDTO.getFrom())
                .toDestination(tourDTO.getTo())
                .transportType(tourDTO.getTransportType())
                .routeImage(tourDTO.getRouteImage())
                .build();
    }
}
