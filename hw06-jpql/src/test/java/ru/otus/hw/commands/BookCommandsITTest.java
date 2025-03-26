package ru.otus.hw.commands;

import com.jsoniter.JsonIterator;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookCommandsITTest {

    @Autowired
    BookCommands commands;
    @Autowired
    CommentCommands commentCommands;

    @Autowired
    private JpaBookRepository repository;
    @Autowired
    private JpaCommentRepository commentRepository;

    @PostConstruct
    public void setProperty() {
        String input = "bbid";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    void findBookByIdTest() {

        String book = commands.findBookById(1L);
        setProperty();
        Book actualBook = JsonIterator.deserialize(book, Book.class);
        Optional<Book> expected = repository.findById(1L);
        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expected.get());
    }
}