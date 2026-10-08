package OnlineGroceryStore.repository;

import OnlineGroceryStore.entity.Order;
import OnlineGroceryStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

}