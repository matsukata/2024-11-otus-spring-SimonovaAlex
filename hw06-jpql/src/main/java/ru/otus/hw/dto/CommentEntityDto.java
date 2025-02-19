package ru.otus.hw.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.otus.hw.models.Book;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CommentEntityDto implements Serializable {
    private Long id;
    private String comment;
    private Book book;
}
