package hello.login.domain.order;

import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.member.CustomerMember;
import hello.login.domain.member.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    /**주문 생성*/
    @Transactional
    public void createOrder(Long itemId, Long memberId, int count) {
        //엔티티 조회
        CustomerMember customer=customerRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery(DeliveryStatus.READY, customer.getPhone());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, count, item.getPrice());

        //주문 생성
        Order order = Order.createOrder(customer, delivery, orderItem);
        //주문 저장
        orderRepository.save(order);

        //return order.getId();
    }

    /** 주문 취소 */
    @Transactional
    public void cancel(Long orderId) {
        Order findOrder = orderRepository.findOne(orderId);
        findOrder.cancel();
    }

    public List<Order> findPersonalOrders(Long memberId) {
        List<Order> orders = orderRepository.findOrderByMemberId(memberId);
        return orders;
    }

    /*public List<Order> findOrders() {
        return orderRepository.findAll();
    }*/

    /** 주문 내역 검색 */
    //OrderSearch라는 검색 조건을 가진 객체로 주문 엔티티를 검색
    public List<Order> findOrders(OrderSearch orderSearch) {

        return orderRepository.findAllByString(orderSearch);
    }
}
