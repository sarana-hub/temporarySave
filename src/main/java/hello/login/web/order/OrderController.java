package hello.login.web.order;


import hello.login.domain.item.Item;
import hello.login.domain.member.CustomerMember;
import hello.login.domain.member.CustomerRepository;
import hello.login.domain.member.Member;
import hello.login.domain.order.Order;
import hello.login.domain.order.OrderSearch;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.member.MemberRepository;
import hello.login.domain.order.OrderService;
import hello.login.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**상품 주문 컨트롤러*/

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    /** customer-상품 목록 */
    @GetMapping("/itemList")
    public String itemList(Model model) {
        //로그인 여부 체크
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "item/itemList";
    }
    /**
     * customer-상품 상세(조회)
     */
    @GetMapping("/itemList/{itemId}")
    public String item2(@PathVariable long itemId, Model model) {
        //로그인 여부 체크
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item/item";
    }


    @PostMapping("/order/add")
    public String add(@RequestParam("itemId") Long itemId, @RequestParam("count") int count, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        orderService.createOrder(itemId, memberId, count);

        return "redirect:/itemList";
    }

    @GetMapping("/order")   //주문 폼 이동
    public String createForm(Model model) {
        List<CustomerMember> customers = customerRepository.findAll();
        List<Item> items = itemRepository.findAll();
        //주문할 고객정보와 상품 정보를 객체(model)에 담아서 뷰에 넘겨줌
        model.addAttribute("customers", customers);
        model.addAttribute("items", items);
        return "order/itemOrder";
    }

    @PostMapping("/order")      //주문 실행
    public String order(@RequestParam("customerId") Long customerId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        //고객 식별자, 주문할 상품 식별자, 수량 정보를 받아서  주문 서비스에 주문을 요청
        orderService.createOrder(customerId, itemId, count);
        return "redirect:/orderList";  //주문이 끝나면 상품주문내역이있는 URL(/orders)로 리다이렉트
    }


    @GetMapping("/orderList")  //주문 목록 검색
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orderList/{orderId}/cancel")    //주문 취소
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancel(orderId);
        return "redirect:/orderList";
    }
}
