import dao.CandidatoDao;
import dao.EleitorDao;
import dao.PartidoDao;
import db.DB;
import entidades.Eleitor;
import frontend.InterfaceUsuario;
import service.CandidatoService;
import service.EleitorService;
import service.ServiceException;
import utilidade.CandidatoUtil;
import utilidade.EleitorUtil;
import utilidade.PartidoUtil;

import java.sql.Connection;

public class App {
    public static void run(){
        Connection conn = DB.getConnection();

        CandidatoDao candidatoDao = new CandidatoDao(conn);
        EleitorDao eleitorDao = new EleitorDao(conn);
        PartidoDao partidoDao = new PartidoDao(conn);

        CandidatoUtil candidatoUtil = new CandidatoUtil(candidatoDao, partidoDao);
        PartidoUtil partidoUtil = new PartidoUtil(partidoDao);
        EleitorUtil eleitorUtil = new EleitorUtil(candidatoDao);

        CandidatoService candidatoService = new CandidatoService(candidatoDao, candidatoUtil, partidoUtil);
        EleitorService eleitorService = new EleitorService(eleitorDao, eleitorUtil, candidatoDao);

        InterfaceUsuario interfaceUsuario = new InterfaceUsuario(candidatoService);

        boolean chave = true;
        while (chave) {
            Integer opcaoEscolhida = interfaceUsuario.exibirMenu();
            if (opcaoEscolhida == 0) {
                try {
                    String nome = interfaceUsuario.exibirEntradaParaNome();
                    Long cpf = interfaceUsuario.exibirEntradaParaCPF();
                    Integer voto = 0;

                    boolean chave2 = true;
                    while (chave2) {
                        try {
                            voto = interfaceUsuario.exibirEntradaParaVoto();
                            Integer confirmarVoto = interfaceUsuario.exibirCandidato(voto);
                            if (confirmarVoto == 1) {
                                throw new Exception("");
                            } else {
                                chave2 = false;
                            }
                        } catch (Exception e) {
                            System.err.println("");
                        }
                    }

                    Eleitor eleitor = new Eleitor(nome, cpf, voto);
                    try {
                        eleitorService.inserirEleitor(eleitor);
                        DB.closeConnection();
                        interfaceUsuario.exibirFIM();
                        chave = false;
                    } catch (ServiceException e) {
                        interfaceUsuario.exibirMensagemDeErro(e.getMessage());
                    }

                } catch (NullPointerException | NumberFormatException e) {
                    System.err.println(e.getMessage());
                    interfaceUsuario.exibirMensagemDeErro("Campos invalidos");
                }


            } else if (opcaoEscolhida == 1) {
                interfaceUsuario.exibirResultado();
            } else {
                DB.closeConnection();
                chave = false;
            }
        }
    }

    public static void main(String[] args) {
        App.run();
    }
}
