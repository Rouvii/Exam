package dat.routes;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.entities.enums.Category;
import dat.security.entities.User;
import dat.security.entities.Role;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Populator {

    public static UserDTO[] populateUsers(EntityManagerFactory emf) {
        User user = new User("usertest", "user123");
        User admin = new User("admintest", "admin123");
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        // Assign roles to users
        user.addRole(userRole);
        admin.addRole(adminRole);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.getTransaction().commit();
        }

        UserDTO userDTO = new UserDTO(user.getUsername(), "user123");
        UserDTO adminDTO = new UserDTO(admin.getUsername(), "admin123");
        return new UserDTO[]{userDTO, adminDTO};
    }

    public static Guide[] populateGuides(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide g1 = new Guide("Kristian", "Jensen", "KristianJensen@tour.dk", 12345678, 5);
            Guide g2 = new Guide("Frederik", "Hansen", "Hansen@Mikkel.dk", 87654321, 3);
            em.persist(g1);
            em.persist(g2);
            em.getTransaction().commit();

            return new Guide[]{g1, g2};
        }
    }

    public static Trip[] populateTrips(EntityManagerFactory emf, Guide[] guides) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Using the correct constructor parameters
            Trip trip1 = new Trip("Aalborg city tour", 3500, Category.CITY, java.time.LocalDate.of(2025, 6, 1), java.time.LocalDate.of(2025, 6, 10), "Aalborg");
            trip1.setGuide(guides[0]); // Associate with John Doe
            em.persist(trip1);
            Trip trip2 = new Trip("Copenhagen city tour", 6700, Category.CITY, java.time.LocalDate.of(2024, 12, 4), java.time.LocalDate.of(2024, 12, 11), "Tivoli");
            trip2.setGuide(guides[1]); // Associate with Jane Smith
            em.persist(trip2);

            em.getTransaction().commit();
            return new Trip[]{trip1, trip2};
        }
    }

    public static void populateGuidesAndTrips(EntityManagerFactory emf) {
        // Populate guides first
        Guide[] guides = populateGuides(emf);

        // Now populate trips using the guides created above
        populateTrips(emf, guides);
    }
}