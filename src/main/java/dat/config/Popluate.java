package dat.config;


import dat.entities.Guide;
import dat.entities.Trip;
import dat.entities.enums.Category;
import dat.security.entities.Role;
import dat.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class Popluate {



    public static void populate() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<Trip> trips1 = getTrips1();
        Set<Trip> trips2 = getTrips2();
        Set<Trip> trips3 = getTrips3();


        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide guide1 = new Guide("Kristian", "Jensen", "KristianJensen@tour.dk", 12345678, 5);
            Guide guide2 = new Guide("Frederik", "Hansen", "Hansen@Mikkel.dk", 87654321, 3);
            Guide guide3 = new Guide("Torben", "Turbo", "Turbo@torben.fr", 12345678, 10);


            guide3.setTrips(trips1);
            guide1.setTrips(trips2);
            guide2.setTrips(trips3);

            em.persist(guide1);
            em.persist(guide2);
            em.persist(guide3);

            trips1.forEach(em::persist);
            trips2.forEach(em::persist);
            trips3.forEach(em::persist);
            em.getTransaction().commit();
        }

    }

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




    private static Set<Trip> getTrips1() {
        Trip trip1 = new Trip("Aalborg city tour", 3500, Category.CITY, java.time.LocalDate.of(2025, 6, 1), java.time.LocalDate.of(2025, 6, 10), "Aalborg");
        Trip trip2 = new Trip("Copenhagen city tour", 6700, Category.CITY, java.time.LocalDate.of(2024, 12, 4), java.time.LocalDate.of(2024, 12, 11), "Tivoli");
        Trip trip3 = new Trip("France forest hunt", 6700, Category.FOREST, java.time.LocalDate.of(2025, 1, 13), java.time.LocalDate.of(2025, 1, 15), "Alsace");

        Trip[] trips = {trip1, trip2, trip3};
        return Set.of(trips);
    }
    private static Set<Trip> getTrips2() {
        Trip trip1 = new Trip("Berlin historical walk", 4500, Category.CITY, java.time.LocalDate.of(2025, 4, 20), java.time.LocalDate.of(2025, 4, 25), "Berlin");
        Trip trip2 = new Trip("Norway fjord cruise", 8000, Category.SEA, java.time.LocalDate.of(2025, 7, 5), java.time.LocalDate.of(2025, 7, 12), "Oslo Fjord");
        Trip trip3 = new Trip("Spain ", 5200, Category.BEACH, java.time.LocalDate.of(2025, 2, 14), java.time.LocalDate.of(2025, 2, 20), "Tabernas Desert");

        Trip[] trips = {trip1, trip2, trip3};
        return Set.of(trips);
    }

    private static Set<Trip> getTrips3() {
        Trip trip1 = new Trip("Italy vineyard visit", 6000, Category.LAKE, java.time.LocalDate.of(2025, 9, 10), java.time.LocalDate.of(2025, 9, 15), "Tuscany");

        Trip trip2 = new Trip("Iceland volcano expedition", 9500, Category.FOREST, java.time.LocalDate.of(2025, 3, 1), java.time.LocalDate.of(2025, 3, 7), "Reykjanes Peninsula");
        Trip trip3 = new Trip("Japan cherry blossom tour", 7200, Category.CITY, java.time.LocalDate.of(2025, 3, 25), java.time.LocalDate.of(2025, 4, 5), "Tokyo");

        Trip[] trips = {trip1, trip2, trip3};
        return Set.of(trips);
    }

}
