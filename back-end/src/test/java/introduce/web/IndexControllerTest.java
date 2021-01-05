package introduce.web;

import introduce.domain.SessionDto;
import introduce.domain.member.MemberRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;
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
        memberRepository.deleteAll();
        session.clearAttributes();
    }

    @Test
    public void index_test() throws Exception {
        String url = "http://localhost:" + port + "/";
        mockMvc.perform(get(url)
                .session(session))
                .andExpect(status().isOk());
    }
}
