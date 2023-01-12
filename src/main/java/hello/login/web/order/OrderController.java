package hello.login.web.order;


import hello.login.domain.item.Item;
import hello.login.domain.member.Member;
import hello.login.domain.order.Order;
import hello.login.domain.order.OrderSearch;
import hello.login.repository.ItemRepository;
import hello.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**상품 주문 컨트롤러*/

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @GetMapping("/order")   //주문 폼 이동
    public String createForm(Model model) {
        List<Member> members = memberRepository.findAll();
        List<Item> items = itemRepository.findAll();
        //주문할 고객정보와 상품 정보를 객체(model)에 담아서 뷰에 넘겨줌
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping("/order")      //주문 실행
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        //고객 식별자, 주문할 상품 식별자, 수량 정보를 받아서  주문 서비스에 주문을 요청
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";  //주문이 끝나면 상품주문내역이있는 URL(/orders)로 리다이렉트
    }


    @GetMapping("/orders")  //주문 목록 검색
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")    //주문 취소
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
