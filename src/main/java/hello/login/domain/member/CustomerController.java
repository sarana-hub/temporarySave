package hello.login.domain.member;

import hello.login.web.SessionConst;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    //@GetMapping("/customers/add")
    @GetMapping("/add")
    public String addForm(@ModelAttribute("customer") CustomerMember customerMember) {
        return "members/addCustomerForm";
    }

    //@PostMapping("/customers/add")
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute CustomerMember customerMember, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addCustomerForm";
        }
        customerRepository.save(customerMember);
        return "redirect:/";
    }

    @GetMapping("/info")
    public String info(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        CustomerMember customer = customerRepository.findById(customerId);
        model.addAttribute("customer", customer);

        return "members/customerInfo";
    }

    @PostMapping("/phone/editForm")
    public String editForm(String phone, Model model) {
        model.addAttribute("phone", phone);
        return "members/infoEditForm";
    }

    @PostMapping("/phone/edit")
    public String edit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        CustomerMember customer = customerRepository.findById(id);
        customer.editPhone(customer.getPhone());

        return "redirect:/members/customerInfo";
    }
}
