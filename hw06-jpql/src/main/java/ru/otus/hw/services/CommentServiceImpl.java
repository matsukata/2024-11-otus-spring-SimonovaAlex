package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public Optional<Comment> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Comment> findByBookId(long id) {
        return repository.findByBookId(id);
    }
}
