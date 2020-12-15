package introduce.web.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequestDto {
    private String comment;
    private String filePath;
    private String fileOriginName;
    private String subIntroduction;
    private String introduction;
    private String phoneNumber;
    private String email;

    @Builder
    public MemberUpdateRequestDto(String comment, String filePath, String fileOriginName, String subIntroduction, String introduction, String phoneNumber, String email) {
        this.comment = comment;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.subIntroduction = subIntroduction;
        this.introduction =introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    // file 정보 세팅 메서드
    public void updateFileInfoSetting(String filePath, String fileOriginName) {
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
    }
}
