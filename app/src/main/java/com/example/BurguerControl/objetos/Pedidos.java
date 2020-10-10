package com.example.BurguerControl.objetos;

public class Pedidos {
    private String idPedido;
    private Integer mesa;
    private String infoPedido;
    private Float valorPedido;

    public Pedidos() {
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public String getInfoPedido() {
        return infoPedido;
    }

    public void setInfoPedido(String infoPedido) {
        this.infoPedido = infoPedido;
    }

    public Float getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(Float valorPedido) {
        this.valorPedido = valorPedido;
    }
}
