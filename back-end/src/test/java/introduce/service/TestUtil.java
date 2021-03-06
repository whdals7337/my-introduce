package introduce.service;

import introduce.domain.FileInfo;
import introduce.domain.member.Member;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class TestUtil {

    public static Member mockMember(Long id, String selectYN) {
        return Member.builder()
                .memberId(id)
                .comment("comment")
                .fileInfo(new FileInfo("filePath", "hello.txt", "fileUrl"))
                .subIntroduction("subIntroduction")
                .introduction("introduction")
                .phoneNumber("phoneNumber")
                .email("email")
                .selectYN(selectYN)
                .build();
    }

    public static MockMultipartFile mockFile() {
        return new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }
}
