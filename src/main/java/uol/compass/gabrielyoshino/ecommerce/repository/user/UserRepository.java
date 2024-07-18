package uol.compass.gabrielyoshino.ecommerce.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.gabrielyoshino.ecommerce.entity.user.TempToken;
import uol.compass.gabrielyoshino.ecommerce.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByTempToken(TempToken tempToken);
}
