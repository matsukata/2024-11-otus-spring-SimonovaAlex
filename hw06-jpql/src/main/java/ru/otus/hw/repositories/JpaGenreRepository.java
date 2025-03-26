package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.util.List;

@Repository
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaGenreRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select genre from Genre genre", Genre.class).getResultList();
    }

    @Override
    public List<Genre> findAllByIds(List<Long> ids) {
        TypedQuery<Genre> query = em.createQuery(
                "select genres from Genre genres where id in (: ids)", Genre.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
