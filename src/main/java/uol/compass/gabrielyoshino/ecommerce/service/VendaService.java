package uol.compass.gabrielyoshino.ecommerce.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uol.compass.gabrielyoshino.ecommerce.entity.Produto;
import uol.compass.gabrielyoshino.ecommerce.entity.Venda;
import uol.compass.gabrielyoshino.ecommerce.exception.EstoqueInsuficienteException;
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
    public Venda adicionarVenda(@Valid Venda venda) {
        validarEstoque(venda.getProdutos());
        venda.setData(LocalDateTime.now());
        venda.setTotal(calcularTotal(venda.getProdutos()));
        return vendaRepository.save(venda);
    }

    @Transactional
    public Venda atualizarVenda(Venda venda) {
        validarEstoque(venda.getProdutos());
        venda.setTotal(calcularTotal(venda.getProdutos()));
        return vendaRepository.save(venda);
    }

    @Transactional
    public void excluirVenda(Long id) {
        vendaRepository.deleteById(id);
    }

    public Optional<Venda> verVenda(Long in) {
        return vendaRepository.findById(in);
    }

    public List<Venda> verVendas() {
        return vendaRepository.findAll();
    }

    // Mostrar venda semanal
    public List<Venda> verVendasSemanal() {
        LocalDateTime comecoDaSemana = LocalDate.now().with(TemporalAdjusters.previousOrSame(LocalDate.now().getDayOfWeek())).atStartOfDay();
        LocalDateTime fimDaSemana = comecoDaSemana.plusWeeks(1).minusSeconds(1);
        return vendaRepository.findByDataBetween(comecoDaSemana, fimDaSemana);
    }

    // Mostrar venda mensal
    public List<Venda> verVendasMensal() {
        LocalDateTime comecoDaSemana = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime fimDaSemana = comecoDaSemana.plusMonths(1).minusSeconds(1);
        return vendaRepository.findByDataBetween(comecoDaSemana, fimDaSemana);
    }

    private void validarEstoque(List<Produto> produtos) {
        for (Produto produto : produtos) {
            if (produto.getEstoque() < 1) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto " + produto.getNome());
            }
        }
    }

    private Double calcularTotal(List<Produto> produtos) {
        return produtos.stream().mapToDouble(Produto::getPreco).sum();
    }
}
