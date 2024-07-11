package uol.compass.gabrielyoshino.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uol.compass.gabrielyoshino.ecommerce.dto.ProdutoDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto não pode estar vazio")
    private String nome;

    @NotBlank(message = "Descrição do produto não pode estar vazia")
    private String descricao;

    @NotNull(message = "Preço do produto não pode ser nulo")
    @Positive(message = "Preço do produto deve ser maior que zero")
    private Double preco;

    @NotNull(message = "Estoque do produto não pode ser nulo")
    @Min(value = 0, message = "Estoque do produto não pode ser menor que zero")
    private Integer estoque;

    @NotNull(message = "Status ativo do produto não pode ser nulo")
    private Boolean ativo;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "produto")
    private List<VendaProduto> vendasProdutos;

    public Produto() {
        this.ativo = true;
    }

    public Produto(Long id, String nome, String descricao, Double preco, Integer estoque, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = ativo;
    }

    // Conversão de Produto para ProdutoDTO
    public ProdutoDTO toDTO() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(this.id);
        dto.setNome(this.nome);
        dto.setDescricao(this.descricao);
        dto.setPreco(this.preco);
        dto.setEstoque(this.estoque);
        dto.setAtivo(this.ativo);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public List<VendaProduto> getVendasProdutos() {
        return vendasProdutos;
    }

    public void setVendasProdutos(List<VendaProduto> vendasProdutos) {
        this.vendasProdutos = vendasProdutos;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", estoque=" + estoque +
                ", ativo=" + ativo +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto produto)) return false;
        return Objects.equals(id, produto.id) &&
                Objects.equals(nome, produto.nome) &&
                Objects.equals(descricao, produto.descricao) &&
                Objects.equals(preco, produto.preco) &&
                Objects.equals(estoque, produto.estoque) &&
                Objects.equals(ativo, produto.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, preco, estoque, ativo);
    }
}
