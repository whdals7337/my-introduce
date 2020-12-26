package introduce.domain.skill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    // 프로젝트 순서 값 범위 조회
    List<Skill> findByLevelBetween(int preLevel, int lastLevel);
}
