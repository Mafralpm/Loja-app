package br.unifor.retail.singleton;

/**
 * Created by mafra on 19/10/16.
 */

public class Singleton_Car {
    private int imageProduct;
    private String nome, decricao, cor;

    public Singleton_Car() {
    }

    public Singleton_Car(int image, String nome, String decricao, String cor) {
        this.imageProduct = image;
        this.nome = nome;
        this.decricao = decricao;
        this.cor = cor;
    }

    public int getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(int imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
