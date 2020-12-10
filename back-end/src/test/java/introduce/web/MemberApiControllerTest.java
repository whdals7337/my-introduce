package introduce.web;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import org.junit.After;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void member_save_test() throws Exception {
        MockMultipartFile testFile
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        String comment = "코멘트 영역 입니다.";
        String imageOriginName = "hello.txt";
        String introduction = "자기소개 영역입니다.";
        String phoneNumber = "010-1111-1111";
        String email = "uok0201@gmail.com";

        String url = "http://localhost:" + port + "/api/member";

        this.mockMvc.perform(multipart(url)
                .file(testFile)
                .param("comment",comment)
                .param("introduction",introduction)
                .param("phoneNumber",phoneNumber)
                .param("email",email))
                .andExpect(status().isOk());

        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getComment()).isEqualTo(comment);
        assertThat(all.get(0).getFileOriginName()).isEqualTo(imageOriginName);
        assertThat(all.get(0).getIntroduction()).isEqualTo(introduction);
        assertThat(all.get(0).getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(all.get(0).getEmail()).isEqualTo(email);
    }

    @Test
    public void member_update_test() throws Exception {
        Member saveMember = memberRepository.save(Member.builder()
                .comment("코멘트")
                .filePath("헤어 이미지 경로")
                .fileOriginName("헤더 이미지 원본 이름")
                .introduction("자기소개")
                .phoneNumber("연락처")
                .email("이메일")
                .build());

        Long updateId = saveMember.getMemberId();
        String expectedComment = "comment";
        String expectedImageOriginName = "hello.txt";
        String expectedIntroduction = "introduction";
        String expectedPhoneNumber = "phoneNumber";
        String expectedEmail = "email";

        MockMultipartFile testFile
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        String url = "http://localhost:" + port + "/api/member/" + updateId;

        MockMultipartHttpServletRequestBuilder builder =
                multipart(url);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        mockMvc.perform(builder
                .file(testFile)
                .param("comment", expectedComment)
                .param("introduction", expectedIntroduction)
                .param("phoneNumber", expectedPhoneNumber)
                .param("email", expectedEmail))
                .andExpect(status().isOk());

        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getComment()).isEqualTo(expectedComment);
        assertThat(all.get(0).getFileOriginName()).isEqualTo(expectedImageOriginName);
        assertThat(all.get(0).getIntroduction()).isEqualTo(expectedIntroduction);
        assertThat(all.get(0).getPhoneNumber()).isEqualTo(expectedPhoneNumber);
        assertThat(all.get(0).getEmail()).isEqualTo(expectedEmail);
    }
}
