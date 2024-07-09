package uol.compass.gabrielyoshino.ecommerce.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VendaProdutoId implements Serializable {

    private Long vendaId;
    private Long produtoId;

    public VendaProdutoId() {
    }

    public VendaProdutoId(Long vendaId, Long produtoId) {
        this.vendaId = vendaId;
        this.produtoId = produtoId;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaProdutoId that = (VendaProdutoId) o;
        return Objects.equals(vendaId, that.vendaId) && Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendaId, produtoId);
    }
}
