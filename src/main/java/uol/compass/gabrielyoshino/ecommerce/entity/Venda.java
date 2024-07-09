package uol.compass.gabrielyoshino.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Data da venda não pode ser nula")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime data;

    @Column(nullable = false)
    @NotNull(message = "Total da venda não pode ser nulo")
    @PositiveOrZero(message = "Total da venda deve ser maior ou igual a zero")
    private Double total;

    @NotNull(message = "Lista de produtos não pode ser nula")
    @Size(min = 1, message = "Lista de produtos deve conter pelo menos um produto")
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VendaProduto> produtosVendidos;


    public Venda() {
    }

    public Venda(Long id, LocalDateTime data, Double total ,List<VendaProduto> produtosVendidos) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.produtosVendidos = produtosVendidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Data da venda não pode ser nula") LocalDateTime getData() {
        return data;
    }

    public void setData(@NotNull(message = "Data da venda não pode ser nula") LocalDateTime data) {
        this.data = data;
    }

    public @NotNull(message = "Total da venda não pode ser nulo") @PositiveOrZero(message = "Total da venda deve ser maior ou igual a zero") Double getTotal() {
        return total;
    }

    public void setTotal(@NotNull(message = "Total da venda não pode ser nulo") @PositiveOrZero(message = "Total da venda deve ser maior ou igual a zero") Double total) {
        this.total = total;
    }

    public @NotNull(message = "Lista de produtos não pode ser nula") @Size(min = 1, message = "Lista de produtos deve conter pelo menos um produto") List<VendaProduto> getProdutosVendidos() {
        return produtosVendidos;
    }

    public void setProdutosVendidos(@NotNull(message = "Lista de produtos não pode ser nula") @Size(min = 1, message = "Lista de produtos deve conter pelo menos um produto") List<VendaProduto> produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", data=" + data +
                ", total=" + total +
                ", produtosVendidos=" + produtosVendidos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venda venda)) return false;
        return Objects.equals(id, venda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
