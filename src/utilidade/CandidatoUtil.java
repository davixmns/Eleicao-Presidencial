package utilidade;

import dao.CandidatoDao;
import dao.PartidoDao;
import entidades.Candidato;
import entidades.Partido;

import java.util.List;

public class CandidatoUtil {
    private final CandidatoDao candidatoDao;
    private final PartidoDao partidoDao;

    public CandidatoUtil(CandidatoDao candidatoDao, PartidoDao partidoDao){
        this.candidatoDao = candidatoDao;
        this.partidoDao = partidoDao;
    }

    public boolean candidatoExiste(String nome) {
        List<Candidato> candidatos = candidatoDao.findAll();
        return candidatos.stream().anyMatch(c -> c.getNome().equals(nome));
    }

    public static boolean nomeValido(String nome) {
        return nome.length() > 0;
    }

    public boolean partidoExiste(String sigla){
        List<Partido> partidos = partidoDao.findAll();

        return partidos.stream().anyMatch(p -> p.getSigla().equals(sigla));
    }

    public boolean candidatoExiste(Integer numero){
        List<Candidato> candidatos = candidatoDao.findAll();

        return candidatos.stream().anyMatch(c -> c.getNumero() == numero);
    }

}
