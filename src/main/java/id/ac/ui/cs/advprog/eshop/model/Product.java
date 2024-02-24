package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        // Generate a UUID for productId
        this.productId = UUID.randomUUID().toString();
    }

    public Product update(Product newProduct){
        this.setProductName(newProduct.getProductName());
        this.setProductQuantity(newProduct.getProductQuantity());

        return this;
    }
}
