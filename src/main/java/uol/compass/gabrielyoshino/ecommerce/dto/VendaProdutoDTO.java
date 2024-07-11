package uol.compass.gabrielyoshino.ecommerce.dto;

import uol.compass.gabrielyoshino.ecommerce.entity.Produto;
import uol.compass.gabrielyoshino.ecommerce.entity.VendaProduto;

public class VendaProdutoDTO {

    private Long produtoId;
    private Integer quantidade;


    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
