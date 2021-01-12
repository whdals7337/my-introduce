package introduce.web;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.skill.Skill;
import introduce.domain.skill.SkillRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(roles = "ADMIN")
public class SkillControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private MemberRepository memberRepository;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void projectForm() throws Exception {
        String url = "http://localhost:" + port + "/skill/skillForm";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    public void projectList() throws Exception {
        String url = "http://localhost:" + port + "/skill/skillList";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("skill/skill-list"))
                .andExpect(model().attributeExists("result"));
    }

    @Test
    public void projectDetail() throws Exception {
        Member member = givenMember();
        Skill skill = givenSkill(member);

        String url = "http://localhost:" + port + "/skill/skillDetail?id="+skill.getSkillId();
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("skill/skill-form"))
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

    public Skill givenSkill(Member member) {
        return skillRepository.save(Skill.builder()
                .skillName("스킬 이름0")
                .filePath("스킬 이미지 경로0")
                .fileOriginName("스킬 이미지 이름0")
                .fileUrl(member.getFileUrl())
                .skillLevel(3)
                .level(1)
                .member(member)
                .build());
    }
}
