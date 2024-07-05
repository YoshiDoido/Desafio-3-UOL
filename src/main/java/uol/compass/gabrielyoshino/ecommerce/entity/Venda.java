package uol.compass.gabrielyoshino.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Data da venda não pode ser nula")
    private LocalDateTime data;

    @Column(nullable = false)
    @NotNull(message = "Total da venda não pode ser nulo")
    @PositiveOrZero(message = "Total da venda deve ser maior ou igual a zero")
    private Double total;

    @NotNull(message = "Lista de produtos não pode ser nula")
    @Size(min = 1, message = "Lista de produtos deve conter pelo menos um produto")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produto> produtos;


    public Venda() {
    }

    public Venda(Long id, LocalDateTime data, Double total ,List<Produto> produtos) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", data=" + data +
                ", total=" + total +
                ", produtos=" + produtos +
                '}';
    }
}
