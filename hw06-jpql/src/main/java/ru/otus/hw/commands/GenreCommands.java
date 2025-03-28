package ru.otus.hw.commands;

import com.jsoniter.output.JsonStream;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.services.GenreService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    @ShellMethod(value = "Find all genres", key = "ag")
    public String findAllGenres() {
        return genreService.findAll().stream()
                .map(JsonStream::serialize)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}

