package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    public List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public void delete(String productId) {
        Iterator<Product> iterator = productData.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (productId != null && productId.equals(product.getProductId())) {
                iterator.remove();
                break;
            }
        }
    }

    public Product findById(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public void update(Product product) {
        for (int i = 0; i < productData.size(); i++) {
            Product existingProduct = productData.get(i);
            if (existingProduct.getProductId().equals(product.getProductId())) {
                productData.set(i, product);
                break;
            }
        }
    }

//        return null; // If product not found
}

//    public void update(Product updatedProduct) {
//        for (int i = 0; i < productData.size(); i++) {
//            Product product = productData.get(i);
//            if (product.getProductId().equals(updatedProduct.getProductId())) {
//                productData.set(i, updatedProduct);
//                return;
//            }
//        }
//    }


//}