package br.unifor.retail.singleton;

/**
 * Created by mafra on 09/11/16.
 */

public class SingletonProduct {

    private double nota;
    private String comentario;

    public SingletonProduct( double nota, String comentario) {

        this.nota = nota;
        this.comentario = comentario;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
