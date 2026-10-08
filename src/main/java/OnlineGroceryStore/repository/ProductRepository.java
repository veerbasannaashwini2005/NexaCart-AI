package OnlineGroceryStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import OnlineGroceryStore.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}