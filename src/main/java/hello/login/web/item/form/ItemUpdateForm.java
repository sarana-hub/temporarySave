package hello.login.web.item.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;

    @NotBlank
    private String shop; //매장명

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    //수정에서는 수량은 자유롭게 변경할 수 있다.
    private Integer quantity;

    private List<MultipartFile> imageFiles;
}