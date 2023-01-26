package hello.login.domain.order;

import hello.login.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;

/**주문상품 엔티티*/

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "order_item")
//@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;    //주문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;  //주문 상품

    @NumberFormat(pattern = "###,###")
    private int orderPrice; //주문 가격

    private int count; //주문 수량

    public OrderItem(Item item, int count, int orderPrice) {
        this.item = item;
        this.count = count;
        this.orderPrice = orderPrice;
    }


    //==생성 메서드==//
    //주문상품 엔티티를 생성
    public static OrderItem createOrderItem(Item item, int count, int orderPrice) {
        OrderItem orderItem = new OrderItem(item, count, orderPrice);
        /*orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);*/
        item.removeStock(count);    //주문한 수량만큼 상품의 재고를 줄인다
        return orderItem;
    }


    //==비즈니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        getItem().addStock(count);
        //취소한 주문 수량만큼 상품의 재고를 증가시킨다
    }

    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    /*public int getTotalPrice() {
        return getOrderPrice() * getCount();
        //주문 가격에 수량을 곱한 값을 반환
    }*/

    public void setOrder(Order order) {
        this.order = order;
    }
}
