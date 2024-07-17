package uol.compass.gabrielyoshino.ecommerce.controller.produto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uol.compass.gabrielyoshino.ecommerce.entity.produto.Produto;
import uol.compass.gabrielyoshino.ecommerce.exception.produto.ProdutoNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.service.produto.ProdutoService;

import java.util.List;

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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody @Valid Produto produto) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }


    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<Void> desativarProduto(@PathVariable Long id) {
        try {
            produtoService.desativarProduto(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProdutoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarProduto(@PathVariable Long id) {
        try {
            produtoService.ativarProduto(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProdutoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/buscar-produto/{id}")
    public ResponseEntity<Produto> buscarProduto(@PathVariable Long id) {
        return produtoService.buscarProduto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
