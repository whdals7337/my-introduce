package introduce.domain.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 프로젝트 순서 값 범위 조회
    List<Project> findByLevelBetween(int preLevel, int lastLevel);
}
