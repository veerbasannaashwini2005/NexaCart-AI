package OnlineGroceryStore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import OnlineGroceryStore.entity.CartItem;
import OnlineGroceryStore.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add product to cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {

        try {
            CartItem cartItem =
                    cartService.addToCart(userId, productId, quantity);

            return ResponseEntity.ok(cartItem);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // View cart
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                cartService.getCartItems(userId)
        );
    }

    // Update quantity
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {

        try {
            CartItem updatedItem =
                    cartService.updateQuantity(cartItemId, quantity);

            return ResponseEntity.ok(updatedItem);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Remove product
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long cartItemId) {

        try {
            cartService.removeFromCart(cartItemId);

            return ResponseEntity.ok(
                    "Product removed from cart successfully"
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}