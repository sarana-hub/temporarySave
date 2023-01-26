package hello.login.domain.customer;

import hello.login.domain.login.SessionConst;
import hello.login.web.customer.CustomerSaveForm;
import hello.login.web.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("customer", new CustomerSaveForm());
        return "members/addCustomerForm";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("customer") CustomerSaveForm form, BindingResult bindingResult) {
        List<Customer> sameIdMember = customerService.findByLoginId(form.getLoginId());

        if (!sameIdMember.isEmpty()) {
            bindingResult.rejectValue("loginId","error.already","이미 존재하는 아이디 입니다.");
        }
        if (bindingResult.hasErrors()) {
            return "members/addCustomerForm";
        }

        Customer customer = new Customer(form.getLoginId(),form.getPassword(), form.getName(), form.getPhone());
        customerService.save(customer);

        return "redirect:/";
    }

    /*@PostMapping("/add")
    public String save(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addCustomerForm";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }*/

    @GetMapping("/info")
    public String info(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        return "members/customerInfo";
    }

    @PostMapping("/phone/editForm")
    public String editForm(String phone, Model model) {
        model.addAttribute("phone", phone);
        return "members/infoEditForm";
    }

    @PostMapping("/phone/edit")
    public String edit(String phone, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        customerService.phoneEdit(customerId, phone);

        return "redirect:/members/customerInfo";
    }
}
