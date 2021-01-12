package introduce.web;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import org.junit.After;
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
public class MemberControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MemberRepository memberRepository;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void memberFrom() throws Exception {
        String url = "http://localhost:" + port + "/member/memberForm";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    public void memberList() throws Exception {
        String url = "http://localhost:" + port + "/member/memberList";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("member/member-list"))
                .andExpect(model().attributeExists("result"));
    }

    @Test
    public void memberDetail() throws Exception {
        Member member = givenMember();
        String url = "http://localhost:" + port + "/member/memberDetail?id="+member.getMemberId();
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("member/member-form"))
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
}
