package dat.dto;

import dat.entities.Trip;
import dat.entities.enums.Category;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDto implements Serializable {
    private Integer id;
    private String name;
    private double price;
    private Category category;
    private LocalDate startTime;
    private LocalDate endTime;
    private String startPosition;
    private GuideDto guide;

    public TripDto(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startPosition = trip.getStartPosition();
        if (trip.getGuide() != null) {
            this.guide = new GuideDto(trip.getGuide());
        }
    }

    public TripDto(String name, double price, Category category, LocalDate startTime, LocalDate endTime, String startPosition) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
    }

}