package OnlineGroceryStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import OnlineGroceryStore.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}