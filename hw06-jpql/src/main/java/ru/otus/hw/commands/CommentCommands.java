package ru.otus.hw.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.output.JsonStream;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.CommentDto;
import ru.otus.hw.dto.CommentEntityDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService service;

    @Transactional(readOnly = true)
    @ShellMethod(value = "Find comment by id", key = "ccid")
    public String findCommentById(long id) {
        Comment comment = service.findById(id).get();
        return JsonStream.serialize(comment);
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Find comment by book id", key = "cbid")
    public String findCommentByBookId(long id) {
        List<CommentEntityDto> comments = service.findByBookId(id)
                .stream()
                .toList();
        CommentDto commentDto = CommentDto.builder().comments(comments).build();
//        String serialize = JsonStream.serialize(commentDto);
//        System.out.println("sss " + serialize);

        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String serialize;
        try {
            serialize = objectMapper.writeValueAsString(commentDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return serialize;
    }
}
