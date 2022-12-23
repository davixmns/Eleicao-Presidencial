package entities;

public class Eleitor {
    private String nome;
    private Integer cpf;
    private Candidato candidato;

    public Eleitor(String nome, Integer cpf, Candidato candidato) {
        this.nome = nome;
        this.cpf = cpf;
        this.candidato = candidato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
}
