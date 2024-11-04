package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Popluate;
import dat.dao.GuideDAO;
import dat.dao.TripDAO;
import dat.dto.GuideDto;
import dat.dto.TripDto;
import dat.entities.enums.Category;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoutesTest {
    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final static SecurityController securityController = SecurityController.getInstance();
    private final SecurityDAO securityDAO = new SecurityDAO(emf);
    private static TripDAO tripDAO;
    private static GuideDAO guideDAO;

    private static String userToken, adminToken;
    private static final String BASE_URL = "http://localhost:7070/api";

    private static TripDto t1, t2, t3;

    private static GuideDto g1, g2, g3;
    private static UserDTO userDTO, adminDTO;

    @BeforeAll
    void setUpAll() {
        HibernateConfig.setTest(true);
        app = ApplicationConfig.startServer(7070);
        guideDAO = GuideDAO.getInstance(emf);
        tripDAO = TripDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {

        t1 = new TripDto("Aalborg city tour", 3500, Category.CITY, java.time.LocalDate.of(2025, 6, 1), java.time.LocalDate.of(2025, 6, 10), "Aalborg");
        t2 = new TripDto("Copenhagen city tour", 6700, Category.CITY, java.time.LocalDate.of(2024, 12, 4), java.time.LocalDate.of(2024, 12, 11), "Tivoli");
        t3 = new TripDto("France forest hunt", 6700, Category.FOREST, java.time.LocalDate.of(2025, 1, 13), java.time.LocalDate.of(2025, 1, 15), "Alsace");

        g1 = new GuideDto("Kristian", "Jensen", "KristianJensen@tour.dk", 12345678, 5);
        g2 = new GuideDto("Frederik", "Hansen", "Hansen@Mikkel.dk", 87654321, 3);
        g3 = new GuideDto("Torben", "Turbo", "Turbo@torben.fr", 12345678, 10);

        g1 = guideDAO.create(g1);
        g2 = guideDAO.create(g2);
        g3 = guideDAO.create(g3);

        t1 = tripDAO.create(t1);
        t2 = tripDAO.create(t2);
        t3 = tripDAO.create(t3);

        // User setup
        UserDTO[] userDTOs = Popluate.populateUsers(emf);
        userDTO = userDTOs[0];  // Assuming userDTOs has at least two users
        adminDTO = userDTOs[1];

        try {
            UserDTO verifiedUser = securityDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser(adminDTO.getUsername(), adminDTO.getPassword());
            userToken = "Bearer " + securityController.createToken(verifiedUser);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Trip").executeUpdate();
            em.createQuery("DELETE FROM Guide").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterAll
    void tearDownAll() {
        app.stop();
    }

    @Test
    void getTrips() {
        given()
                .header("Authorization", userToken)
                .when()
                .get(BASE_URL + "/trip")
                .then()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    void getTripById() {
        given()
                .header("Authorization", userToken)
                .when()
                .get(BASE_URL + "/trip/" + t1.getId())
                .then()
                .statusCode(200)
                .body("name", is(t1.getName()));
    }

    @Test
    void createTrip() {
        TripDto trip = new TripDto("Spain ", 5200, Category.BEACH, java.time.LocalDate.of(2025, 2, 14), java.time.LocalDate.of(2025, 2, 20), "Tabernas Desert");

        given()
                .header("Authorization", adminToken)
                .contentType("application/json")
                .body(trip)
                .when()
                .post(BASE_URL + "/trip")
                .then()
                .statusCode(201);
        given()
                .when()
                .get(BASE_URL + "/trip")
                .then()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    void updateTrip() {

        TripDto trip = new TripDto("Updated Trip", 6000, Category.LAKE, java.time.LocalDate.of(2025, 9, 10), java.time.LocalDate.of(2025, 9, 15), "Tuscany");

        given()
                .header("Authorization", adminToken)
                .contentType("application/json")
                .body(trip)
                .when()
                .put(BASE_URL + "/trip/" + t1.getId())
                .then()
                .statusCode(200);
        given()
                .header("Authorization", adminToken)
                .when()
                .get(BASE_URL + "/trip/" + t1.getId())
                .then()
                .statusCode(200)
                .body("name", is("Updated Trip"));
    }

    @Test
    void deleteTrip() {
        given()
                .header("Authorization", adminToken)
                .when()
                .delete(BASE_URL + "/trip/" + t1.getId())
                .then()
                .statusCode(200);
        given()
                .header("Authorization", adminToken)
                .when()
                .get(BASE_URL + "/trip")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void addGuideToTrip() {

        GuideDto guide = new GuideDto("John", "Doe", "john.doe@example.com", 1234567890, 5);
        guide = guideDAO.create(guide);

        given()
                .header("Authorization", adminToken)
                .when()
                .put(BASE_URL + "/trip/trips/" + t1.getId() + "/guides/" + guide.getId())
                .then()
                .statusCode(200);
    }

    @Test
    void getTripsByGuide() {
        // Assuming you have a GuideDto class and a guideDAO to create a guide
        GuideDto guide = new GuideDto("John", "Doe", "john.doe@example.com", 1234567890, 5);
        guide = guideDAO.create(guide);

        // Add the guide to a trip
        given()
                .header("Authorization", adminToken)
                .when()
                .put(BASE_URL + "/trip/trips/" + t1.getId() + "/guides/" + guide.getId())
                .then()
                .statusCode(200);

        // Retrieve trips by guide
        given()
                .header("Authorization", adminToken)
                .when()
                .get(BASE_URL + "/trip/guides/" + guide.getId())
                .then()
                .statusCode(200)
                .body("size()", is(1));

    }

}