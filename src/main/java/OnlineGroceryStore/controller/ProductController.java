package OnlineGroceryStore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import OnlineGroceryStore.entity.Product;
import OnlineGroceryStore.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }


    // Get product by ID
    @GetMapping("/{id}")
    public Product getProduct(
            @PathVariable Long id) {

        return productService.getProductById(id);
    }


    // Add product
    @PostMapping
    public Product addProduct(
            @RequestBody Product product) {

        return productService.createProduct(product);
    }


    // Update product
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        return productService.updateProduct(
                id,
                product
        );
    }


    // Delete product
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }
}