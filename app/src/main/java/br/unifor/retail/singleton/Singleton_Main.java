package br.unifor.retail.singleton;

/**
 * Created by mafra on 31/10/16.
 */

public class Singleton_Main {
    private int imagemEsquerda, imagemDireita;
    private String precoEsquerda, produtoEsquerda;
    private String precoDireita, produtoDireita;

    public Singleton_Main(int imagemEsquerda, int imagemDireita, String precoEsquerda, String produtoEsquerda, String precoDireita, String produtoDireita) {
        this.imagemEsquerda = imagemEsquerda;
        this.imagemDireita = imagemDireita;
        this.precoEsquerda = precoEsquerda;
        this.produtoEsquerda = produtoEsquerda;
        this.precoDireita = precoDireita;
        this.produtoDireita = produtoDireita;
    }

    public int getImagemEsquerda() {
        return imagemEsquerda;
    }

    public void setImagemEsquerda(int imagemEsquerda) {
        this.imagemEsquerda = imagemEsquerda;
    }

    public int getImagemDireita() {
        return imagemDireita;
    }

    public void setImagemDireita(int imagemDireita) {
        this.imagemDireita = imagemDireita;
    }

    public String getPrecoEsquerda() {
        return precoEsquerda;
    }

    public void setPrecoEsquerda(String precoEsquerda) {
        this.precoEsquerda = precoEsquerda;
    }

    public String getProdutoEsquerda() {
        return produtoEsquerda;
    }

    public void setProdutoEsquerda(String produtoEsquerda) {
        this.produtoEsquerda = produtoEsquerda;
    }

    public String getPrecoDireita() {
        return precoDireita;
    }

    public void setPrecoDireita(String precoDireita) {
        this.precoDireita = precoDireita;
    }

    public String getProdutoDireita() {
        return produtoDireita;
    }

    public void setProdutoDireita(String produtoDireita) {
        this.produtoDireita = produtoDireita;
    }
}
