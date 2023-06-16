package at.fhtw.swen2.model;

import at.fhtw.swen2.persistence.entity.TourLogEntity;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Builder
@Component
public class TourLogDTO {
    private long id;
    private Timestamp dateTime;
    private String comment;
    private int difficulty;
    private int totalTime;
    private int rating;

    public TourLogDTO(Long id, Timestamp dateTime, String comment, int difficulty, int totalTime, int rating) {
        this.id = id;
        this.dateTime = dateTime;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }

    public TourLogDTO(TourLogEntity tourLogEntity) {
        this.id = tourLogEntity.getId();
        this.dateTime = tourLogEntity.getDateTime();
        this.comment = tourLogEntity.getComment();
        this.difficulty = tourLogEntity.getDifficulty();
        this.totalTime = tourLogEntity.getTotalTime();
        this.rating = tourLogEntity.getRating();
    }

    public TourLogDTO() {};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
