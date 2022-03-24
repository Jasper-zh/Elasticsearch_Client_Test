package cn.yuornotes.es.pojo;
/**
 * @author hao
 * @date 2022/3/18 17:17
 */
public class Product {
    private String name;
    private Integer price;

    public Product() {
    }

    public Product(Integer price) {
        this.price = price;
    }

    public Product(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
