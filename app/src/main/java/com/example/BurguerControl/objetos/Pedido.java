package com.example.BurguerControl.objetos;

public class Pedido {
    private Integer mesa, quantBurguer, quantBebida;
    private String burguer, addIngrediente, removeIngrediente, bebida;

    public Pedido() {
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public Integer getQuantBurguer() {
        return quantBurguer;
    }

    public void setQuantBurguer(Integer quantBurguer) {
        this.quantBurguer = quantBurguer;
    }

    public Integer getQuantBebida() {
        return quantBebida;
    }

    public void setQuantBebida(Integer quantBebida) {
        this.quantBebida = quantBebida;
    }

    public String getBurguer() {
        return burguer;
    }

    public void setBurguer(String burguer) {
        this.burguer = burguer;
    }

    public String getAddIngrediente() {
        return addIngrediente;
    }

    public void setAddIngrediente(String addIngrediente) {
        this.addIngrediente = addIngrediente;
    }

    public String getRemoveIngrediente() {
        return removeIngrediente;
    }

    public void setRemoveIngrediente(String removeIngrediente) {
        this.removeIngrediente = removeIngrediente;
    }

    public String getBebida() {
        return bebida;
    }

    public void setBebida(String bebida) {
        this.bebida = bebida;
    }
}
