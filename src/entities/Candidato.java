package entities;

import javax.swing.*;

public class Candidato {
    private String nome;
    private String partido;
    private int numero;
    private ImageIcon foto;

    public Candidato(String nome, String partido, int numero, ImageIcon foto) {
        this.nome = nome;
        this.partido = partido;
        this.numero = numero;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ImageIcon getFoto() {
        return foto;
    }

    public void setFoto(ImageIcon foto) {
        this.foto = foto;
    }


}
