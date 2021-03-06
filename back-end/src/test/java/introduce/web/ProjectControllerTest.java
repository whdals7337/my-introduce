package introduce.web;

import introduce.domain.FileInfo;
import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.project.Project;
import introduce.domain.project.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(roles = "ADMIN")
public class ProjectControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberRepository memberRepository;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void projectForm() throws Exception {
        String url = "http://localhost:" + port + "/project/projectForm";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    public void projectList() throws Exception {
        String url = "http://localhost:" + port + "/project/projectList";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-list"))
                .andExpect(model().attributeExists("result"));
    }

    @Test
    public void projectDetail() throws Exception {
        Member member = givenMember();
        Project project = givenProject(member);

        String url = "http://localhost:" + port + "/project/projectDetail?id="+project.getProjectId();
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-form"))
                .andExpect(model().attributeExists("result"));
    }

    public Member givenMember() {
        return memberRepository.save(Member.builder()
                .comment("페이지 탑 영역 내용 부분입니다.")
                .fileInfo(new FileInfo("헤어 이미지 경로","헤더 이미지 원본 이름","파일 주소"))
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
                .fileInfo(new FileInfo("프로젝트 이미지 경로0","프로젝트 이미지 원본이름0","파일주소"))
                .projectLink("http://gergerg")
                .level(1)
                .member(member)
                .build());
    }
}
