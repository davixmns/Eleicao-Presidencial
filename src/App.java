import dao.CandidatoDao;
import dao.EleitorDao;
import dao.PartidoDao;
import db.DB;
import entidades.Candidato;
import entidades.Eleitor;
import frontend.InterfaceUsuario;
import service.CandidatoService;
import service.EleitorService;
import service.ServiceException;
import utilidade.CandidatoUtil;
import utilidade.EleitorUtil;
import utilidade.PartidoUtil;

import java.net.MalformedURLException;
import java.sql.Connection;

public class App {
    public static void run() {
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
        boolean chave2 = true;
        boolean chave3 = true;
        while (chave) {
            Integer opcaoEscolhida = interfaceUsuario.exibirMenu();

            if (opcaoEscolhida == 0) {
                try {
                    String nome = interfaceUsuario.exibirEntradaParaNome();
                    Long cpf = interfaceUsuario.exibirEntradaParaCPF();
                    Integer voto = null;
                    boolean confirmado = false;
                    while (!confirmado) {
                        try {
                            voto = interfaceUsuario.exibirEntradaParaVoto("DIGITE O NUMERO DO SEU CANDIDATO");
                            Integer conf = interfaceUsuario.exibirCandidato(voto);

                            if (conf == 0) {
                                confirmado = true;
                            }
                        } catch (NumberFormatException e) {
                            interfaceUsuario.exibirMensagemDeErro(e.getMessage());
                        }
                    }

                    Eleitor eleitor = new Eleitor(nome, cpf, voto);
                    try {
                        eleitorService.inserirEleitor(eleitor);
                        interfaceUsuario.exibirFIM();
                        chave = false;
                    } catch (ServiceException e) {
                        interfaceUsuario.exibirMensagemDeErro(e.getMessage());
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    System.err.println(e.getMessage());
                    interfaceUsuario.exibirMensagemDeErro(e.getMessage());
                }
            } else if (opcaoEscolhida == 1) {
                interfaceUsuario.exibirResultado();

            } else if (opcaoEscolhida == 2) {
                chave3 = true;
                while (chave3) {
                    Integer quemAlterar = interfaceUsuario.exibirAlterarDados();

                    if (quemAlterar == 0) {
                        try {
                            Integer oqueAlterar = interfaceUsuario.exibirOpcoesDeAlteracaoDeCandidato();
                            if (oqueAlterar == 0) { //alterar nome
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a modificar");
                                String novoNome = interfaceUsuario.exibirEntradaDeTexto("Digite o novo nome do candidato");
                                candidatoService.alterarNomeDeCandidato(numeroDoCandidato, novoNome);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 1) { //alterar partido
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a modificar");
                                String novoPartido = interfaceUsuario.exibirEntradaDeTexto("Digite o novo partido do candidato");
                                candidatoService.alterarPartidoDeCandidato(numeroDoCandidato, novoPartido);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 2) { //alterar numero
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a modificar");
                                Integer novoNumero = interfaceUsuario.exibirEntradaDeNumero("Digite o novo numero do candidato");
                                candidatoService.alterarNumeroDeCandidato(novoNumero, numeroDoCandidato);
                                interfaceUsuario.exibirFIM();

                            }else if(oqueAlterar == 3){ //alterar foto
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a moodificar");
                                String fotoURL = interfaceUsuario.exibirSelecionarArquivo();
                                candidatoService.alterarFotoDeCandidato(numeroDoCandidato, fotoURL);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 4) { //deletar candidato
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a deletar");
                                candidatoService.deletarCandidato(numeroDoCandidato);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 5) { //adicionar candidato
                                String nome = interfaceUsuario.exibirEntradaDeTexto("Digite o nome do novo candidato");
                                String partido = interfaceUsuario.exibirEntradaDeTexto("Digite o partido do novo candidato");
                                Integer numero = interfaceUsuario.exibirEntradaDeNumero("Digite o numero do novo candidato");
                                String fotoURL = interfaceUsuario.exibirSelecionarArquivo();
                                System.out.println(fotoURL);
                                Candidato c = new Candidato(nome, partido, numero, fotoURL);
                                candidatoService.inserirCandidato(c);
                                interfaceUsuario.exibirFIM();
                            }

                        } catch (ServiceException | NumberFormatException e) {
                            interfaceUsuario.exibirMensagemDeErro(e.getMessage());
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }

                    } else if (quemAlterar == 1) {

                    } else if (quemAlterar == 2) {

                    } else {
                        chave3 = false;
                    }
                }
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
