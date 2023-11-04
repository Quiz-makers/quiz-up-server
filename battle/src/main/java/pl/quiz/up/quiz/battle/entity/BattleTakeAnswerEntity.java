package pl.quiz.up.quiz.battle.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "battle_take_answer", schema = "public", catalog = "quiz_db")
public class BattleTakeAnswerEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "battle_take_answer_id")
    private Long id;

    @Column(name = "battle_id")
    private Long battleId;

    @Column(name = "first_user_take_answer_id")
    private Long firstUserTakeAnswer;

    @Column(name = "second_user_take_answer_id")
    private Long secondUserTakeAnswer;
}
