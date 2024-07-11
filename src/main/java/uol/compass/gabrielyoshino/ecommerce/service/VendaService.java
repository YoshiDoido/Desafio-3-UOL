package uol.compass.gabrielyoshino.ecommerce.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uol.compass.gabrielyoshino.ecommerce.dto.VendaProdutoDTO;
import uol.compass.gabrielyoshino.ecommerce.entity.Venda;
import uol.compass.gabrielyoshino.ecommerce.entity.VendaProduto;
import uol.compass.gabrielyoshino.ecommerce.exception.EstoqueInsuficienteException;
import uol.compass.gabrielyoshino.ecommerce.exception.VendaNaoEncontradaException;
import uol.compass.gabrielyoshino.ecommerce.repository.VendaRepository;

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

    @Transactional
    @CacheEvict(value = "vendas", allEntries = true)
    public Venda adicionarVenda(@Valid Venda venda) {
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
            if (vendaProduto.getProduto().getEstoque() < 1) {
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
