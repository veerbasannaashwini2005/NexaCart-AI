package OnlineGroceryStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import OnlineGroceryStore.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}