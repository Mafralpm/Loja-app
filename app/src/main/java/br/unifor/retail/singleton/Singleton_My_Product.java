package br.unifor.retail.singleton;

import android.media.Image;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mafra on 19/10/16.
 */

public class Singleton_My_Product {
    private int image;
    private String loja, produto, data;

    public Singleton_My_Product() {}

    public Singleton_My_Product(int image, String loja, String produto, String data) {
        this.image = image;
        this.loja = loja;
        this.produto = produto;
        this.data = data;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
