package introduce.domain.member;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @After
    public void cleanup() {
        memberRepository.deleteAll();;
    }

    @Test
    public void member_domain_test() {
        LocalDateTime now = LocalDateTime.of(2020,12,5,0,0,0);
        String comment = "페이지 탑 영역 내용 부분입니다.";
        String headerImagePath = "헤더 이미지 경로";
        String imageOriginName ="헤더 이미지 원본 이름";
        String introduction = "자기소개 내용 부분입니다.";
        String phoneNumber = "010-1111-1111";
        String email = "uok0201@gmail.com";

        memberRepository.save(Member.builder()
                .comment(comment)
                .filePath(headerImagePath)
                .fileOriginName(imageOriginName)
                .introduction(introduction)
                .phoneNumber(phoneNumber)
                .email(email)
                .build());

        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);

        System.out.println(">>>>>>>>>> rgDate:" + member.getRgDate());
        System.out.println(">>>>>>>>>> mdDate:" + member.getMdDate());

        assertThat(member.getComment()).isEqualTo(comment);
        assertThat(member.getFilePath()).isEqualTo(headerImagePath);
        assertThat(member.getFileOriginName()).isEqualTo(imageOriginName);
        assertThat(member.getIntroduction()).isEqualTo(introduction);
        assertThat(member.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getRgDate()).isAfter(now);
        assertThat(member.getMdDate()).isAfter(now);
    }
}
