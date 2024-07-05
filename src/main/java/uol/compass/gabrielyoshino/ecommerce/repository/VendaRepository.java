package uol.compass.gabrielyoshino.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uol.compass.gabrielyoshino.ecommerce.entity.Venda;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}
