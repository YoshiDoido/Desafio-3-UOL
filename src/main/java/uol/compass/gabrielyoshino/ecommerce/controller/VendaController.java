package uol.compass.gabrielyoshino.ecommerce.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uol.compass.gabrielyoshino.ecommerce.entity.Venda;
import uol.compass.gabrielyoshino.ecommerce.service.VendaService;

import java.util.List;

@RestController
@RequestMapping("api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping("/adicionar")
    public ResponseEntity<Venda> adicionarVenda(@RequestBody @Valid Venda venda) {
        Venda novaVenda = vendaService.adicionarVenda(venda);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Venda> atualizarVenda(@PathVariable Long id, @RequestBody @Valid Venda venda) {
        venda.setId(id);
        Venda vendaAtualizada = vendaService.atualizarVenda(venda);
        return ResponseEntity.ok(vendaAtualizada);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirVenda(@PathVariable Long id) {
        vendaService.excluirVenda(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar-venda/{id}")
    public ResponseEntity<Venda> verVenda(@PathVariable Long id) {
        return vendaService.buscarVenda(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/buscar-todas")
    public ResponseEntity<List<Venda>> verTodasVendas() {
        List<Venda> vendas = vendaService.buscarTodasVendas();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/relatiorio/semanal")
    public ResponseEntity<List<Venda>> verVendasSemanal() {
        List<Venda> vendasSemanal = vendaService.verVendasSemanal();
        return ResponseEntity.ok(vendasSemanal);
    }

    @GetMapping("/relatorio/mensal")
    public ResponseEntity<List<Venda>> verVendasMensal() {
        List<Venda> vendasMensal = vendaService.verVendasMensal();
        return ResponseEntity.ok(vendasMensal);
    }
}
