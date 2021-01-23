package introduce.domain.project;

import introduce.domain.FileInfo;
import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void cleanup() {
        projectRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void findByLevelBetween() {
        Member member = givenMember();
        for(int i = 1; i < 6; i++) {
            givenProject(member, i);
        }

        List<Project> list = projectRepository.findByLevelBetween(2,5);

        for(Project project : list) {
            assertThat(project.getLevel()).isGreaterThanOrEqualTo(2);
            assertThat(project.getLevel()).isLessThanOrEqualTo(5);
        }
    }

    @Test
    public void findAllByMember() {
        Member member = givenMember();
        for(int i = 1; i < 6; i++) {
            givenProject(member, i);
        }

        Page<Project> list = projectRepository.findAllByMember(member, null);

        for(Project project : list) {
            assertThat(project.getMember()).isEqualTo(member);
        }
    }

    public Member givenMember() {
        return memberRepository.save(Member.builder()
                .comment("페이지 탑 영역 내용 부분입니다.")
                .fileInfo(new FileInfo("헤더 이미지 경로","헤더 이미지 원본 이름","파일 주소"))
                .subIntroduction("자기소개 서브 내용 부분입니다.")
                .introduction("자기소개 내용 부분입니다.")
                .phoneNumber("010-1111-1111")
                .email("uok0201@gmail.com")
                .selectYN("Y")
                .build());
    }

    public Project givenProject(Member member , int level) {
        return  Project.builder()
                .projectTitle("프로젝트 이름")
                .projectContent("프로젝트는 Spring4 + angularJs를 기반으로 개발된 프로젝트입니다.")
                .projectPostScript("#Spring #angularJs #현장실습")
                .fileInfo(new FileInfo("경로","이름","주소"))
                .projectLink("http://aergaerg")
                .level(level)
                .member(member)
                .build();
    }
}