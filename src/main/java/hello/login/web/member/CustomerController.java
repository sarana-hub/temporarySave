package hello.login.web.member;

import hello.login.domain.member.CustomerMember;
import hello.login.domain.member.Member;
import hello.login.repository.CustomerRepository;
import hello.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("customer") CustomerMember customerMember) {
        return "members/addCustomerForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute CustomerMember customerMember, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addCustomerForm";
        }

        customerRepository.save(customerMember);
        return "redirect:/";
    }
}
