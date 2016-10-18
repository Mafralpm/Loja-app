package br.unifor.retail.model;

/**
 * Created by vania on 10/10/16.
 */

public class Product {

    private Long idProduct;
    private String descricaoProduct;
    private Float precoProduct;
    private String nomeProduct;
    private Integer quantidadeEmEstoque;
    private String tamanhoProduct;
    private String corProduto;


    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getDescricaoProduct() {
        return descricaoProduct;
    }

    public void setDescricaoProduct(String descricaoProduct) {
        this.descricaoProduct = descricaoProduct;
    }

    public Float getPrecoProduct() {
        return precoProduct;
    }

    public void setPrecoProduct(Float precoProduct) {
        this.precoProduct = precoProduct;
    }

    public String getNomeProduct() {
        return nomeProduct;
    }

    public void setNomeProduct(String nomeProduct) {
        this.nomeProduct = nomeProduct;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public String getTamanhoProduct() {
        return tamanhoProduct;
    }

    public void setTamanhoProduct(String tamanhoProduct) {
        this.tamanhoProduct = tamanhoProduct;
    }

    public String getCorProduto() {
        return corProduto;
    }

    public void setCorProduto(String corProduto) {
        this.corProduto = corProduto;
    }
}
