package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {// Clear product data before each test
        productRepository.productData.clear();

    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);
        Iterator<Product> productIterator = productRepository.findAll();

        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();

        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteExistingProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        productRepository.delete(product.getProductId());

        assertNull(productRepository.findById(product.getProductId()));
    }

    @Test
    void testDeleteNonExistingProduct() {
        productRepository.delete("nonExistingProductId");

        // No exception should occur if the product to be deleted does not exist
    }

    @Test
    void testUpdateExistingProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Sampo Cap Bambang");
        updatedProduct.setProductQuantity(200);

        productRepository.update(updatedProduct);

        Product retrievedProduct = productRepository.findById(product.getProductId());
        assertNotNull(retrievedProduct);
        assertEquals(updatedProduct.getProductName(), retrievedProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), retrievedProduct.getProductQuantity());
    }

    @Test
    void testUpdateNonExistingProduct() {
        Product nonExistingProduct = new Product();
        nonExistingProduct.setProductId("nonExistingProductId");
        nonExistingProduct.setProductName("Non-existing Product");
        nonExistingProduct.setProductQuantity(50);

        productRepository.update(nonExistingProduct);

        // No exception should occur if the product to be updated does not exist
    }

    @Test
    void testUpdateProductQuantity() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductQuantity(200);

        productRepository.update(updatedProduct);

        Product retrievedProduct = productRepository.findById(product.getProductId());
        assertNotNull(retrievedProduct);
        assertEquals(updatedProduct.getProductQuantity(), retrievedProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Updated Sampo Cap Bambang");

        productRepository.update(updatedProduct);

        Product retrievedProduct = productRepository.findById(product.getProductId());
        assertNotNull(retrievedProduct);
        assertEquals(updatedProduct.getProductName(), retrievedProduct.getProductName());
    }




}
