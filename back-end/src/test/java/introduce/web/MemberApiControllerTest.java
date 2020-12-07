package introduce.web;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.web.dto.member.MemberSaveRequestDto;
import introduce.web.dto.member.MemberUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    public void member_save_test() throws Exception {
        String comment = "코멘트 영역 입니다.";
        String headerImagePath = "헤더 이미지 경로";
        String imageOriginName ="헤더 이미지 원본 이름";
        String introduction = "자기소개 영역 입니다.";
        String phoneNumber = "010-1111-1111";
        String email = "uok0201@gmail.com";

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .comment(comment)
                .headerImagePath(headerImagePath)
                .imageOriginName(imageOriginName)
                .introduction(introduction)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();

        String url = "http://localhost:" + port + "/api/member";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0l);

        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getComment()).isEqualTo(comment);
        assertThat(all.get(0).getHeaderImagePath()).isEqualTo(headerImagePath);
        assertThat(all.get(0).getImageOriginName()).isEqualTo(imageOriginName);
        assertThat(all.get(0).getIntroduction()).isEqualTo(introduction);
        assertThat(all.get(0).getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(all.get(0).getEmail()).isEqualTo(email);
    }

    @Test
    public void member_update_test() throws Exception {
        Member saveMember = memberRepository.save(Member.builder()
                .comment("코멘트")
                .headerImagePath("헤어 이미지 경로")
                .imageOriginName("헤더 이미지 원본 이름")
                .introduction("자기소개")
                .phoneNumber("연락처")
                .email("이메일")
                .build());

        Long updateId = saveMember.getMemberId();
        String expectedComment = "comment";
        String expectedHeaderImagePath = "headerImagePath";
        String expectedImageOriginName = "imageOriginName";
        String expectedIntroduction = "introduction";
        String expectedPhoneNumber = "phoneNumber";
        String expectedEmail = "email";

        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
                .comment(expectedComment)
                .headerImagePath(expectedHeaderImagePath)
                .imageOriginName(expectedImageOriginName)
                .introduction(expectedIntroduction)
                .phoneNumber(expectedPhoneNumber)
                .email(expectedEmail)
                .build();

        String url = "http://localhost:" + port + "/api/member/" + updateId;

        HttpEntity<MemberUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getComment()).isEqualTo(expectedComment);
        assertThat(all.get(0).getHeaderImagePath()).isEqualTo(expectedHeaderImagePath);
        assertThat(all.get(0).getImageOriginName()).isEqualTo(expectedImageOriginName);
        assertThat(all.get(0).getIntroduction()).isEqualTo(expectedIntroduction);
        assertThat(all.get(0).getPhoneNumber()).isEqualTo(expectedPhoneNumber);
        assertThat(all.get(0).getEmail()).isEqualTo(expectedEmail);
    }
}
