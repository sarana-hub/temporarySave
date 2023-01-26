package hello.login.domain.login;

import hello.login.domain.customer.Customer;
import hello.login.domain.member.Member;
import hello.login.domain.customer.CustomerRepository;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;

    /**
     * @return null 로그인 실패
     */
    public Member login(String loginId, String password) {
        List<Member> members = memberRepository.findByLoginId(loginId);
        if (members == null) {
            return null;
        } else {
            for (Member member : members) {
                if (member.getPassword().equals(password)) {
                    return member;
                }
            }
        }
        return null;
    }

    public Customer login2(String loginId, String password) {
        List<Customer> customers = customerRepository.findByLoginId(loginId);
        if (customers == null) {
            return null;
        } else {
            for (Customer customer : customers) {
                if (customer.getPassword().equals(password)) {
                    return customer;
                }
            }
        }
        return null;
    }
}
