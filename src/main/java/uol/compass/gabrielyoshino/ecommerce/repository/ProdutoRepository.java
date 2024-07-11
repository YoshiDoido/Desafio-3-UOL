package uol.compass.gabrielyoshino.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uol.compass.gabrielyoshino.ecommerce.entity.Produto;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByAtivo(boolean ativo);

    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:nome%")
    List<Produto> findByNomeContaining(String nome);
}
