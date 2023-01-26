package hello.login.domain.member;

import hello.login.domain.login.SessionConst;
import hello.login.web.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("member", new MemberSaveForm());

        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("member") MemberSaveForm form, BindingResult bindingResult) {
        List<Member> sameIdMember = memberService.findByLoginId(form.getLoginId());

        if (!sameIdMember.isEmpty()) {
            bindingResult.rejectValue("loginId","error.already","이미 존재하는 아이디 입니다.");
        }
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        Member member = new Member(form.getLoginId(),form.getPassword(), form.getName(), form.getShop());
        memberService.save(member);
        return "redirect:/";
    }

    @GetMapping("/info")
    public String info(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Member member = memberService.findById(memberId);
        model.addAttribute("member", member);

        return "members/memberInfo";
    }

}
