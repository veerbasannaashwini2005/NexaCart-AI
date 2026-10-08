package OnlineGroceryStore.service;

import OnlineGroceryStore.entity.Product;
import OnlineGroceryStore.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElse(null);
    }

    public Product createProduct(Product product) {

        validateProduct(product);

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {

        Product existingProduct =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        validateProduct(updatedProduct);

        existingProduct.setName(
                updatedProduct.getName());

        existingProduct.setPrice(
                updatedProduct.getPrice());

        existingProduct.setDescription(
                updatedProduct.getDescription());

        existingProduct.setStock(
                updatedProduct.getStock());

        existingProduct.setImageUrl(
                updatedProduct.getImageUrl());

        existingProduct.setCategory(
                updatedProduct.getCategory());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        productRepository.delete(product);
    }


    private void validateProduct(Product product) {

        if (product.getName() == null ||
                product.getName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Product name is required");
        }


        if (product.getPrice() == null ||
                product.getPrice() <= 0) {

            throw new RuntimeException(
                    "Product price must be greater than 0");
        }


        if (product.getStock() == null ||
                product.getStock() < 0) {

            throw new RuntimeException(
                    "Product stock cannot be negative");
        }


        if (product.getDescription() == null ||
                product.getDescription().trim().isEmpty()) {

            throw new RuntimeException(
                    "Product description is required");
        }
    }
}