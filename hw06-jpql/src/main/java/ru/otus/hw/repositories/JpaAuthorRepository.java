package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaAuthorRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select author from Author author ", Author.class).getResultList();

    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }
}
    

