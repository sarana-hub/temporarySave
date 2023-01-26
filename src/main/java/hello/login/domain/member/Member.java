package hello.login.domain.member;

import hello.login.domain.item.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Setter
//@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Member(String loginId, String name, String password, String shop) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.shop = shop;
    }

    //private List<Shop> shops = new ArrayList<Shop>();
}
