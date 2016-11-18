package br.unifor.retail.singleton;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.ViewById;

/**
 * Created by mafra on 18/11/16.
 */

public class SingletonHistory {

    private String imagem;
    private String nome;
    private double preco;
    private String data;

    public SingletonHistory() {
    }

    public SingletonHistory(String imagem, String nome, double preco, String data) {
        this.imagem = imagem;
        this.nome = nome;
        this.preco = preco;
        this.data = data;
    }

    public SingletonHistory(String nome, double preco, String data) {
        this.nome = nome;
        this.preco = preco;
        this.data = data;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
