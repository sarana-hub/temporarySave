package hello.login.web;

import hello.login.domain.member.CustomerMember;
import hello.login.domain.member.Member;
import hello.login.repository.CustomerRepository;
import hello.login.repository.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;
    private final SessionManager sessionManager;

    @GetMapping("/")
    public String homeLoginArgumentResolver(@Login Member loginMember, @Login CustomerMember loginCustomer,
                                            Model model) {

        if(loginMember != null) {
            model.addAttribute("member", loginMember);
            return "loginHome";
        }
        else if (loginCustomer != null) {
            model.addAttribute("customer", loginCustomer);
            return "loginCustomerHome";
        }
        else {
            return "home";
        }
    }

}
