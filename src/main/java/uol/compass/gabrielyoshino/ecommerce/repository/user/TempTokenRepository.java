package uol.compass.gabrielyoshino.ecommerce.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.gabrielyoshino.ecommerce.entity.user.TempToken;

public interface TempTokenRepository extends JpaRepository<TempToken, Long> {

    TempToken findByToken(String token);
}
