package OnlineGroceryStore.controller;

import OnlineGroceryStore.repository.OrderRepository;
import OnlineGroceryStore.repository.ProductRepository;
import OnlineGroceryStore.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public AdminController(
            ProductRepository productRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {

        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStatistics() {

        long totalProducts =
                productRepository.count();

        long totalOrders =
                orderRepository.count();

        long totalCustomers =
                userRepository.count();

        double totalRevenue =
                orderRepository.findAll()
                        .stream()
                        .mapToDouble(order ->
                                order.getTotalAmount())
                        .sum();

        Map<String, Object> stats =
                new HashMap<>();

        stats.put("totalProducts", totalProducts);
        stats.put("totalOrders", totalOrders);
        stats.put("totalCustomers", totalCustomers);
        stats.put("totalRevenue", totalRevenue);

        return stats;
    }
}