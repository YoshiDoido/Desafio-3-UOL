package uol.compass.gabrielyoshino.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime data;

    @Column(nullable = false)
    private Double total;

    //@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // A causa do erro era por que tinha o cascade = CascadeType.ALL
    @OneToMany(mappedBy = "venda",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VendaProduto> produtosVendidos;


    public Venda() {
        this.produtosVendidos = new ArrayList<>();
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
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
