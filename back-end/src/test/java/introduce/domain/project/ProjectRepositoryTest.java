package introduce.domain.project;

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
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

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
        projectRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void project_save_test() {
        LocalDateTime now = LocalDateTime.of(2020,12,5,0,0,0);
        String projectTitle = "프로젝트 이름";
        String projectContent = "프로젝트는 Spring4 + angularJs를 기반으로 개발된 프로젝트입니다.";
        String projectPostScript = "#Spring #angularJs #현장실습";
        String projectImagePath = "D:\\my-introduce\\images\\project\\projectImage01.img";
        String imageOriginName = "프로젝트 이미지 이름";
        String projectLink = "http://aergaerg";
        int level = 1;

        Long memberId = memberRepository.findAll().get(0).getMemberId();
        Optional<Member> member = memberRepository.findById(memberId);

        Project project = Project.builder()
                .projectTitle(projectTitle)
                .projectContent(projectContent)
                .projectPostScript(projectPostScript)
                .filePath(projectImagePath)
                .fileOriginName(imageOriginName)
                .projectLink(projectLink)
                .level(level)
                .member(member.get())
                .build();

        projectRepository.save(project);

        List<Project> projectList = projectRepository.findAll();

        System.out.println(">>>>>>>>>> rgDate:" + projectList.get(0).getRgDate());
        System.out.println(">>>>>>>>>> mdDate:" + projectList.get(0).getMdDate());

        assertThat(projectList.get(0).getProjectTitle()).isEqualTo(projectTitle);
        assertThat(projectList.get(0).getProjectContent()).isEqualTo(projectContent);
        assertThat(projectList.get(0).getProjectPostScript()).isEqualTo(projectPostScript);
        assertThat(projectList.get(0).getFilePath()).isEqualTo(projectImagePath);
        assertThat(projectList.get(0).getFileOriginName()).isEqualTo(imageOriginName);
        assertThat(projectList.get(0).getProjectLink()).isEqualTo(projectLink);
        assertThat(projectList.get(0).getMember().getMemberId()).isEqualTo(memberId);
        assertThat(projectList.get(0).getLevel()).isEqualTo(level);
        assertThat(projectList.get(0).getRgDate()).isAfter(now);
        assertThat(projectList.get(0).getMdDate()).isAfter(now);
    }

}