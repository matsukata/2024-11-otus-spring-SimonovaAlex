package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentEntityDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final BookRepository bookRepository;

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

    private Comment save(String text, long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        Comment comment = Comment.builder()
                .comment(text)
                .book(book).
                build();
        return repository.save(comment);
    }

    @Override
    public Comment update(long id, String text, long bookId) {
        Comment foundComment = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        Book foundBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(bookId)));
        foundComment.setComment(text);
        foundComment.setBook(foundBook);
        return repository.save(foundComment);
    }

    @Override
    public Comment insert(String text, long bookId) {
        return save(text, bookId);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
