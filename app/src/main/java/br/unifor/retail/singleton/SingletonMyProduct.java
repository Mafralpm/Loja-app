package br.unifor.retail.singleton;

/**
 * Created by mafra on 19/10/16.
 */

public class SingletonMyProduct {
    private String imagem;
    private String nome;
    private String preco;
    private String quantidade;
    private Long idProduto;

    public SingletonMyProduct() {
    }

    public SingletonMyProduct(String imagem, String nome, String preco, String quantidade, Long id) {
        this.imagem = imagem;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.idProduto = id;
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

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long id) {
        this.idProduto = id;
    }
}
