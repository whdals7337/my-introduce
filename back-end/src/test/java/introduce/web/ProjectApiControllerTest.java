package introduce.web;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.project.Project;
import introduce.domain.project.ProjectRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void memberSetting() {
        String comment = "페이지 탑 영역 내용 부분입니다.";
        String headerImagePath = "헤더 이미지 경로";
        String memimageOriginName ="헤더 이미지 원본 이름";
        String subIntroduction = "자기소개 서브 내용 부분입니다.";
        String introduction = "자기소개 내용 부분입니다.";
        String phoneNumber = "010-1111-1111";
        String email = "uok0201@gmail.com";

        memberRepository.save(Member.builder()
                .comment(comment)
                .filePath(headerImagePath)
                .fileOriginName(memimageOriginName)
                .subIntroduction(subIntroduction)
                .introduction(introduction)
                .phoneNumber(phoneNumber)
                .email(email)
                .build());
    }

    @After
    public void tearDown() throws Exception {
        projectRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void project_save_test() throws Exception {
        MockMultipartFile testFile
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        String projectTitle = "프로젝트 이름";
        String imageOriginName = "hello.txt";
        String projectContent = "프로젝트 내용";
        String projectPostScript = "프로젝트 추신";
        String projectLink = "http://gaergerg";
        Integer level = 1;
        Long memberId = memberRepository.findAll().get(0).getMemberId();

        String url = "http://localhost:" + port + "/api/project";

        this.mockMvc.perform(multipart(url)
                .file(testFile)
                .param("projectTitle", projectTitle)
                .param("projectContent", projectContent)
                .param("projectPostScript", projectPostScript)
                .param("projectLink", projectLink)
                .param("level", String.valueOf(level))
                .param("memberId", String.valueOf(memberId)))
                .andExpect(status().isOk());

        List<Project> all = projectRepository.findAll();
        assertThat(all.get(0).getProjectTitle()).isEqualTo(projectTitle);
        assertThat(all.get(0).getFileOriginName()).isEqualTo(imageOriginName);
        assertThat(all.get(0).getProjectContent()).isEqualTo(projectContent);
        assertThat(all.get(0).getProjectPostScript()).isEqualTo(projectPostScript);
        assertThat(all.get(0).getProjectLink()).isEqualTo(projectLink);
        assertThat(all.get(0).getLevel()).isEqualTo(level);
        assertThat(all.get(0).getMember().getMemberId()).isEqualTo(memberId);
    }

    @Test
    public void project_update_test() throws Exception{
        Long updateId = null;
        // level 의 3과 1을 교환한 순서
        int[] level_list = {2, 3, 1 ,4};
        Long memberId = memberRepository.findAll().get(0).getMemberId();
        Member member = memberRepository.findById(memberId).get();

        // 프로젝트 값 4개 setting
        for(int i = 1; i < 5; i++){
            Project project = projectRepository.save(Project.builder()
                    .projectTitle("프로젝트 이름0" + i)
                    .projectContent("프로젝트 내용0" + i)
                    .projectPostScript("프로젝트 추신0" + i)
                    .filePath("프로젝트 이미지 경로0" + i)
                    .fileOriginName("프로젝트 이미지 원본이름0" + i)
                    .projectLink("http://gergerg" + i)
                    .level(i)
                    .member(member)
                    .build());

            // 프로젝트 level 이 3 인 경우의 id값
            if(i == 3) {
                updateId = project.getProjectId();
            }
        }

        MockMultipartFile testFile
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        String expectedProjectTitle = "projectTitle";
        String expectedProjectContent = "projectContent";
        String expectedProjectPostScript = "projectPostScript";
        String expectedImageOriginName = "hello.txt";
        String expectedProjectLink = "http://gergergerg";
        int expectedLevel = 1;
        Long expectedMemberId = memberId;

        String url = "http://localhost:" + port + "/api/project/" + updateId;

        MockMultipartHttpServletRequestBuilder builder =
                multipart(url);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        this.mockMvc.perform(builder
                .file(testFile)
                .param("projectTitle", expectedProjectTitle)
                .param("projectContent", expectedProjectContent)
                .param("projectPostScript", expectedProjectPostScript)
                .param("projectLink", expectedProjectLink)
                .param("level", String.valueOf(expectedLevel))
                .param("memberId", String.valueOf(expectedMemberId)))
                .andExpect(status().isOk());

        // 수정된 값 검증
        Project target = projectRepository.findById(updateId).get();
        assertThat(target.getProjectTitle()).isEqualTo(expectedProjectTitle);
        assertThat(target.getProjectContent()).isEqualTo(expectedProjectContent);
        assertThat(target.getProjectPostScript()).isEqualTo(expectedProjectPostScript);
        assertThat(target.getFileOriginName()).isEqualTo(expectedImageOriginName);
        assertThat(target.getProjectLink()).isEqualTo(expectedProjectLink);
        assertThat(target.getLevel()).isEqualTo(expectedLevel);
        assertThat(target.getMember().getMemberId()).isEqualTo(expectedMemberId);

        // 테이블의 level 값 전체 검증
        List<Project> all = projectRepository.findAll();
        for(int i = 0; i < 4; i++){
            assertThat(all.get(i).getLevel()).isEqualTo(level_list[i]);
        }
    }
}
