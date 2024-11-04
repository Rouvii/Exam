package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dat.dto.TripDto;
import dat.entities.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Trip {

    @Id
    @GeneratedValue
    @Column(name = "trip_id", nullable = false, unique = true)
    private Integer id;

    private String name;

    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDate startTime;

    private LocalDate endTime;

    private String startPosition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guide_id", nullable = true)
    @JsonBackReference
    private Guide guide;

    public Trip(TripDto tripDto) {
        this.id = tripDto.getId();
        this.name = tripDto.getName();
        this.price = tripDto.getPrice();
        this.category = tripDto.getCategory();
        this.startTime = tripDto.getStartTime();
        this.endTime = tripDto.getEndTime();
        this.startPosition = tripDto.getStartPosition();
        if (tripDto.getGuide() != null) {
            this.guide = new Guide(tripDto.getGuide());
        }
    }

    public Trip(String name, double price, Category category, LocalDate startTime, LocalDate endTime, String startPosition) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
    }
}