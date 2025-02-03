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
import ru.otus.hw.models.Comment;

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

    @BeforeEach
    void setUp() {
        dbBooksIds = getDbBooks();
        dbComments = getDbComments(dbBooksIds);
    }

    @DisplayName("должен загружать коммент по id")
    @ParameterizedTest
    @MethodSource("getDbComments")
    void shouldReturnCorrectCommentById(Comment expected) {
        System.out.println("expected.toString()" + expected.toString());
        var actual = repositoryJpa.findById(expected.getId());

        assertThat(actual).get().isEqualTo(expected);
    }


    @DisplayName("должен загружать список всех комментов по айди книги")
    @Test
    void shouldReturnCorrectCommentList() {
        var actualComments = repositoryJpa.findByBookId(1L);
        var expected = dbComments;
        assertThat(actualComments.get(0)).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expected.get(0));
        actualComments.forEach(System.out::println);
    }


    private static List<Comment> getDbComments(List<Long> dbBooksIds) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Comment(id.longValue(), "Comment_" + id, dbBooksIds.get(id - 1)))
                .toList();
    }

    private static List<Long> getDbBooks() {
        return List.of(1L, 2L, 3L);
    }


    private static List<Comment> getDbComments() {
        var dbBooksIds = getDbBooks();
        return getDbComments(dbBooksIds);
    }

}
