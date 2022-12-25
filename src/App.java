import dao.CandidatoDao;
import dao.EleitorDao;
import dao.PartidoDao;
import db.DB;
import service.CandidatoService;
import service.EleitorService;
import service.PartidoService;
import utilidade.CandidatoUtil;
import utilidade.EleitorUtil;
import utilidade.PartidoUtil;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Connection conn = DB.getConnection();

        CandidatoDao candidatoDao = new CandidatoDao(conn);
        EleitorDao eleitorDao = new EleitorDao(conn);
        PartidoDao partidoDao = new PartidoDao(conn);

        CandidatoUtil candidatoUtil = new CandidatoUtil(candidatoDao, partidoDao);
        PartidoUtil partidoUtil = new PartidoUtil(partidoDao);
        EleitorUtil eleitorUtil = new EleitorUtil(candidatoDao);

        CandidatoService candidatoService = new CandidatoService(candidatoDao, candidatoUtil);
        PartidoService partidoService = new PartidoService(partidoDao, partidoUtil);
        EleitorService eleitorService = new EleitorService(eleitorDao, candidatoDao);


    }
}
