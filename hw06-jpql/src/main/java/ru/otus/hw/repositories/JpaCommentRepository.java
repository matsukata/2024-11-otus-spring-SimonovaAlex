package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaCommentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book_id_graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        return Optional.ofNullable(em.find(Comment.class, id, properties));

    }

    @Override
    public List<Comment> findByBookId(Long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book_id_graph");

        TypedQuery<Comment> query = em.createQuery("select distinct comment from Comment comment " +
                "join fetch comment.book book " +
                "join fetch book.genres " +
                "join fetch book.author where book.id = :id", Comment.class);
        return query
                .setParameter("id", id)
                .setHint(FETCH.getKey(), entityGraph)
                .getResultList();
    }
}
