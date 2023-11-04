package pl.quiz.up.quiz.battle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.battle.entity.BattleEntity;

@Repository
public interface BattleRepository extends JpaRepository<BattleEntity, Long> {

}
