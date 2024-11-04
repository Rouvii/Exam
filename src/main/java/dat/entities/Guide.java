package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dat.dto.GuideDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Guide {


    @Id
    @GeneratedValue
    @Column(name = "guide_id", nullable = false, unique = true)
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private int phoneNumber;

    private int yearsOfExperience;
    @OneToMany(mappedBy = "guide", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Trip> trips = new HashSet<>();

    public Guide(GuideDto guideDto) {
        this.id = guideDto.getId();
        this.firstname = guideDto.getFirstname();
        this.lastname = guideDto.getLastname();
        this.email = guideDto.getEmail();
        this.phoneNumber = guideDto.getPhoneNumber();
        this.yearsOfExperience = guideDto.getYearsOfExperience();
    }

    public Guide(String firstname, String lastname, String email, int phoneNumber, int yearsOfExperience, Set<Trip> trips) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.trips = trips;
    }

    public Guide(String firstname, String lastname, String email, int phoneNumber, int yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.yearsOfExperience = yearsOfExperience;
    }


    // Bi-directional relationship for all rooms in a hotel
    public void setTrips(Set<Trip> trips) {
        if(trips != null) {
            this.trips = trips;
            for (Trip trip : trips) {
                trip.setGuide(this);
            }
        }
    }

    // Bi-directional relationship
    public void addTrip(Trip trip) {
        if ( trip != null) {
            this.trips.add(trip);
            trip.setGuide(this);
        }
    }
}
