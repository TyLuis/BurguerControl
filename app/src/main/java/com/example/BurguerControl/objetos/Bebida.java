package com.example.BurguerControl.objetos;

public class Bebida {
    private String idBebida;
    private String descricaoBebida;
    private Integer quantidadeBebida;
    private Float valorBebida;

    public Bebida() {
    }

    public String getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(String idBebida) {
        this.idBebida = idBebida;
    }

    public String getDescricaoBebida() {
        return descricaoBebida;
    }

    public void setDescricaoBebida(String descricaoBebida) {
        this.descricaoBebida = descricaoBebida;
    }

    public Integer getQuantidadeBebida() {
        return quantidadeBebida;
    }

    public void setQuantidadeBebida(Integer quantidadeBebida) {
        this.quantidadeBebida = quantidadeBebida;
    }

    public Float getValorBebida() {
        return valorBebida;
    }

    public void setValorBebida(Float valorBebida) {
        this.valorBebida = valorBebida;
    }
}
