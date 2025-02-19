package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JpaBookRepository implements BookRepository {

    private static final String ERROR = "Сущность не найдена";

    @PersistenceContext
    private final EntityManager em;

    public JpaBookRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("authors_genres_graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("authors_graph");
        return em.createQuery("select book from Book book", Book.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book update(long id, String title, Author author, List<Genre> genres) {

        Optional<Book> optionalBook = Optional.ofNullable(em.find(Book.class, id));

        if (optionalBook.isEmpty()) {
            throw new EntityNotFoundException("Сущность не найдена");
        }
        Book book = optionalBook.get();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenres(genres);

        return em.merge(book);
    }


    @Override
    public void deleteById(long id) {

        Optional<Book> optionalBook = Optional.ofNullable(em.find(Book.class, id));

        if (optionalBook.isEmpty()) {
            throw new EntityNotFoundException(ERROR);
        }
        em.remove(optionalBook.get());
    }
}