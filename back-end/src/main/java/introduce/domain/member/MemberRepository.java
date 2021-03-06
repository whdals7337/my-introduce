package introduce.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 선택된 멤버 조회
    Optional<Member> findBySelectYN(String selectYN);

    @Query("select m from Member m left join fetch m.skillList where m.memberId = :memberId" )
    Optional<Member> findTotalInfo(@Param("memberId") Long memberId);
}
