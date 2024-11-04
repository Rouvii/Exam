package dat.dao;

import dat.dto.GuideDto;
import dat.dto.TripDto;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.entities.enums.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TripDAO implements IDao<TripDto>,ITripGuideDAO {
    private static EntityManagerFactory emf;
    private static TripDAO instance;
    public TripDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO(emf);
        }
        return instance;
    }
    @Override
    public List<TripDto> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<TripDto> query = em.createQuery("SELECT new dat.dto.TripDto(t) FROM Trip t", TripDto.class);
            return query.getResultList();
        }
    }

    @Override
    public TripDto getById(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                return null;
            }
            return new TripDto(trip);
        }
    }

    @Override
    public TripDto create(TripDto tripDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDto);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDto(trip);
        }
    }

    @Override
    public TripDto update(int id, TripDto update) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            trip.setName(update.getName());
            trip.setPrice(update.getPrice());
            trip.setCategory(update.getCategory());
            trip.setStartTime(update.getStartTime());
            trip.setEndTime(update.getEndTime());
            trip.setStartPosition(update.getStartPosition());
            em.getTransaction().commit();
            return new TripDto(trip);
        }
    }

    @Override
    public TripDto delete(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if(trip == null) {
                return null;
            }
            em.remove(trip);
            em.getTransaction().commit();
            return new TripDto(trip);
        }
    }


    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            trip.setGuide(em.find(Trip.class, guideId).getGuide());
            em.getTransaction().commit();
        }
    }

    @Override
    public Set<TripDto> getTripsByGuide(int guideId) {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDto> query = em.createQuery("SELECT new dat.dto.TripDto(t) FROM Trip t WHERE t.guide.id = :id", TripDto.class);
            query.setParameter("id", guideId);
            return Set.copyOf(query.getResultList());
        }
    }


    public List<TripDto> getTripsByCategory(Category category) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Trip> trips = em.createQuery("SELECT t FROM Trip t", Trip.class).getResultList();
            return trips.stream()
                    .filter(trip -> trip.getCategory().equals(category))
                    .map(TripDto::new)
                    .collect(Collectors.toList());
        }
    }

    // WIP //
//
//    public List<GuideDto> getGuidesWithTotalTripPrice() {
//        try (EntityManager em = emf.createEntityManager()) {
//            List<Guide> guides = em.createQuery("SELECT g FROM Guide g JOIN FETCH g.trips", Guide.class).getResultList();
//            return guides.stream()
//                    .map(guide -> {
//                        double totalTripPrice = guide.getTrips().stream().mapToDouble(Trip::getPrice).sum();
//                        return new GuideDto(guide.getId(), totalTripPrice);
//                    })
//                    .collect(Collectors.toList());
//        }
//    }


}
