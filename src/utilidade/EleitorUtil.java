package utilidade;

import dao.CandidatoDao;
import entidades.Candidato;

import java.util.List;

public class EleitorUtil {
    private final CandidatoDao candidatoDao;

    public EleitorUtil(CandidatoDao candidatoDao){
        this.candidatoDao = candidatoDao;
    }

    public boolean votoNulo(Integer numero) {
        List<Candidato> candidatos = candidatoDao.findAll();
        return candidatos.stream().noneMatch(c -> c.getNumero() == numero);
    }

    public boolean cpfValido(Long cpf){
        return cpf.toString().length() == 11;
    }

    public boolean nomeValido(String nome){
        return nome.length() > 0;
    }
}
