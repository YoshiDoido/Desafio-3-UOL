package uol.compass.gabrielyoshino.ecommerce.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uol.compass.gabrielyoshino.ecommerce.entity.Produto;
import uol.compass.gabrielyoshino.ecommerce.exception.ProdutoNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.service.ProdutoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/criar")
    public ResponseEntity<Produto> criarProduto(@RequestBody @Valid Produto produto) {
        Produto novoProduto = produtoService.criarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Produto> atualizarProduto(@RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizarProduto(produto);
        return ResponseEntity.status(HttpStatus.OK).body(produtoAtualizado);
    }

    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<Produto> desativarProduto(@PathVariable Long id) {
        try {
            Produto produtoDesativado = produtoService.desativarProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body(produtoDesativado);
        } catch (ProdutoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<Produto> ativarProduto(@PathVariable Long id) {
        try {
            Produto produtoAtivado = produtoService.ativarProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body(produtoAtivado);
        } catch (ProdutoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/buscar-produto/{id}")
    public ResponseEntity<Produto> buscarProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarProduto(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<List<Produto>> buscarTodosProdutos() {
        List<Produto> produtos = produtoService.buscarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar-ativos")
    public ResponseEntity<List<Produto>> buscarProdutosAtivos() {
        List<Produto> produtosAtivos = produtoService.buscarProdutosAtivos();
        return ResponseEntity.ok(produtosAtivos);
    }

    @GetMapping("/buscar-inativos")
    public ResponseEntity<List<Produto>> buscarProdutosInativos() {
        List<Produto> produtosInativos = produtoService.buscarProdutosInativos();
        return ResponseEntity.ok(produtosInativos);
    }

    @GetMapping("/buscar-nome/{nome}")
    public ResponseEntity<List<Produto>> buscarProdutosPorNome(@PathVariable String nome) {
        List<Produto> produtos = produtoService.buscarProdutosPorNome(nome);
        return ResponseEntity.ok(produtos);
    }
}
