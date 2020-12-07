package introduce.domain.member;

import introduce.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50, nullable = false)
    private String comment;

    @Column(length = 500, nullable = false)
    private String headerImagePath;

    @Column(length = 100, nullable = false)
    private String imageOriginName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String introduction;

    @Column(length = 14)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String email;

    @Builder
    public Member(String comment, String headerImagePath, String imageOriginName, String introduction, String phoneNumber, String email) {
        this.comment = comment;
        this.headerImagePath = headerImagePath;
        this.imageOriginName = imageOriginName;
        this.introduction =introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void update(String comment, String headerImagePath, String imageOriginName, String introduction, String phoneNumber, String email){
        this.comment = comment;
        this.headerImagePath = headerImagePath;
        this.imageOriginName = imageOriginName;
        this.introduction =introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
