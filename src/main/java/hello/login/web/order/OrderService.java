package hello.login.web.order;

import hello.login.domain.item.Item;
import hello.login.domain.member.Member;
import hello.login.domain.order.Delivery;
import hello.login.domain.order.Order;
import hello.login.domain.order.OrderItem;
import hello.login.domain.order.OrderSearch;
import hello.login.repository.ItemRepository;
import hello.login.repository.MemberRepository;
import hello.login.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**주문 서비스*/

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        //delivery.setStatus(DeliveryStatus.READY);
        
        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order=Order.createOrder(member, delivery, orderItem);
        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order=orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    /** 주문 내역 검색 */
    //OrderSearch라는 검색 조건을 가진 객체로 주문 엔티티를 검색
    public List<Order> findOrders(OrderSearch orderSearch) {

        return orderRepository.findAllByString(orderSearch);
    }

}
