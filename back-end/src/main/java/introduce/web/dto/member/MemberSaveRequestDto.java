package introduce.web.dto.member;

import introduce.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String comment;
    private String filePath;
    private String fileOriginName;
    private String subIntroduction;
    private String introduction;
    private String phoneNumber;
    private String email;

    @Builder
    public MemberSaveRequestDto(String comment, String filePath, String fileOriginName,String subIntroduction, String introduction, String phoneNumber, String email) {
        this.comment = comment;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.subIntroduction = subIntroduction;
        this.introduction =introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Member toEntity() {
        return  Member.builder()
                .comment(comment)
                .filePath(filePath)
                .fileOriginName(fileOriginName)
                .subIntroduction(subIntroduction)
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
