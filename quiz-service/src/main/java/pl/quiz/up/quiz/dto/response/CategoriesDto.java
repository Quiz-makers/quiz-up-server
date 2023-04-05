package pl.quiz.up.quiz.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoriesDto {

    private Long categoryId;

    private String category;

    private byte[] thumbnail;

}
