package pl.quiz.up.quiz.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuizTypesDto {

    private Long typeId;

    private String type;

}
