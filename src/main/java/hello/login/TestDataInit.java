package hello.login;

import hello.login.domain.item.ItemRepository;
import hello.login.domain.customer.Customer;
import hello.login.domain.customer.CustomerRepository;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        //itemRepository.save(new Item("itemA", 10000, 10));
        //itemRepository.save(new Item("itemB", 20000, 20));

        /*Member member = new Member();
        member.setLoginId("t");
        member.setPassword("t");
        member.setName("테스터");
        memberRepository.save(member);*/

        /*Customer customer=new Customer();
        customer.setLoginId("tt");
        customer.setPassword("tt");
        customer.setName("test");
        customerRepository.save(customer);*/
    }

}