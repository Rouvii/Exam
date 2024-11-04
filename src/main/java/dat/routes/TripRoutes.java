package dat.routes;

import dat.config.HibernateConfig;
import dat.controller.TripController;
import dat.dao.TripDAO;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final TripDAO tripDAO = new TripDAO(emf);

    private final TripController tripController = new TripController(tripDAO);


    public EndpointGroup getTripRoutes() {
        return () -> {
            get("/", tripController::getAll, Role.USER);
            get("/{id}", tripController::getById,Role.USER);
            post("/", tripController::create, Role.ADMIN);
            put("/{id}", tripController::update, Role.ADMIN);
            delete("/{id}", tripController::delete, Role.ADMIN);
            put("trips/{id}/guides/{guideId}", tripController::addGuideToTrip, Role.ADMIN);
            get("/guides/{guideId}", tripController::getTripsByGuide, Role.USER);
            get("/category/{category}", tripController::getTripsByCategory, Role.USER);
           // get("/guides/totalprice", tripController::getGuidesWithTotalTripPrice, Role.USER);

        };
    }




}
