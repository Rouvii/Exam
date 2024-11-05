package dat.dao;

import dat.dto.TripDTO;
import dat.entities.Trip;
import dat.entities.enums.Category;
import dat.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TripDAO implements IDao<TripDTO>,ITripGuideDAO {
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
    public List<TripDTO> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dto.TripDTO(t) FROM Trip t", TripDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public TripDTO getById(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                return null;
            }
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO create(TripDTO tripDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDto);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO update(int id, TripDTO update) {
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
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO delete(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if(trip == null) {
                return null;
            }
            em.remove(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
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
    public Set<TripDTO> getTripsByGuide(int guideId) {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dto.TripDTO(t) FROM Trip t WHERE t.guide.id = :id", TripDTO.class);
            query.setParameter("id", guideId);
            return Set.copyOf(query.getResultList());
        }
    }


    public List<TripDTO> getTripsByCategory(Category category) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Trip> trips = em.createQuery("SELECT t FROM Trip t", Trip.class).getResultList();
            return trips.stream()
                    .filter(trip -> trip.getCategory().equals(category))
                    .map(TripDTO::new)
                    .collect(Collectors.toList());
        }
    }



    public double getTotalPriceByGuide(int guideId) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            Double totalPrice = em.createQuery(
                            "SELECT SUM(t.price) FROM Trip t WHERE t.guide.id = :guideId", Double.class)
                    .setParameter("guideId", guideId)
                    .getSingleResult();

            return totalPrice != null ? totalPrice : 0.0;
        } catch (NoResultException e) {
            return 0.0;
        }
    }



}
