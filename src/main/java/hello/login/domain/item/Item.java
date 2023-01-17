package hello.login.domain.item;

import hello.login.exception.NotEnoughStockException;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
//@Getter @Setter
@Data
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;
    private Integer price;
    private Integer quantity;

    private String shop; //매장명

    @OneToMany //(mappedBy = "items", cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles=new ArrayList<>();

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    /*public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }*/

    //==비즈니스 로직==//
    public void addStock(int quantity) {    //stock 증가
        this.quantity += quantity;     //파라미터로 넘어온 수만큼 재고를 늘린다
    }

    public void removeStock(int quantity) {     //stock 감소
        int restStock = this.quantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.quantity = restStock;
    }
}