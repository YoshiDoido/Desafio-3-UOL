package uol.compass.gabrielyoshino.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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

    public Produto() {
    }

    public Produto(Long id, String nome, String descricao, Double preco, Integer estoque, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = ativo;
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

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", estoque=" + estoque +
                ", ativo=" + ativo +
                '}';
    }
}
