package uol.compass.gabrielyoshino.ecommerce.dto.venda;

import uol.compass.gabrielyoshino.ecommerce.dto.vendaproduto.VendaProdutoDTO;

import java.util.ArrayList;
import java.util.List;

public class VendaDTO {

    private List<VendaProdutoDTO> produtosVendidos;

    public List<VendaProdutoDTO> getProdutosVendidos() {
        return produtosVendidos;
    }

    public void setProdutosVendidos(List<VendaProdutoDTO> produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
    }

    public VendaDTO() {
        this.produtosVendidos = new ArrayList<>();
    }

}
