package OnlineGroceryStore.service;

import OnlineGroceryStore.entity.Cart;
import OnlineGroceryStore.entity.CartItem;
import OnlineGroceryStore.entity.Order;
import OnlineGroceryStore.entity.OrderItem;
import OnlineGroceryStore.entity.Product;
import OnlineGroceryStore.entity.User;
import OnlineGroceryStore.repository.CartItemRepository;
import OnlineGroceryStore.repository.CartRepository;
import OnlineGroceryStore.repository.OrderItemRepository;
import OnlineGroceryStore.repository.OrderRepository;
import OnlineGroceryStore.repository.ProductRepository;
import OnlineGroceryStore.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order placeOrder(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Check stock before placing order
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            int requestedQuantity = cartItem.getQuantity();
            int availableStock = product.getStock();

            if (requestedQuantity > availableStock) {

                throw new RuntimeException(
                        "Insufficient stock for "
                        + product.getName()
                        + ". Available stock: "
                        + availableStock
                );
            }
        }

        Order order = new Order();

        order.setUser(user);
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // Create order items and reduce stock
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            int quantity = cartItem.getQuantity();

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            // Reduce product stock
            product.setStock(product.getStock() - quantity);

            productRepository.save(product);
        }

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        cart.setTotalAmount(0);

        cartRepository.save(cart);

        return savedOrder;
    }


    public List<Order> getUserOrders(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }


    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElse(null);
    }


    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }


    public Order updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setStatus(status.toUpperCase());

        return orderRepository.save(order);
    }


    // ==============================
    // CANCEL ORDER
    // ==============================

    @Transactional
    public Order cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        // Prevent cancelling already cancelled orders
        if ("CANCELLED".equalsIgnoreCase(order.getStatus())) {

            throw new RuntimeException(
                    "Order is already cancelled"
            );
        }

        // Prevent cancelling delivered orders
        if ("DELIVERED".equalsIgnoreCase(order.getStatus())) {

            throw new RuntimeException(
                    "Delivered order cannot be cancelled"
            );
        }

        List<OrderItem> orderItems =
                orderItemRepository.findByOrder(order);

        // Return stock to products
        for (OrderItem orderItem : orderItems) {

            Product product = orderItem.getProduct();

            int currentStock = product.getStock();

            int cancelledQuantity = orderItem.getQuantity();

            product.setStock(
                    currentStock + cancelledQuantity
            );

            productRepository.save(product);
        }

        // Change order status
        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }
}