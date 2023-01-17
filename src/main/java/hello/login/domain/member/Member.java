package hello.login.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @Setter
//@Data
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;


    //@NotEmpty
    private String loginId; //로그인 ID

    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String password;
    @NotEmpty
    private String shop; //매장 이름
}
