package uol.compass.gabrielyoshino.ecommerce.dto;

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
