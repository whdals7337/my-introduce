package introduce.web.dto.member;

import introduce.domain.member.Member;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {
    private String comment;
    private String filePath;
    private String fileOriginName;
    private String subIntroduction;
    private String introduction;
    private String phoneNumber;
    private String email;
    private char selectYN;

    public Member toEntity() {
        return  Member.builder()
                .comment(comment)
                .filePath(filePath)
                .fileOriginName(fileOriginName)
                .subIntroduction(subIntroduction)
                .introduction(introduction)
                .phoneNumber(phoneNumber)
                .email(email)
                .selectYN(selectYN)
                .build();
    }
    
    // file 정보 셋팅 메서드
    public void settingFileInfo(String filePath, String fileOriginName) {
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
    }

    public void settingSelectYN( char selectYN) {
        this.selectYN = selectYN;
    }
}
