package introduce.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 선택된 멤버 조회
    Optional<Member> findBySelectYN(String selectYN);
}
