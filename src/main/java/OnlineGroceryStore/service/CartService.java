package OnlineGroceryStore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import OnlineGroceryStore.entity.Cart;
import OnlineGroceryStore.entity.CartItem;
import OnlineGroceryStore.entity.Product;
import OnlineGroceryStore.entity.User;
import OnlineGroceryStore.repository.CartItemRepository;
import OnlineGroceryStore.repository.CartRepository;
import OnlineGroceryStore.repository.ProductRepository;
import OnlineGroceryStore.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // =========================================================
    // ADD PRODUCT TO CART
    // =========================================================

    @Transactional
    public CartItem addToCart(
            Long userId,
            Long productId,
            int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than 0"
            );
        }

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        // Check stock
        if (product.getStock() == null ||
                product.getStock() <= 0) {

            throw new RuntimeException(
                    "Product is out of stock"
            );
        }

        if (quantity > product.getStock()) {
            throw new RuntimeException(
                    "Only " + product.getStock()
                    + " items available in stock"
            );
        }

        // Find existing cart
        Cart cart = cartRepository.findByUser(user)
                .orElse(null);

        // Create cart if it doesn't exist
        if (cart == null) {

            cart = new Cart();
            cart.setUser(user);
            cart.setTotalAmount(0.0);

            cart = cartRepository.save(cart);
        }

        // Check whether product already exists in cart
        CartItem existingItem =
                cartItemRepository
                        .findByCartAndProduct(cart, product)
                        .orElse(null);

        if (existingItem != null) {

            int newQuantity =
                    existingItem.getQuantity() + quantity;

            if (newQuantity > product.getStock()) {
                throw new RuntimeException(
                        "Cannot add more than available stock. "
                        + "Available stock: "
                        + product.getStock()
                );
            }

            existingItem.setQuantity(newQuantity);

            CartItem updatedItem =
                    cartItemRepository.save(existingItem);

            updateCartTotal(cart);

            return updatedItem;
        }

        // Create new cart item
        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        CartItem savedItem =
                cartItemRepository.save(cartItem);

        // Update total
        updateCartTotal(cart);

        return savedItem;
    }

    // =========================================================
    // GET CART ITEMS
    // =========================================================

    public List<CartItem> getCartItems(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElse(null);

        if (cart == null) {
            return List.of();
        }

        return cartItemRepository.findByCart(cart);
    }

    // =========================================================
    // UPDATE QUANTITY
    // =========================================================

    @Transactional
    public CartItem updateQuantity(
            Long cartItemId,
            int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than 0"
            );
        }

        CartItem cartItem =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart item not found"
                                ));

        Product product = cartItem.getProduct();

        // Check stock
        if (quantity > product.getStock()) {
            throw new RuntimeException(
                    "Only " + product.getStock()
                    + " items available in stock"
            );
        }

        cartItem.setQuantity(quantity);

        CartItem updatedItem =
                cartItemRepository.save(cartItem);

        updateCartTotal(cartItem.getCart());

        return updatedItem;
    }

    // =========================================================
    // REMOVE PRODUCT FROM CART
    // =========================================================

    @Transactional
    public void removeFromCart(Long cartItemId) {

        CartItem cartItem =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart item not found"
                                ));

        Cart cart = cartItem.getCart();

        cartItemRepository.delete(cartItem);

        updateCartTotal(cart);
    }

    // =========================================================
    // UPDATE CART TOTAL
    // =========================================================

    private void updateCartTotal(Cart cart) {

        List<CartItem> items =
                cartItemRepository.findByCart(cart);

        double total = 0.0;

        for (CartItem item : items) {

            if (item.getProduct() != null &&
                    item.getProduct().getPrice() != null) {

                total +=
                        item.getProduct().getPrice()
                        * item.getQuantity();
            }
        }

        cart.setTotalAmount(total);

        cartRepository.save(cart);
    }
}