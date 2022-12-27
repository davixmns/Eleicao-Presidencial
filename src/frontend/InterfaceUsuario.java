package frontend;

import entidades.Candidato;
import service.CandidatoService;
import utilidade.AudioPlayer;
import utilidade.InterfaceUsuarioUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class InterfaceUsuario {
    private static final String brasaoURL = "fotos/justica_eleitoral.jpg";
    private static final ImageIcon brasao = InterfaceUsuarioUtil.formatarFoto(new ImageIcon(brasaoURL), 120, 120);
    private final CandidatoService candidatoService;

    public InterfaceUsuario(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
    }

    public Integer exibirMenu() {
        int n = JOptionPane.showOptionDialog(
                null,
                "SISTEMA DE URNA ELETRÔNICA \nselecione um comando:",
                "ELEIÇÃO PRESIDENCIAL",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                brasao,
                new String[]{"VOTAR", "RESULTADO", "MAIS", "SAIR"},
                null
        );
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return n;
    }

    public Integer exibirCandidato(Integer numeroDoCandidato) {
        List<Candidato> candidatos = candidatoService.getCandidatos();
        Candidato c = candidatos.stream()
                .filter(candidato -> candidato.getNumero() == numeroDoCandidato)
                .findFirst()
                .orElse(null);
        if (c == null) {
            return exibirVotoNulo();
        }

        ImageIcon fotoFormatada = InterfaceUsuarioUtil.formatarFoto(c.getFoto(), 160, 200);
        int n = JOptionPane.showOptionDialog(
                null,
                "CONFIRA SEU VOTO:\n" +
                        "Nome: " + c.getNome() + "\n" +
                        "Partido: " + c.getPartido() + "\n" +
                        "Numero: " + c.getNumero() + "\n",
                "Informações do Candidato",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                fotoFormatada,
                new String[]{"Confirmar", "Cancelar"},
                null
        );
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return n;
    }

    public String exibirEntradaParaNome() {
        String nome = JOptionPane.showInputDialog(null, "DIGITE SEU NOME");
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return nome;
    }

    public Long exibirEntradaParaCPF() {
        Long cpf = Long.valueOf(JOptionPane.showInputDialog(null, "DIGITE SEU CPF"));
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return cpf;
    }

    public Integer exibirEntradaParaVoto(String msg) {
        String listaDeCandidatos = "LISTA DE CANDIDATOS:\n" + candidatoService.getlistaDeCandidatos() + "\n" + msg;
        Integer voto = Integer.valueOf(JOptionPane.showInputDialog(null, listaDeCandidatos));
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return voto;
    }

    public void exibirMensagemDeErro(String msg) {
        AudioPlayer.tocarSom(AudioPlayer.erro);
        JOptionPane.showMessageDialog(null, msg);
        AudioPlayer.tocarSom(AudioPlayer.confirma);
    }

    public void exibirFIM() {
        AudioPlayer.tocarSom(AudioPlayer.fim);
        JOptionPane.showMessageDialog(null, "FIM");
    }

    public void exibirResultado() {
        JOptionPane.showMessageDialog(null, candidatoService.contarVotos());
        AudioPlayer.tocarSom(AudioPlayer.confirma);
    }

    public Integer exibirVotoNulo() {
        Integer n = JOptionPane.showOptionDialog(
                null,
                "VOTO NULO",
                "Aviso",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Confirmar", "Cancelar"},
                null
        );
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return n;
    }

    public Integer exibirAlterarDados() {
        Integer n = JOptionPane.showOptionDialog(
                null,
                "OPERAÇÕES AVANÇADAS\n" +
                        "Selecione quem deseja Modificar",
                "Aviso",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"CANDIDATO", "ELEITOR", "PARTIDO", "Voltar"},
                null
        );
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return n;
    }

    public Integer exibirOpcoesDeAlteracaoDeCandidato() {
        Integer n = JOptionPane.showOptionDialog(
                null,
                "Selecione o que deseja alterar" +
                        "",
                "Aviso",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"ALTERAR NOME", "ALTERAR PARTIDO", "ALTERAR NUMERO", "ALTERAR FOTO", "DELETAR", "ADICIONAR", "Voltar"},
                null
        );
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return n;
    }

    public String exibirEntradaDeTexto(String texto) {
        return JOptionPane.showInputDialog(null, texto);
    }

    public Integer exibirEntradaDeNumero(String texto) {
        Integer n = Integer.parseInt(JOptionPane.showInputDialog(null, texto));
        System.out.println(n);
        AudioPlayer.tocarSom(AudioPlayer.confirma);
        return n;
    }

    public String exibirSelecionarArquivo() throws MalformedURLException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos JPG", "jpg"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

}
