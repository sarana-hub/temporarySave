package hello.login.web.member;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
public class MemberLoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
