package hello.login.domain.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> findByLoginId(String loginId) {

        return customerRepository.findByLoginId(loginId);
    }

    @Transactional
    public void save(Customer member) {
        customerRepository.save(member);
    }

    public Customer findById(Long memberId) {
        return customerRepository.findById(memberId);
    }

    @Transactional
    public void phoneEdit(Long customerId, String phone) {
        Customer customer = customerRepository.findById(customerId);
        customer.editPhone(phone);
    }
}
