package introduce.domain.skill;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillRepositoryTest {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void cleanup() {
        skillRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void findAllByMember() {
        Member member = givenMember();
        for(int i = 1; i < 6; i++) {
            givenSkill(member, i);
        }

        Page<Skill> list = skillRepository.findAllByMember(member, null);

        for(Skill skill : list) {
            assertThat(skill.getMember()).isEqualTo(member);
        }
    }

    public Member givenMember() {
        return memberRepository.save(Member.builder()
                .comment("페이지 탑 영역 내용 부분입니다.")
                .filePath("헤더 이미지 경로")
                .fileOriginName("헤더 이미지 원본 이름")
                .fileUrl("파일 주소")
                .subIntroduction("자기소개 서브 내용 부분입니다.")
                .introduction("자기소개 내용 부분입니다.")
                .phoneNumber("010-1111-1111")
                .email("uok0201@gmail.com")
                .selectYN("Y")
                .build());
    }

    public Skill givenSkill(Member member, int level) {
        return  Skill.builder()
                .skillName("JAVA")
                .filePath("path")
                .fileOriginName("java_logo_image")
                .fileUrl("파일주소")
                .skillLevel(1)
                .level(level)
                .member(member)
                .build();
    }
}
