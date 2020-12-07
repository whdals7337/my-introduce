package introduce.web.dto.member;

import introduce.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long memberId;
    private String comment;
    private String headerImagePath;
    private String imageOriginName;
    private String introduction;
    private String phoneNumber;
    private String email;

    public MemberResponseDto(Member entity) {
        this.memberId = entity.getMemberId();
        this.comment = entity.getComment();
        this.headerImagePath = entity.getHeaderImagePath();
        this.imageOriginName = entity.getImageOriginName();
        this.introduction =entity.getIntroduction();
        this.phoneNumber = entity.getPhoneNumber();
        this.email = entity.getEmail();
    }
}
