package hello.login.domain.member;

import hello.login.domain.member.CustomerMember;
import hello.login.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Repository
public class CustomerRepository {
    private static Map<Long, CustomerMember> store = new HashMap<>(); //static 사용
    private static long sequence = 0L;//static 사용

    public CustomerMember save(@Valid CustomerMember member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public CustomerMember findById(Long id) {
        return store.get(id);
    }

    public Optional<CustomerMember> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<CustomerMember> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
