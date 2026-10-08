package OnlineGroceryStore.controller;

import OnlineGroceryStore.entity.Order;
import OnlineGroceryStore.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    // Place order
    @PostMapping("/place/{userId}")
    public ResponseEntity<?> placeOrder(
            @PathVariable Long userId) {

        try {

            Order order =
                    orderService.placeOrder(userId);

            return ResponseEntity.ok(order);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // Get orders of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getUserOrders(userId)
        );
    }


    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(
            @PathVariable Long id) {

        Order order =
                orderService.getOrderById(id);

        if (order == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(order);
    }


    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }


    // Update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        try {

            return ResponseEntity.ok(
                    orderService.updateOrderStatus(
                            id,
                            status
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // Cancel order
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(
            @PathVariable Long id) {

        try {

            Order cancelledOrder =
                    orderService.cancelOrder(id);

            return ResponseEntity.ok(
                    cancelledOrder
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}