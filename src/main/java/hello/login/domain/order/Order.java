package hello.login.domain.order;

import hello.login.domain.delivery.Delivery;
import hello.login.domain.delivery.DeliveryStatus;
import hello.login.domain.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**주문 엔티티*/

@Entity
@Table(name = "orders")
@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;  //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();

    /**모든 연관관계는 지연로딩( LAZY )으로 설정해야함!*/
    //즉시로딩( EAGER )은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;  //배송정보

    private LocalDateTime orderDate;    //주문시간

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;     //주문상태 [ORDER, CANCEL]


    //==연관관계 메서드==//
    public void setMember(Customer customer) {
        this.customer = customer;
        //customer.getOrders().add(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    protected void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    // ==생성 메서드==//
     // 주문 엔티티를 생성할 때 사용
    public static Order createOrder(Customer customer, Delivery delivery, OrderItem orderItem) {
        //OrderItem을 리스트로(여러개) 넘김
        Order order = new Order();
        order.setMember(customer);
        order.setDelivery(delivery);
        order.addOrderItem(orderItem);
        order.setOrderDate(LocalDateTime.now());
        /*for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }*/
        //order.setStatus(OrderStatus.ORDER);
        order.status = OrderStatus.ORDER;

        return order;
    }

    //==비즈니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {  //이미 배송을 완료한 상품이면
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
            //(주문을 취소하지 못하도록) 예외를 발생시킨다
        }
        //this.setStatus(OrderStatus.CANCEL);     //주문 상태를 취소로 변경하고
        status = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {     //주문상품에 주문 취소를 알림
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /** 전체 주문 가격 조회 */
    /*public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        //주문상품들의 가격을 조회해서 더한 값을 반환
        return totalPrice;
    }*/

}
