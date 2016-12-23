package br.unifor.retail.singleton;

/**
 * Created by mafra on 19/10/16.
 */

public class SingletonCar {
    private String imageProduct;
    private String nome, preco;
    private long produto_id, pedido_id;

    public SingletonCar() {
    }

    public SingletonCar(String imageProduct, String nome, String preco, long pedido_id, long produto_id) {
        this.imageProduct = imageProduct;
        this.nome = nome;
        this.preco = preco;
        this.produto_id = produto_id;
        this.pedido_id = pedido_id;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public long getproduto_id() {
        return produto_id;
    }

    public void setproduto_id(long produto_id) {
        this.produto_id = produto_id;
    }

    public long getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(long pedido_id) {
        this.pedido_id = pedido_id;
    }
}
