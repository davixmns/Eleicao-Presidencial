package entities;

import javax.swing.*;

public class Candidato {
    private String nome;
    private String partido;
    private int numero;
    private ImageIcon foto;
    private String fotoURL;

    public Candidato(String nome, String partido, int numero) {
        this.nome = nome;
        this.partido = partido;
        this.numero = numero;
    }

    public Candidato(String nome, String partido, int numero, ImageIcon foto) {
        this.nome = nome;
        this.partido = partido;
        this.numero = numero;
        this.foto = foto;
    }

    public Candidato(String nome, String partido, int numero, String fotoURL) {
        this.nome = nome;
        this.partido = partido;
        this.numero = numero;
        this.fotoURL = fotoURL;
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

    public String getFotoURL() {
        return fotoURL;
    }

    public void setFoto(String fotoURL) {
        this.fotoURL = fotoURL;
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "nome='" + nome + '\'' +
                ", partido='" + partido + '\'' +
                ", numero=" + numero +
                ", fotoURL='" + fotoURL + '\'' +
                '}';
    }
}
