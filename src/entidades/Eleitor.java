package entidades;

public class Eleitor {
    private String nome;
    private Long cpf;
    private Integer candidatoNumero;

    public Eleitor(String nome, Long cpf, Integer candidato) {
        this.nome = nome;
        this.cpf = cpf;
        this.candidatoNumero = candidato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Integer getCandidatoNumero() {
        return candidatoNumero;
    }

    public void setCandidatoNumero(Integer candidato) {
        this.candidatoNumero = candidato;
    }
}
