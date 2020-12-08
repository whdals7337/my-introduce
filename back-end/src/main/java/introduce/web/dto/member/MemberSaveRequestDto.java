package introduce.web.dto.member;

import introduce.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String comment;
    private String filePath;
    private String fileOriginName;
    private String introduction;
    private String phoneNumber;
    private String email;

    @Builder
    public MemberSaveRequestDto(String comment, String filePath, String fileOriginName, String introduction, String phoneNumber, String email) {
        this.comment = comment;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.introduction =introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Member toEntity() {
        return  Member.builder()
                .comment(comment)
                .filePath(filePath)
                .fileOriginName(fileOriginName)
                .introduction(introduction)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }
    
    // file 정보 셋팅 메서드
    public void saveFileInfoSetting(String filePath, String fileOriginName) {
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
    }
}
