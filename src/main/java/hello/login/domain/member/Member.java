package hello.login.domain.member;

import hello.login.domain.order.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
//@Data
public class Member {

    private Long id;

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    //@NotEmpty
    private String loginId; //로그인 ID

    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String password;
    @NotEmpty
    private String store; //매장 이름

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
