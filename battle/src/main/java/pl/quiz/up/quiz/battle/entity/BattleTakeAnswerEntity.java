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


    @OneToOne
    @MapsId
    @JoinColumn(name = "battle_id")
    private BattleEntity battleEngine;

    @OneToOne
    @MapsId
    @JoinColumn(name = "first_user_take_answer_id")
    private TakeAnswerEntity firstUserTakeAnswer;

    @OneToOne
    @MapsId
    @JoinColumn(name = "second_user_take_answer_id")
    private TakeAnswerEntity secondUserTakeAnswer;
}
