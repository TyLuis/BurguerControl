package com.example.BurguerControl.objetos;

public class Burguer {
    private String idBurguer;
    private String descricaoBurguer;
    private Integer estoqueBurguer;
    private Float valorBurguer;

    public Burguer() {
    }

    public String getIdBurguer() {
        return idBurguer;
    }

    public void setIdBurguer(String idBurguer) {
        this.idBurguer = idBurguer;
    }

    public String getDescricaoBurguer() {
        return descricaoBurguer;
    }

    public void setDescricaoBurguer(String descricaoBurguer) {
        this.descricaoBurguer = descricaoBurguer;
    }

    public Integer getEstoqueBurguer() {
        return estoqueBurguer;
    }

    public void setEstoqueBurguer(Integer estoqueBurguer) {
        this.estoqueBurguer = estoqueBurguer;
    }

    public Float getValorBurguer() {
        return valorBurguer;
    }

    public void setValorBurguer(Float valorBurguer) {
        this.valorBurguer = valorBurguer;
    }
}
