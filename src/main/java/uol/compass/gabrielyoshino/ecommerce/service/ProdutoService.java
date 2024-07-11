package uol.compass.gabrielyoshino.ecommerce.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uol.compass.gabrielyoshino.ecommerce.entity.Produto;
import uol.compass.gabrielyoshino.ecommerce.exception.ProdutoInvalidoException;
import uol.compass.gabrielyoshino.ecommerce.exception.ProdutoNaoEncontradoException;
import uol.compass.gabrielyoshino.ecommerce.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

/*
*  Classe de serviço para o gerenciamento do produtos dentro do sistema de e-commerce.
*  Possui métodos para criar, atualizar, excluir, buscar e listar produtos por diferentes critérios.
*  Ela interage com o {@link ProdutoRepository} para realizar as operações CRUD de produtos no banco de dados
*/
@Service
@Transactional(readOnly = true)
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto criarProduto(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizarProduto(Long id, Produto produto) {
        Produto produtoAtualizado = buscarProduto(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
        produtoAtualizado.setNome(produtoAtualizado.getNome());
        produtoAtualizado.setDescricao(produtoAtualizado.getDescricao());
        produtoAtualizado.setPreco(produtoAtualizado.getPreco());
        produtoAtualizado.setEstoque(produtoAtualizado.getEstoque());
        produtoAtualizado.setAtivo(produtoAtualizado.getAtivo());

        return produtoRepository.save(produtoAtualizado);
    }


    @Transactional
    /* Não pode excluir o produto diretamente, somente mudar seu status para inativo */
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

    /* Ativa um produto */
    @Transactional
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

    /* Busca um produto pelo id */
    public Optional<Produto> buscarProduto(Long id) {
        return produtoRepository.findById(id);
    }

    /* Busca todos os produtos */
    public List<Produto> buscarTodosProdutos() {
        return produtoRepository.findAll();
    }

    /* Busca todos os produtos ativos */
    public List<Produto> buscarProdutosAtivos() {
        return produtoRepository.findByAtivo(true);
    }

    /* Busca todos os produtos inativos */
    public List<Produto> buscarProdutosInativos() {
        return produtoRepository.findByAtivo(false);
    }

    /* Busca os produtos pelo nome */
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
