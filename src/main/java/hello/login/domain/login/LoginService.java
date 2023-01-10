package hello.login.domain.login;

import hello.login.domain.member.CustomerMember;
import hello.login.domain.member.Member;
import hello.login.repository.CustomerRepository;
import hello.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;

    /**
     * @return null 로그인 실패
     */
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public CustomerMember login2(String loginId, String password) {
        return customerRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
