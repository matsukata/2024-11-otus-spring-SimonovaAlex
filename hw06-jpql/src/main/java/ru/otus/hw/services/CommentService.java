package ru.otus.hw.services;

import ru.otus.hw.dto.CommentEntityDto;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<CommentEntityDto> findByBookId(long id);
}
