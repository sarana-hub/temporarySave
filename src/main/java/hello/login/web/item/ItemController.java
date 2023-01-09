package hello.login.web.item;

import hello.login.domain.file.FileStore;
import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.item.UploadFile;
import hello.login.web.item.form.ItemSaveForm;
import hello.login.web.item.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
//@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    /** 상품 목록 */
    @GetMapping("/items")
    public String items(Model model) {
        //로그인 여부 체크
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "items/items";
    }

    /** 상품 상세(조회) */
    @GetMapping("/items/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        //로그인 여부 체크
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "items/item";
    }

    /** 상품 등록 폼 */
    @GetMapping("/items/add")
    public String addForm(Model model) {
        //로그인 여부 체크
        model.addAttribute("item", new Item());
        return "items/addForm";
    }

    @PostMapping("/items/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/addForm";
        }

        //성공 로직 //데이터베이스에 저장
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());
        item.setImageFiles(storeImageFiles);

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    /** 상품 수정 */
    @GetMapping("/items/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "items/editForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/editForm";
        }

        Item itemParam = itemRepository.findById(itemId);
        itemParam.setItemName(form.getItemName());
        itemParam.setPrice(form.getPrice());
        itemParam.setQuantity(form.getQuantity());
        if(!storeImageFiles.isEmpty()) {
            itemParam.setImageFiles(storeImageFiles);
        }
        itemRepository.update(itemId, itemParam);

        redirectAttributes.addAttribute("itemId", itemParam.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/items/{itemId}";
    }

}
