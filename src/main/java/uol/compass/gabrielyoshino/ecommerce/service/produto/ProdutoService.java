package uol.compass.gabrielyoshino.ecommerce.service.produto;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uol.compass.gabrielyoshino.ecommerce.entity.produto.Produto;
import uol.compass.gabrielyoshino.ecommerce.exception.produto.ProdutoInvalidoException;
import uol.compass.gabrielyoshino.ecommerce.exception.produto.ProdutoNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.repository.produto.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    @Caching(put = {@CachePut(value = "produto", key = "#produto.id")},
            evict = {@CacheEvict(value = "produtos", allEntries = true)})
    public Produto criarProduto(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    @Transactional
    @Caching(put = {@CachePut(value = "produto", key = "#id")},
            evict = {@CacheEvict(value = "produtos", allEntries = true)})
    public Produto atualizarProduto(Long id, Produto produto) {
        Produto produtoAtualizado = buscarProduto(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
        produtoAtualizado.setNome(produto.getNome());
        produtoAtualizado.setDescricao(produto.getDescricao());
        produtoAtualizado.setPreco(produto.getPreco());
        produtoAtualizado.setEstoque(produto.getEstoque());
        produtoAtualizado.setAtivo(produto.getAtivo());
        return produtoRepository.save(produtoAtualizado);
    }

    @Transactional
    @Caching(evict = {@CacheEvict(value = "produto", key = "#id"),
            @CacheEvict(value = "produtos", allEntries = true)})
    public Produto desativarProduto(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setAtivo(false);
            return produtoRepository.save(produto);
        } else {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
    }

    @Transactional
    @Caching(evict = {@CacheEvict(value = "produto", key = "#id"),
            @CacheEvict(value = "produtos", allEntries = true)})
    public Produto ativarProduto(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setAtivo(true);
            return produtoRepository.save(produto);
        } else {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
    }

    @Cacheable(value = "produto", key = "#id")
    public Optional<Produto> buscarProduto(Long id) {
        return produtoRepository.findById(id);
    }

    @Cacheable("produtos")
    public List<Produto> buscarTodosProdutos() {
        return produtoRepository.findAll();
    }

    @Cacheable("produtosAtivos")
    public List<Produto> buscarProdutosAtivos() {
        return produtoRepository.findByAtivo(true);
    }

    @Cacheable("produtosInativos")
    public List<Produto> buscarProdutosInativos() {
        return produtoRepository.findByAtivo(false);
    }

    @Cacheable(value = "produtosPorNome", key = "#nome")
    public List<Produto> buscarProdutosPorNome(String nome) {
        return produtoRepository.findByNomeContaining(nome);
    }

    public void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new ProdutoInvalidoException("Nome do produto não pode ser vazio");
        }
        if (produto.getDescricao() == null || produto.getDescricao().isEmpty()) {
            throw new ProdutoInvalidoException("Descrição do produto não pode ser vazia");
        }
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new ProdutoInvalidoException("Preço do produto não pode ser nulo ou negativo");
        }
    }
}