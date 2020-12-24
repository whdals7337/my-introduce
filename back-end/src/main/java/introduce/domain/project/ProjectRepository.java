package introduce.domain.project;

import introduce.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 프로젝트 순서 값 범위 조회
    List<Project> findByLevelBetween(int preLevel, int lastLevel);

    // 특정 회원의 프로젝트 조회
    List<Project> findAllByMember(Member member);
}
