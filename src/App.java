import dao.CandidatoDao;
import dao.EleitorDao;
import dao.PartidoDao;
import db.DB;
import entidades.Candidato;
import entidades.Eleitor;
import entidades.Partido;
import frontend.InterfaceUsuario;
import service.CandidatoService;
import service.EleitorService;
import service.PartidoService;
import service.ServiceException;
import utilidade.CandidatoUtil;
import utilidade.EleitorUtil;
import utilidade.PartidoUtil;

import javax.swing.*;
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
        PartidoService partidoService = new PartidoService(partidoDao, partidoUtil, candidatoDao);

        InterfaceUsuario interfaceUsuario = new InterfaceUsuario(candidatoService);

        boolean chave = true;
        boolean chave3;
        while (chave) {
            Integer opcaoEscolhida = interfaceUsuario.exibirMenu();

            if (opcaoEscolhida == 0) {
                try {
                    String nome = interfaceUsuario.exibirEntradaParaNome();
                    Long cpf = interfaceUsuario.exibirEntradaParaLong("Digite seu CPF");
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

                    if (quemAlterar == 0) { //candidato
                        try {
                            Integer oqueAlterar = interfaceUsuario.exibirOpcoesDeModificacaoDeCandidato();
                            if (oqueAlterar == 0) { //alterar nome
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a modificar");
                                String novoNome = interfaceUsuario.exibirEntradaParaString("Digite o novo nome do candidato");
                                candidatoService.alterarNomeDeCandidato(numeroDoCandidato, novoNome);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 1) { //alterar partido
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a modificar");
                                String novoPartido = interfaceUsuario.exibirEntradaParaString("Digite o novo partido do candidato");
                                candidatoService.alterarPartidoDeCandidato(numeroDoCandidato, novoPartido);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 2) { //alterar numero
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a modificar");
                                Integer novoNumero = interfaceUsuario.exibirEntradaDeNumero("Digite o novo numero do candidato");
                                candidatoService.alterarNumeroDeCandidato(novoNumero, numeroDoCandidato);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 3) { //alterar foto
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a moodificar");
                                String fotoURL = interfaceUsuario.exibirSelecionarArquivo();
                                candidatoService.alterarFotoDeCandidato(numeroDoCandidato, fotoURL);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 4) { //deletar candidato
                                Integer numeroDoCandidato = interfaceUsuario.exibirEntradaParaVoto("Digite o numero do candidato a deletar");
                                candidatoService.deletarCandidato(numeroDoCandidato);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 5) { //adicionar candidato
                                String nome = interfaceUsuario.exibirEntradaParaString("Digite o nome do novo candidato");
                                String partido = interfaceUsuario.exibirEntradaParaString(partidoUtil.getListaDePartidos() + "\nDigite o partido do novo candidato");
                                Integer numero = interfaceUsuario.exibirEntradaDeNumero(candidatoService.getlistaDeCandidatos() + "Digite o numero do novo candidato");
                                String fotoURL = interfaceUsuario.exibirSelecionarArquivo();
                                System.out.println(fotoURL);
                                Candidato c = new Candidato(nome, partido, numero, fotoURL);
                                candidatoService.inserirCandidato(c);
                                interfaceUsuario.exibirFIM();
                            }

                        } catch (ServiceException | NumberFormatException | MalformedURLException e) {
                            interfaceUsuario.exibirMensagemDeErro(e.getMessage());
                        }

                    } else if (quemAlterar == 1) { //eleitor
                        Integer oqueAlterar = interfaceUsuario.exibirOpcoesDeModificacaoDeEleitor();

                        try {
                            if (oqueAlterar == 0) { //alterar nome
                                Integer id = interfaceUsuario.exibirEntradaDeNumero("Digite o ID do eleitor");
                                String novoNome = interfaceUsuario.exibirEntradaParaString("Digite o novo nome do eleitor");
                                eleitorService.alterarNomeDeEleitor(id, novoNome);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 1) { //alterar cpf
                                Integer id = interfaceUsuario.exibirEntradaDeNumero("Digite o ID do eleitor");
                                Long novoCpf = interfaceUsuario.exibirEntradaParaLong("Digite o novo CPF do eleitor");
                                eleitorService.alterarCpfDeEleitor(id, novoCpf);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 2) { //deletar
                                Integer id = interfaceUsuario.exibirEntradaDeNumero("Digite o ID do eleitor");
                                eleitorService.deletarEleitor(id);
                                interfaceUsuario.exibirFIM();

                            }

                        } catch (ServiceException | NumberFormatException e) {
                            interfaceUsuario.exibirMensagemDeErro(e.getMessage());
                        }

                    } else if (quemAlterar == 2) { //partido
                        Integer oqueAlterar = interfaceUsuario.exibirOpcoesDeModificacaoDePartido();

                        try{
                            if(oqueAlterar == 0){ //alterar sigla
                                String sigla = interfaceUsuario.exibirEntradaParaString(partidoUtil.getListaDePartidos() + "\nDigite a sigla do partido desejado");
                                String novaSigla = interfaceUsuario.exibirEntradaParaString("Digite a nova sigla do partido");
                                partidoService.alterarSiglaDePartido(sigla, novaSigla);
                                interfaceUsuario.exibirFIM();

                            } else if (oqueAlterar == 1) { //alterar nome
                                String sigla = interfaceUsuario.exibirEntradaParaString(partidoUtil.getListaDePartidos() + "\nDigite a sigla do partido desejado");
                                String novoNome = interfaceUsuario.exibirEntradaParaString("Digite o novo nome do partido");
                                partidoService.alterarNomeDePartido(sigla, novoNome);
                                interfaceUsuario.exibirFIM();


                            } else if(oqueAlterar == 2) { //adicionar
                                String sigla = interfaceUsuario.exibirEntradaParaString("Digite a sigla do novo partido");
                                String nome = interfaceUsuario.exibirEntradaParaString("Digite o nome do novo partido");
                                Partido p = new Partido(nome, sigla);
                                partidoService.inserirPartido(p);
                                interfaceUsuario.exibirFIM();

                            } else if(oqueAlterar == 3) { //deletar
                                String sigla = interfaceUsuario.exibirEntradaParaString(partidoUtil.getListaDePartidos() + "\nDigite a sigla do partido a deletar");
                                partidoService.deletarPartido(sigla);
                                interfaceUsuario.exibirFIM();

                            }

                        }catch (ServiceException | NumberFormatException e){
                            interfaceUsuario.exibirMensagemDeErro(e.getMessage());
                        }

                    } else if(quemAlterar == 3){
                        int deletarConfirm = interfaceUsuario.exibirPedidoDeConfirmacao("Tem certeza que deseja zerar a urna?\n " +
                                "Esta ação deletará todos os eleitores e zerará o numero de votos de todos os candidatos");

                        if(deletarConfirm == JOptionPane.YES_OPTION){
                            eleitorService.deletarTodosOsEleitores();
                            interfaceUsuario.exibirFIM();
                        }
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
