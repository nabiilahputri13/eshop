package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProduct() {
        // Prepare test data
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        // Mock behavior
        when(productRepository.create(any(Product.class))).thenReturn(product);

        // Test method
        Product createdProduct = productService.create(product);

        // Verify behavior
        assertNotNull(createdProduct);
        assertEquals(product.getProductName(), createdProduct.getProductName());
        assertEquals(product.getProductQuantity(), createdProduct.getProductQuantity());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        // Prepare test data
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        // Mock behavior
        when(productRepository.findAll()).thenReturn(productList.iterator());

        // Test method
        List<Product> foundProducts = productService.findAll();

        // Verify behavior
        assertNotNull(foundProducts);
        assertEquals(productList.size(), foundProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProduct() {
        // Prepare test data
        String productId = "testProductId";

        // Test method
        productService.delete(productId);

        // Verify behavior
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testFindProductById() {
        // Prepare test data
        String productId = "testProductId";
        Product product = new Product();
        product.setProductId(productId);

        // Mock behavior
        when(productRepository.findById(productId)).thenReturn(product);

        // Test method
        Product foundProduct = productService.findById(productId);

        // Verify behavior
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getProductId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testUpdateProduct() {
        // Prepare test data
        Product product = new Product();
        product.setProductId("testProductId");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        // Test method
        productService.update(product);

        // Verify behavior
        verify(productRepository, times(1)).update(product);
    }
}
