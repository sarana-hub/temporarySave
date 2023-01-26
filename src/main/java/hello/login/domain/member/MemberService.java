package hello.login.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member findById(Long memberId) {

        return memberRepository.findById(memberId);
    }

}
