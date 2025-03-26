package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({JpaCommentRepository.class})
public class JpaCommentRepositoryTest {
    @Autowired
    private JpaCommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;
    private List<Comment> dbComments;

    private List<Long> dbBooksIds;
    private List<Book> dbBooks;
    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooksIds = getDbBooksIds();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
        dbComments = getDbComments(dbBooks);

    }

    @DisplayName("должен загружать коммент по id")
    @ParameterizedTest
    @MethodSource("getDbComments")
    void shouldReturnCorrectCommentById(Comment expected) {

        var actual = repositoryJpa.findById(expected.getId()).get();
        Comment exp = em.find(Comment.class, expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(exp);
    }


    @DisplayName("должен загружать список всех комментов по айди книги")
    @Test
    void shouldReturnCorrectCommentList() {
        var actualComments = repositoryJpa.findByBookId(1L);
        var expected = dbComments;
        assertThat(actualComments.get(0)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expected.get(0));
        actualComments.forEach(System.out::println);
    }


    private static List<Comment> getDbComments(List<Book> dbBooks) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Comment(id.longValue(), "Comment_" + id, dbBooks.get(id - 1)))
                .toList();
    }

    private static List<Long> getDbBooksIds() {
        return List.of(1L, 2L, 3L);
    }


    private static List<Comment> getDbComments() {
        var dbBooks = getDbBooks();
        return getDbComments(dbBooks);
    }

    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id.longValue(),
                        "BookTitle_" + id,
                        dbAuthors.get(id - 1),
                        dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2)
                ))
                .toList();
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id.longValue(), "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Genre(id.longValue(), "Genre_" + id))
                .toList();
    }

}
