package hello.login.domain.customer;

import lombok.*;

import javax.persistence.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Setter
//@Data
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    //@NotEmpty
    private String loginId; //로그인 ID

    //@NotEmpty
    private String name; //사용자 이름
    //@NotEmpty
    private String password;
    //@NotEmpty
    private String phone; //전화번호

    public void editPhone(String phone) {
        this.phone = phone;
    }

    public Customer(String loginId, String name, String password, String phone) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    /*@OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();*/
}
