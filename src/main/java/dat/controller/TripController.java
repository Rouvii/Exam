package dat.controller;

import dat.dao.TripDAO;
import dat.dto.TripDTO;
import dat.entities.enums.Category;
import dat.exception.ApiException;
import dat.exception.Message;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TripController implements IController, ITripController {
    private final TripDAO tripDAO;
    private final Logger log = LoggerFactory.getLogger(TripController.class);



    public TripController(TripDAO tripDAO) {
        this.tripDAO = new TripDAO();
    }

    @Override
    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO trip = tripDAO.getById(id);

            if (trip == null) {
                ctx.status(404);
                ctx.json(new Message(404, "Trip not found"));
                return;
            }

            ctx.status(200);
            ctx.json(trip, TripDTO.class);
        } catch (Exception e) {
            ctx.json(new Message(400, "Invalid request"));
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void create(Context ctx) {
        try {
            TripDTO tripDto = ctx.bodyAsClass(TripDTO.class);
            if (tripDto == null) {
                ctx.status(404);
                ctx.json(new Message(404, "No trip data found"));
                return;
            }

            TripDTO newTrip = tripDAO.create(tripDto);
            ctx.status(201);
            ctx.json(newTrip, TripDTO.class);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void getAll(Context ctx) {
        try {
            List<TripDTO> tripDtos = tripDAO.getAll();

            if (tripDtos.isEmpty()) {
                ctx.status(404);
                ctx.json(new Message(404, "No trips found"));
                return;
            }

            ctx.status(200);
            ctx.json(tripDtos, TripDTO.class);
        } catch (ApiException e) {
            log.error("Error reading all trips: {}", e.getMessage());
            ctx.json(new Message(500, "Error reading all trips"));
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO tripDto = ctx.bodyAsClass(TripDTO.class);
            TripDTO updatedTrip = tripDAO.update(id, tripDto);
            ctx.status(200);
            ctx.json(updatedTrip, TripDTO.class);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
           TripDTO deletedTrip = tripDAO.delete(id);
            if (deletedTrip == null) {
                ctx.status(404);
                ctx.json(new Message(404, "Trip not found"));
                ctx.json(deletedTrip, TripDTO.class);
                log.error("404 Trip not found");
                return;
            }
            ctx.status(200);
            ctx.json(new Message(200, "Trip deleted"));
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void addGuideToTrip(Context ctx) {
        try {
            int tripId = Integer.parseInt(ctx.pathParam("id"));
            int guideId = Integer.parseInt(ctx.pathParam("guideId"));
            tripDAO.addGuideToTrip(tripId, guideId);
            ctx.status(201);
            ctx.json(new Message(201, "Guide added to trip"));

        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void getTripsByGuide(Context ctx) {
        try {
            int guideId = Integer.parseInt(ctx.pathParam("guideId"));
            Set<TripDTO> trips = tripDAO.getTripsByGuide(guideId);
            if (trips.isEmpty()) {
                ctx.status(404);
                ctx.json(new Message(404, "No trips found"));
                return;
            }
            ctx.status(200);
            ctx.json(trips, TripDTO.class);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }


    public void getTripsByCategory(Context ctx) {
        try {
            String category = ctx.pathParam("category");
            List<TripDTO> trips = tripDAO.getTripsByCategory(Category.valueOf(category.toUpperCase()));
            if (trips.isEmpty()) {
                ctx.status(404);
                ctx.json(new Message(404, "No trips found for category: " + category));
                return;
            }
            ctx.status(200);
            ctx.json(trips, TripDTO.class);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void getTotalPriceByGuide(Context ctx) {
        int guideId = Integer.parseInt(ctx.pathParam("id"));
        try {
            double totalPrice = tripDAO.getTotalPriceByGuide(guideId);
            ctx.status(200).json(Map.of("guideId", guideId, "totalPrice", totalPrice));
        } catch (ApiException e) {
            ctx.status(e.getStatusCode()).json(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).json("An error occurred while retrieving total price by guide.");
        }
    }







}