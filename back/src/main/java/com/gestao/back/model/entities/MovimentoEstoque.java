package com.gestao.back.model.entities;

import com.gestao.back.model.enums.TipoMovimento;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentos_estoque")
public class MovimentoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = true)
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimento tipo;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(name = "nome_produto_snapshot", nullable = false)
    private String nomeProdutoSnapshot;

    private String Motivo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;



    public MovimentoEstoque(Long id, int quantidade, Produto produto, TipoMovimento tipo, LocalDateTime data,String nomeProdutoSnapshot, String motivo, Usuario usuarioId) {
        this.id = id;
        this.quantidade = quantidade;
        this.produto = produto;
        this.tipo = tipo;
        this.data = data;
        this.nomeProdutoSnapshot = nomeProdutoSnapshot;
        Motivo = motivo;
        this.usuario = usuarioId;
    }

    public MovimentoEstoque() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public TipoMovimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimento tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }

    public String getNomeProdutoSnapshot() {
        return nomeProdutoSnapshot;
    }

    public void setNomeProdutoSnapshot(String nomeProdutoSnapshot) {
        this.nomeProdutoSnapshot = nomeProdutoSnapshot;
    }
}


