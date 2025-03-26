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

        ObjectMapper objectMapper = new ObjectMapper();
        String serialize;
        try {
            serialize = objectMapper.writeValueAsString(commentDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return serialize;
    }

    // cins newComment 1 1
    @Transactional
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String text, long bookId) {
        var savedBook = service.insert(text, bookId);
        return JsonStream.serialize(savedBook);
    }

    // cupd 1 editedComment 1
    @Transactional
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(long id, String text, long bookId) {
        var savedComment = service.update(id, text, bookId);
        return JsonStream.serialize(savedComment);
    }

    // cdel 1
    @Transactional
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteComment(long id) {
        service.deleteById(id);
    }
}
