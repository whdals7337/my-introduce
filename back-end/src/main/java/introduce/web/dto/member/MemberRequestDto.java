package introduce.web.dto.member;

import introduce.domain.member.Member;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {
    private String comment;
    private String subIntroduction;
    private String introduction;
    private String phoneNumber;
    private String email;

    public Member toEntity(String savePath, String originalName, String fileUrl, String selectYN) {
        return  Member.builder()
                .comment(comment)
                .filePath(savePath)
                .fileOriginName(originalName)
                .fileUrl(fileUrl)
                .subIntroduction(subIntroduction)
                .introduction(introduction)
                .phoneNumber(phoneNumber)
                .email(email)
                .selectYN(selectYN)
                .build();
    }
}
