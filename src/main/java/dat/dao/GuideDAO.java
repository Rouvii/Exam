package dat.dao;

import dat.dto.GuideDto;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;


public class GuideDAO implements IDao<GuideDto> {

    private static EntityManagerFactory emf;

    public GuideDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<GuideDto> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<GuideDto> query = em.createQuery("SELECT new dat.dto.GuideDto(g) FROM Guide g", GuideDto.class);
            return query.getResultList();
        }
    }

    @Override
    public GuideDto getById(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            return new GuideDto(guide);
        }
    }

    @Override
    public GuideDto create(GuideDto guideDto) {
        Guide guide = new Guide(guideDto);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide managedGuide = em.merge(guide);
            em.getTransaction().commit();
            return new GuideDto(managedGuide);
        } catch (Exception e) {

            throw e;
        }
    }


    @Override
    public GuideDto update(int id, GuideDto update) {
            try(EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();
                Guide guide = em.find(Guide.class, id);
                guide.setFirstname(update.getFirstname());
                guide.setLastname(update.getLastname());
                guide.setEmail(update.getEmail());
                guide.setPhoneNumber(update.getPhoneNumber());
                guide.setYearsOfExperience(update.getYearsOfExperience());
                em.getTransaction().commit();
                return new GuideDto(guide);
            }
    }

    @Override
    public GuideDto delete(long id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            em.remove(guide);
            em.getTransaction().commit();
            return new GuideDto(guide);
        }
    }


}
