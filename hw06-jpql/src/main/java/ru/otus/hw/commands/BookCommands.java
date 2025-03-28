package ru.otus.hw.commands;

import com.jsoniter.output.JsonStream;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;

    @Transactional(readOnly = true)
    @ShellMethod(value = "Find all books", key = "ab")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(JsonStream::serialize)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Find book by id", key = "bbid")
    public String findBookById(long id) {
        return bookService.findById(id)
                .map(JsonStream::serialize)
                .orElse("Book with id %d not found".formatted(id));
    }

    // bins newBook 1 1,6
    @Transactional
    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(String title, long authorId, List<Long> genresIds) {
        var savedBook = bookService.insert(title, authorId, genresIds);
        return JsonStream.serialize(savedBook);
    }

    // bupd 4 editedBook 3 2,5
    @Transactional
    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook(long id, String title, long authorId, List<Long> genresIds) {
        var savedBook = bookService.update(id, title, authorId, genresIds);
        return JsonStream.serialize(savedBook);
    }

    // bdel 4
    @Transactional
    @ShellMethod(value = "Delete book by id", key = "bdel")
    public void deleteBook(long id) {
        bookService.deleteById(id);
    }
}

