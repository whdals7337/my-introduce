package introduce.web;

import introduce.domain.SessionDto;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberRepository memberRepository;

    protected MockHttpSession session;

    @Before
    public void before() {
        session = new MockHttpSession();
        SessionDto sessionDto = SessionDto.builder().isAccess("access").build();
        session.setAttribute("accessObject", sessionDto);
    }
    @After
    public void tearDown() throws Exception {

        session.clearAttributes();
    }

    @Test
    public void projectForm() throws Exception {
        String url = "http://localhost:" + port + "/project/projectForm";
        mockMvc.perform(get(url)
                .session(session))
                .andExpect(status().isOk());
    }

    @Test
    public void projectList() throws Exception {
        String url = "http://localhost:" + port + "/project/projectList";
        mockMvc.perform(get(url)
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-list"))
                .andExpect(model().attributeExists("result"));
    }

    @Test
    public void projectDetail() throws Exception {
        Member member = givenMember();
        Project project = givenProject(member);

        String url = "http://localhost:" + port + "/project/projectDetail?id="+project.getProjectId();
        mockMvc.perform(get(url)
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-form"))
                .andExpect(model().attributeExists("result"));
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

    public Project givenProject(Member member) {
        return projectRepository.save(Project.builder()
                .projectTitle("프로젝트 이름0")
                .projectContent("프로젝트 내용0")
                .projectPostScript("프로젝트 추신0")
                .filePath("프로젝트 이미지 경로0")
                .fileOriginName("프로젝트 이미지 원본이름0")
                .fileUrl("파일주소")
                .projectLink("http://gergerg")
                .level(1)
                .member(member)
                .build());
    }
}
