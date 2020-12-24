package introduce.domain.skill;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillRepositoryTest {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void memberSetting() throws Exception {
        String comment = "페이지 탑 영역 내용 부분입니다.";
        String headerImagePath = "헤더 이미지 경로";
        String imageOriginName ="헤더 이미지 원본 이름";
        String subIntroduction = "자기소개 서브 내용 부분입니다.";
        String introduction = "자기소개 내용 부분입니다.";
        String phoneNumber = "010-1111-1111";
        String email = "uok0201@gmail.com";

        memberRepository.save(Member.builder()
                .comment(comment)
                .filePath(headerImagePath)
                .fileOriginName(imageOriginName)
                .subIntroduction(subIntroduction)
                .introduction(introduction)
                .phoneNumber(phoneNumber)
                .email(email)
                .build());
    }

    @After
    public void cleanup() {
        skillRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void skill_save_test() {
        LocalDateTime now = LocalDateTime.of(2020,12,5,0,0,0);
        String skillName = "JAVA";
        String skillImagePath = "D:\\my-introduce\\images\\skills\\skillImage01.img";
        String imageOriginName = "java_logo_image";
        int skillLevel = 1;
        int level = 1;

        Long memberId = memberRepository.findAll().get(0).getMemberId();
        Optional<Member> member = memberRepository.findById(memberId);

        Skill skill = Skill.builder()
                .skillName(skillName)
                .filePath(skillImagePath)
                .fileOriginName(imageOriginName)
                .skillLevel(skillLevel)
                .level(level)
                .member(member.get())
                .build();

        skillRepository.save(skill);

        List<Skill> skillList = skillRepository.findAll();

        System.out.println(">>>>>>>>>> rgDate:" + skillList.get(0).getRgDate());
        System.out.println(">>>>>>>>>> mdDate:" + skillList.get(0).getMdDate());

        assertThat(skillList.get(0).getSkillName()).isEqualTo(skillName);
        assertThat(skillList.get(0).getFilePath()).isEqualTo(skillImagePath);
        assertThat(skillList.get(0).getFileOriginName()).isEqualTo(imageOriginName);
        assertThat(skillList.get(0).getSkillLevel()).isEqualTo(skillLevel);
        assertThat(skillList.get(0).getLevel()).isEqualTo(level);
        assertThat(skillList.get(0).getMember().getMemberId()).isEqualTo(memberId);
        assertThat(skillList.get(0).getRgDate()).isAfter(now);
        assertThat(skillList.get(0).getMdDate()).isAfter(now);
    }
}
