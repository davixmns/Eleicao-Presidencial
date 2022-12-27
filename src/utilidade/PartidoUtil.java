package utilidade;

import dao.PartidoDao;
import entidades.Partido;

import java.util.List;

public class PartidoUtil {
    private final PartidoDao partidoDao;

    public PartidoUtil(PartidoDao partidoDao){
        this.partidoDao = partidoDao;
    }

    public static boolean isNomeValido(String nome){
        return nome != null && nome.length() > 0;
    }

    public static boolean isSiglaValida(String sigla){
        return sigla != null && sigla.length() > 0;
    }

    public static boolean nomeSiglaValidos(Partido p){
        return isNomeValido(p.getNome()) && isSiglaValida(p.getSigla());
    }

    public boolean partidoExiste(String sigla) {
        List<Partido> partidos = partidoDao.findAll();
        return partidos.stream().anyMatch(p -> p.getSigla().equals(sigla));
    }

    public String getListaDePartidos(){
        List<Partido> partidos = partidoDao.findAll();
        StringBuilder sb =new StringBuilder();

        for (Partido p : partidos) {
            sb.append(p.getSigla()).append(" - ").append(p.getNome()).append("\n");
        }
        return sb.toString();
    }

}
