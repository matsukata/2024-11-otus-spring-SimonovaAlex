package ru.otus.hw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw.dto.CommentEntityDto;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommentDto implements Serializable {
    private String id = String.valueOf(1);

    private List<CommentEntityDto> comments;
}
