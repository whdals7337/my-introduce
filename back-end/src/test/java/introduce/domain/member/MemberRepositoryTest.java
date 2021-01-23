package introduce.domain.member;

import introduce.domain.FileInfo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @After
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    public void findBySelectYN() {
        Member expect = givenMember();
        Member member = memberRepository.findBySelectYN("Y").get();
        assertThat(member.getComment()).isEqualTo(expect.getComment());
        assertThat(member.getFileInfo().getFilePath()).isEqualTo(expect.getFileInfo().getFilePath());
        assertThat(member.getFileInfo().getFileOriginName()).isEqualTo(expect.getFileInfo().getFileOriginName());
        assertThat(member.getFileInfo().getFileUrl()).isEqualTo(expect.getFileInfo().getFileUrl());
        assertThat(member.getSubIntroduction()).isEqualTo(expect.getSubIntroduction());
        assertThat(member.getIntroduction()).isEqualTo(expect.getIntroduction());
        assertThat(member.getSelectYN()).isEqualTo(expect.getSelectYN());
        assertThat(member.getEmail()).isEqualTo(expect.getEmail());
        assertThat(member.getMemberId()).isEqualTo(expect.getMemberId());
        assertThat(member.getRgDate()).isEqualTo(expect.getRgDate());
        assertThat(member.getMdDate()).isEqualTo(expect.getMdDate());
    }


    public Member givenMember() {
        return memberRepository.save(Member.builder()
                .comment("페이지 탑 영역 내용 부분입니다.")
                .fileInfo(new FileInfo("헤더 이미지 경로","헤더 이미지 원본 이름","파일 주소"))
                .subIntroduction("자기소개 서브 내용 부분입니다.")
                .introduction("자기소개 내용 부분입니다.")
                .phoneNumber("010-1111-1111")
                .email("uok0201@gmail.com")
                .selectYN("Y")
                .build());
    }
}
