package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService service;

    @Transactional(readOnly = true)
    @ShellMethod(value = "Find comment by id", key = "ccid")
    public String findCommentById(long id) {
        return service.findById(id)
                .map(Comment::toString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Find comment by book id", key = "cbid")
    public String findCommentBybookId(long id) {
        return service.findByBookId(id)
                .stream()
                .map(Comment::toString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
