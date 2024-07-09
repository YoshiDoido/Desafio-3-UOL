package uol.compass.gabrielyoshino.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import uol.compass.gabrielyoshino.ecommerce.dto.VendaProdutoDTO;

@Entity
public class VendaProduto {

    @EmbeddedId
    private VendaProdutoId id = new VendaProdutoId();

    @NotNull(message = "Quantidade não pode ser nula")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vendaId")
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produtoId")
    private Produto produto;

    public VendaProduto() {
    }

    public VendaProduto(Venda venda, Produto produto, Integer quantidade) {
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.id = new VendaProdutoId(venda.getId(), produto.getId());
    }

    // Conversão de VendaProduto para VendaProdutoDTO
    public VendaProdutoDTO toDTO() {
        VendaProdutoDTO dto = new VendaProdutoDTO();
        dto.setProdutoId(this.produto.getId());
        dto.setQuantidade(this.quantidade);
        return dto;
    }

    public VendaProdutoId getId() {
        return id;
    }

    public void setId(VendaProdutoId id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
