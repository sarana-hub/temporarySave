package hello.login.domain.delivery;

import hello.login.domain.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**배송 엔티티*/

@Entity
@Getter
@NoArgsConstructor
//@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private String phone;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]

    public Delivery(DeliveryStatus status, String phone) {
        this.status = status;
        this.phone = phone;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
