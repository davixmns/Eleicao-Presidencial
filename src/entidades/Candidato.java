package entidades;

import javax.swing.*;

public class Candidato {
    private String nome;
    private String partido;
    private Integer numero;
    private ImageIcon foto;
    private String fotoURL;
    private Long votos = 0L;

    public Candidato(String nome, String partido, int numero) {
        this.nome = nome;
        this.partido = partido;
        this.numero = numero;
    }

    public Candidato(String nome, String partido, int numero, ImageIcon foto, Long votos) {
        this.nome = nome;
        this.partido = partido;
        this.numero = numero;
        this.foto = foto;
        this.votos = votos;
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
                ", fotoURL='" + foto + '\'' +
                '}';
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public ImageIcon getFoto() {
        return foto;
    }

    public void setFoto(ImageIcon foto) {
        this.foto = foto;
    }

    public void setFotoURL(String fotoURL) {
        this.fotoURL = fotoURL;
    }

    public Long getVotos() {
        return votos;
    }

    public void setVotos(Long votos) {
        this.votos = votos;
    }
}
