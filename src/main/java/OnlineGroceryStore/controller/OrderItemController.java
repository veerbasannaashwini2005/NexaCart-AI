package OnlineGroceryStore.controller;

import OnlineGroceryStore.entity.OrderItem;
import OnlineGroceryStore.entity.Order;
import OnlineGroceryStore.repository.OrderItemRepository;
import OnlineGroceryStore.repository.OrderRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public OrderItemController(
            OrderItemRepository orderItemRepository,
            OrderRepository orderRepository) {

        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderItems(
            @PathVariable Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElse(null);

        if (order == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        List<OrderItem> items =
                orderItemRepository.findByOrder(order);

        return ResponseEntity.ok(items);
    }
}