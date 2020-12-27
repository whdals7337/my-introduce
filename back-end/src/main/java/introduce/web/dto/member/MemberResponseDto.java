package introduce.web.dto.member;

import introduce.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private Long memberId;
    private String comment;
    private String filePath;
    private String fileOriginName;
    private String subIntroduction;
    private String introduction;
    private String phoneNumber;
    private String email;

    public MemberResponseDto(Member entity) {
        this.memberId = entity.getMemberId();
        this.comment = entity.getComment();
        this.filePath = entity.getFilePath();
        this.fileOriginName = entity.getFileOriginName();
        this.subIntroduction = entity.getSubIntroduction();
        this.introduction =entity.getIntroduction();
        this.phoneNumber = entity.getPhoneNumber();
        this.email = entity.getEmail();
    }
}
