package com.example.BurguerControl.objetos;

public class Ingrediente {
    private String idIngrediente;
    private String descricaoIngrediente;
    private Float valorIngrediente;

    public Float getValorIngrediente() {
        return valorIngrediente;
    }

    public void setValorIngrediente(Float valorIngrediente) {
        this.valorIngrediente = valorIngrediente;
    }

    public Ingrediente() {
    }

    public String getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(String idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getDescricaoIngrediente() {
        return descricaoIngrediente;
    }

    public void setDescricaoIngrediente(String descricaoIngrediente) {
        this.descricaoIngrediente = descricaoIngrediente;
    }
}
