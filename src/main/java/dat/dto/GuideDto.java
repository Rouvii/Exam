package dat.dto;

import dat.entities.Guide;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link dat.entities.Guide}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuideDto implements Serializable {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int phoneNumber;
    private int yearsOfExperience;
    private Set<TripDto> trips = new HashSet<>();
   // private double totalPrice;
    //private int guideId;

    public GuideDto(Guide guide) {
        this.id = guide.getId();
        this.firstname = guide.getFirstname();
        this.lastname = guide.getLastname();
        this.email = guide.getEmail();
        this.phoneNumber = guide.getPhoneNumber();
        this.yearsOfExperience = guide.getYearsOfExperience();
    }

   // WIP//
//    public GuideDto(int guideId, double totalTripsPrice) {
//        this.id = guideId;
//        this.totalPrice = totalTripsPrice;
//    }

    public GuideDto(String firstname, String lastname, String email, int phoneNumber, int yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.trips = trips;
    }



}