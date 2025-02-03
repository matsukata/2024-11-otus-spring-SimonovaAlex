package ru.otus.hw.repositories;

import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(long id);

    List<Book> findAll();

    Book save(Book book);

    Book update(long id, String title, Author authorId, List<Genre> genres);

    void deleteById(long id);
}
