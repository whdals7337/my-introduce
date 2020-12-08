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
    private String filePath;

    @Column(length = 100, nullable = false)
    private String fileOriginName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String introduction;

    @Column(length = 14)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String email;

    @Builder
    public Member(String comment, String filePath, String fileOriginName, String introduction, String phoneNumber, String email) {
        this.comment = comment;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.introduction =introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void update(String comment, String filePath, String fileOriginName, String introduction, String phoneNumber, String email){
        this.comment = comment;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.introduction =introduction;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
