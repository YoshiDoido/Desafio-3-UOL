package uol.compass.gabrielyoshino.ecommerce.service.venda;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uol.compass.gabrielyoshino.ecommerce.dto.venda.VendaDTO;
import uol.compass.gabrielyoshino.ecommerce.dto.vendaproduto.VendaProdutoDTO;
import uol.compass.gabrielyoshino.ecommerce.entity.produto.Produto;
import uol.compass.gabrielyoshino.ecommerce.entity.venda.Venda;
import uol.compass.gabrielyoshino.ecommerce.entity.vendaproduto.VendaProduto;
import uol.compass.gabrielyoshino.ecommerce.exception.produto.EstoqueInsuficienteException;
import uol.compass.gabrielyoshino.ecommerce.exception.produto.ProdutoNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.repository.venda.VendaRepository;
import uol.compass.gabrielyoshino.ecommerce.service.produto.ProdutoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    @CacheEvict(value = "vendas", allEntries = true)
    public Venda adicionarVenda(@Valid VendaDTO dto) {
        Venda venda = new Venda();
        for (VendaProdutoDTO vendaProdutoDTO : dto.getProdutosVendidos()) {
            Produto produto = produtoService.buscarProduto(vendaProdutoDTO.getProdutoId())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
            VendaProduto vendaProduto = new VendaProduto();
            vendaProduto.setProduto(produto);
            vendaProduto.setQuantidade(vendaProdutoDTO.getQuantidade());
            vendaProduto.setVenda(venda);
            venda.getProdutosVendidos().add(vendaProduto);
        }
        validarEstoque(venda.getProdutosVendidos());
        venda.setTotal(calcularTotal(venda.getProdutosVendidos()));
        return vendaRepository.save(venda);
    }

    @Transactional
    @CacheEvict(value = "vendas", allEntries = true)
    public Venda atualizarVenda(Venda venda) {
        validarEstoque(venda.getProdutosVendidos());
        venda.setTotal(calcularTotal(venda.getProdutosVendidos()));
        return vendaRepository.save(venda);
    }

    @Transactional
    @CacheEvict(value = "vendas", allEntries = true)
    public void excluirVenda(Long id) {
        vendaRepository.deleteById(id);
    }

    @Cacheable(value = "vendas")
    public Optional<Venda> buscarVenda(Long id) {
        return vendaRepository.findById(id);
    }

    @Cacheable(value = "vendas")
    public List<Venda> buscarTodasVendas() {
        return vendaRepository.findAll();
    }

    // Mostrar vendas da semana atual
    @Cacheable("vendas")
    public List<Venda> verVendasSemanal() {
        LocalDateTime inicioSemana = LocalDate.now().with(TemporalAdjusters.previousOrSame(LocalDate.now().getDayOfWeek())).atStartOfDay();
        LocalDateTime fimSemana = inicioSemana.plusWeeks(1).minusSeconds(1);
        return vendaRepository.findByDataBetween(inicioSemana, fimSemana);
    }

    // Mostrar vendas do mês atual
    @Cacheable("vendas")
    public List<Venda> verVendasMensal() {
        LocalDateTime inicioMes = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime fimMes = inicioMes.plusMonths(1).minusSeconds(1);
        return vendaRepository.findByDataBetween(inicioMes, fimMes);
    }

    private void validarEstoque(List<VendaProduto> produtosVendidos) {
        for (VendaProduto vendaProduto : produtosVendidos) {
            Integer estoque = vendaProduto.getProduto().getEstoque();
            if (estoque == null || estoque < 1) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto " + vendaProduto.getProduto().getNome());
            }
        }
    }

    private Double calcularTotal(List<VendaProduto> produtosVendidos) {
        return produtosVendidos.stream()
                .mapToDouble(vendaProduto -> vendaProduto.getProduto().getPreco() * vendaProduto.getQuantidade())
                .sum();
    }
}
