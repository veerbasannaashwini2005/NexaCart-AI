package OnlineGroceryStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import OnlineGroceryStore.entity.Order;
import OnlineGroceryStore.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);
}