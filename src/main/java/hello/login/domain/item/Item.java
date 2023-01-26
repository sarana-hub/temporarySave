package hello.login.domain.item;

import hello.login.domain.member.Member;
import hello.login.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Data
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "dtype")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;
    private Integer price;
    private Integer quantity; //재고수량

    private String shop; //매장명

    @OneToMany //(mappedBy = "items", cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles=new ArrayList<>();

    /*@ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();*/

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    //==비즈니스 로직==//

    public void addStock(int q) {    //stock 증가
        this.quantity += q;     //파라미터로 넘어온 수만큼 재고를 늘린다
    }

    public void removeStock(int count) {     //stock 감소
        int restStock = this.quantity - count;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다");
        }
        this.quantity = restStock;
    }
}