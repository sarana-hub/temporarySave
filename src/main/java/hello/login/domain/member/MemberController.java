package hello.login.domain.member;

import hello.login.web.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {

        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        memberRepository.save(member);
        return "redirect:/";
    }

    @GetMapping("/info")
    public String info(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id);
        model.addAttribute("member", member);

        return "members/memberInfo";
    }


    /*@GetMapping("/info")
    //public String info(HttpServletRequest request, @ModelAttribute("member") Model model) {
    public String info(@ModelAttribute("member") Model model){
        //HttpSession session = request.getSession();
        //Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //Member member = memberRepository.findById(memberId);
        //model.addAttribute("member", member);

        return "members/memberInfo";
    }*/
}
