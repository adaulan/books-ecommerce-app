package com.example.agenda.modelo;

import java.io.Serializable;

public class ItemVenda implements Serializable {

    private Long id;
    private Produto produto;
    private Integer quantidade;
    private Double valorUnitarioVendido;

    public ItemVenda(Long id, Produto produto, Integer quantidade, Double valorUnitarioVendido) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitarioVendido = valorUnitarioVendido;
    }

    public ItemVenda(Produto produto, Integer quantidade, Double valorUnitarioVendido) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitarioVendido = valorUnitarioVendido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitarioVendido() {
        return valorUnitarioVendido;
    }

    public void setValorUnitarioVendido(Double valorUnitarioVendido) {
        this.valorUnitarioVendido = valorUnitarioVendido;
    }

    @Override
    public String toString() {
        return produto.toString();
    }

}
