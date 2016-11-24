package br.unifor.retail.singleton;

/**
 * Created by mafra on 19/10/16.
 */

public class SingletonCar {
    private String imageProduct;
    private String nome, preco;

    public SingletonCar() {
    }
    public SingletonCar(String image, String nome, String preco) {
        this.imageProduct = image;
        this.nome = nome;
        this.preco = preco;
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


}
