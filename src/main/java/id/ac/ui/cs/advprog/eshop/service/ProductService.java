package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
    void delete(String productId);
    public Product findById(String productId); // Add this method
    public void update(Product product); // Add this method
}