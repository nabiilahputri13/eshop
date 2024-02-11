package id.ac.ui.cs.advprog.eshop;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.controller.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteProductSuccess() {

        String existingProductId = "existingProductId";

        String viewName = productController.deleteProduct(existingProductId);

        verify(productService, times(1)).delete(existingProductId);
        assert viewName.equals("redirect:/product/list");
    }

    @Test
    public void testDeleteProductFailure() {

        String nonExistingProductId = "nonExistingProductId";

        String viewName = productController.deleteProduct(nonExistingProductId);

        verify(productService, times(1)).delete(nonExistingProductId);
        assert viewName.equals("redirect:/product/list");}

    @Test
    public void testEditProductPage() {

        String existingProductId = "existingProductId";
        Product existingProduct = new Product();
        existingProduct.setProductId(existingProductId);
        existingProduct.setProductName("Existing Product");
        existingProduct.setProductQuantity(10);
        when(productService.findById(existingProductId)).thenReturn(existingProduct);

        String viewName = productController.editProductPage(existingProductId, model);

        verify(productService, times(1)).findById(existingProductId);
        verify(model, times(1)).addAttribute(eq("productId"), anyString());
        verify(model, times(1)).addAttribute(eq("product"), eq(existingProduct));
        assert viewName.equals("editProduct");
    }

    @Test
    public void testEditProductPost() {

        Product editedProduct = new Product();
        editedProduct.setProductName("Edited Product");
        editedProduct.setProductQuantity(20);

        String viewName = productController.editProductPost(editedProduct, model);

        verify(productService, times(1)).update(editedProduct);
        assert viewName.equals("redirect:list");
    }
}