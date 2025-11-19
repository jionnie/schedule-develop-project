package hello.scheduledevelop.domain.member.entity;

import hello.scheduledevelop.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 정보를 저장하는 엔티티
 * 고유 id, 유저 이름, 비밀번호, 이메일 등의 필드를 포함한다.
 * 
 * @author jiwon jung
 */
@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 80)
    private String email;

    @Column(nullable = false)
    private String password;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
