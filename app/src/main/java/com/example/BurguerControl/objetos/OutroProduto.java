package com.example.BurguerControl.objetos;

public class OutroProduto {
    private String idOutroProduto;
    private String descricaoOutro;
    private Integer quantidadeOutro;
    private Float valorOutro;

    public OutroProduto() {
    }

    public String getIdOutroProduto() {
        return idOutroProduto;
    }

    public void setIdOutroProduto(String idOutroProduto) {
        this.idOutroProduto = idOutroProduto;
    }

    public String getDescricaoOutro() {
        return descricaoOutro;
    }

    public void setDescricaoOutro(String descricaoOutro) {
        this.descricaoOutro = descricaoOutro;
    }

    public Integer getQuantidadeOutro() {
        return quantidadeOutro;
    }

    public void setQuantidadeOutro(Integer quantidadeOutro) {
        this.quantidadeOutro = quantidadeOutro;
    }

    public Float getValorOutro() {
        return valorOutro;
    }

    public void setValorOutro(Float valorOutro) {
        this.valorOutro = valorOutro;
    }
}
