package introduce.web.dto.member;

import introduce.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private String comment;
    private String headerImagePath;
    private String imageOriginName;
    private String introduction;
    private String phoneNumber;
    private String email;

    @Builder
    public MemberSaveRequestDto(String comment, String headerImagePath, String imageOriginName, String introduction, String phoneNumber, String email) {
        this.comment = comment;
        this.headerImagePath = headerImagePath;
        this.imageOriginName = imageOriginName;
        this.introduction =introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Member toEntity() {
        return  Member.builder()
                .comment(comment)
                .headerImagePath(headerImagePath)
                .imageOriginName(imageOriginName)
                .introduction(introduction)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }
}
