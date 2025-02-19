package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentEntityDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public Optional<Comment> findById(long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentEntityDto> findByBookId(long id) {
        List<Comment> comments = repository.findByBookId(id);
        return comments.stream().map(comment ->
                        CommentEntityDto.builder()
                                .id(comment.getId())
                                .comment(comment.getComment())
                                .book(comment.getBook())
                                .build())
                .collect(Collectors.toList());
    }
}
