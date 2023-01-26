package hello.login.domain.order;


import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.login.SessionConst;
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
    private final ItemRepository itemRepository;

    private final OrderService orderService;
    //private final LoginService loginService;
    //private final CustomerRepository customerRepository;
    //private final OrderRepository orderRepository;



    @PostMapping("/orders/add")
    public String add(@RequestParam("itemId") Long itemId, @RequestParam("count") int count, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long customer = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        //Long customerId = session.getAttribute(customerRepository.findById());
        //CustomerMember customer = customerRepository.findById();
        orderService.createOrder(itemId, customer, count);
        return "redirect:/itemList";
    }

    @GetMapping("/orders")
    public String orders(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long customerId =(Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        List<Order> orders = orderService.findPersonalOrders(customerId);

        model.addAttribute("orders", orders);

        return "order/order";
    }

    /*
    @GetMapping("/orders")
    public String createForm(@PathVariable Long id, Model model) {
        List<Order> orders = orderService.findPersonalOrders(id);
        model.addAttribute("orders", orders);
        return "order/order";
        //return "order/itemOrder";
    }

    @PostMapping("/orders/add")      //주문 실행
    public String order(@RequestParam("itemId") Long itemId,
                        @RequestParam("customerId") String customerId,
                        @RequestParam("count") int count) {
        //고객 식별자, 주문할 상품 식별자, 수량 정보를 받아서  주문 서비스에 주문을 요청
        orderService.createOrder(itemId, Long.valueOf(customerId), count);
        return "redirect:/itemList";
        //return "redirect:/orders";  //주문이 끝나면 상품주문내역이있는 URL(/orders)로 리다이렉트//주문이 끝나면 상품주문내역이있는 URL(/orders)로 리다이렉트
    }

    @GetMapping("/orders")   //주문 폼 이동
    public String createForm(Model model) {
        List<CustomerMember> customers = customerRepository.findAll();
        List<Item> items = itemRepository.findAll();
        //주문할 고객정보와 상품 정보를 객체(model)에 담아서 뷰에 넘겨줌
        model.addAttribute("customers", customers);
        model.addAttribute("items", items);
        //List<Order> orders = orderRepository.findOrderByMemberId(customerId);
        //model.addAttribute("orders", orders);
        return "order/order";
        //return "order/itemOrder";
    }    */


    @GetMapping("/orderList")  //주문 목록 검색
    public String orderList(Model model) {
        List<Order> orders = orderService.findOrders();
        model.addAttribute("orders", orders);
        return "items/orderList";
    }
    /*public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "items/orderList";
    }*/

    @PostMapping("/orderList/{orderId}/cancel")    //주문 취소
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancel(orderId);
        return "redirect:/orderList";
    }
}
