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
public class CustomerMember {

    private Long id;

    //@NotEmpty
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private String loginId; //로그인 ID

    @NotEmpty
    private String name; //사용자 이름
    @NotEmpty
    private String password;
    @NotEmpty
    private String phone; //전화번호

    public void editPhone(String phone) {
        this.phone = phone;
    }

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
