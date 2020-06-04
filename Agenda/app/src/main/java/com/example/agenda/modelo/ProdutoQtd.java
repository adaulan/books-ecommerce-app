package com.example.agenda.modelo;

import java.io.Serializable;

public class ProdutoQtd implements Serializable {


    private Long idProduto;
    private Long qtd;


    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getQtd() {
        return qtd;
    }

    public void setQtd(Long qtd) {
        this.qtd = qtd;
    }
}
