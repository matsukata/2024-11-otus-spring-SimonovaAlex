package ru.otus.hw.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.JpaCommentRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CommentCommandsITTest {

    @Autowired
    CommentCommands commentCommands;

    @Autowired
    private JpaCommentRepository commentRepository;

    @PostConstruct
    public void setProperty() {
        String input = "bbid";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    void findCommentByBookId() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String commentDto = commentCommands.findCommentByBookId(1L);
        setProperty();
        CommentDto commentDto1 = mapper.readValue(commentDto, new TypeReference<>() {
        });

        List<Comment> expected = commentRepository.findByBookId(1L);

        assertThat(commentDto1.getComments())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteCommentById() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        commentRepository.findById(1L);
        String commentDto = commentCommands.findCommentByBookId(1L);
        setProperty();
        CommentDto commentDto1 = mapper.readValue(commentDto, new TypeReference<>() {
        });

        List<Comment> expected = commentRepository.findByBookId(1L);

        assertThat(commentDto1.getComments())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
