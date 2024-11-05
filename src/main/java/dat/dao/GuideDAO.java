package dat.dao;

import dat.dto.GuideDTO;
import dat.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class GuideDAO implements IDao<GuideDTO> {

    private static EntityManagerFactory emf;
    private static GuideDAO instance;

    public GuideDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public static GuideDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideDAO(emf);
        }
        return instance;
    }

    @Override
    public List<GuideDTO> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<GuideDTO> query = em.createQuery("SELECT new dat.dto.GuideDTO(g) FROM Guide g", GuideDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public GuideDTO getById(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            return new GuideDTO(guide);
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDto) {
        Guide guide = new Guide(guideDto);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide managedGuide = em.merge(guide);
            em.getTransaction().commit();
            return new GuideDTO(managedGuide);
        } catch (Exception e) {

            throw e;
        }
    }


    @Override
    public GuideDTO update(int id, GuideDTO update) {
            try(EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                Guide guide = em.find(Guide.class, id);
                guide.setFirstname(update.getFirstname());
                guide.setLastname(update.getLastname());
                guide.setEmail(update.getEmail());
                guide.setPhoneNumber(update.getPhoneNumber());
                guide.setYearsOfExperience(update.getYearsOfExperience());
                em.getTransaction().commit();
                return new GuideDTO(guide);
            }
    }

    @Override
    public GuideDTO delete(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            em.remove(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);
        }
    }


}
