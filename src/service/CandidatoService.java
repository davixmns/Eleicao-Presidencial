package service;

import dao.CandidatoDao;
import entidades.Candidato;
import utilidade.CandidatoUtil;
import utilidade.InterfaceUsuarioUtil;
import utilidade.PartidoUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CandidatoService {
    private final PartidoUtil partidoUtil;
    private final CandidatoDao candidatoDao;
    private final CandidatoUtil candidatoUtil;

    public CandidatoService(CandidatoDao candidatoDao, CandidatoUtil candidatoUtil, PartidoUtil partidoUtil) {
        this.candidatoDao = candidatoDao;
        this.candidatoUtil = candidatoUtil;
        this.partidoUtil = partidoUtil;
    }

    public void inserirCandidato(Candidato candidato) {
        if (!candidatoUtil.nomeDoCandidatoExiste(candidato.getNome())) {
            if (!candidatoUtil.numeroDoCandidatoExiste(candidato.getNumero())) {
                System.out.println("sim");
                if (partidoUtil.partidoExiste(candidato.getPartido())) {
                    this.candidatoDao.insert(candidato);
                } else {
                    throw new ServiceException("Partido não existe no banco de dados");
                }
            } else {
                throw new ServiceException("Número do candidato já foi cadastrado");
            }
        } else {
            throw new ServiceException("Nome do candidato já foi cadastrado");
        }
    }

    public void deletarCandidato(Integer candidatoID) {
        this.candidatoDao.deleteById(candidatoID);
    }

    public List<Candidato> getCandidatos() {
        return this.candidatoDao.findAll();
    }

    public String getlistaDeCandidatos() {
        List<Candidato> candidatos = candidatoDao.findAll();
        StringBuilder sb = new StringBuilder();
        for (Candidato c : candidatos) {
            sb.append(c.getNumero()).append(" - ").append(c.getNome()).append(" - ").append(c.getPartido()).append('\n');
        }
        return sb.toString();
    }

    public String contarVotos() {
        List<Candidato> candidatos = candidatoDao.findAll();
        candidatos.sort(Comparator.comparing(Candidato::getVotos));
        Collections.reverse(candidatos);
        StringBuilder sb = new StringBuilder();
        for (Candidato c : candidatos) {
            sb.append(c.getVotos()).append(" votos - ").append(c.getNome()).append(" ").append(c.getPartido()).append('\n');
        }
        return sb.toString();
    }

    public void alterarNomeDeCandidato(Integer numero, String novoNome) {
        if (candidatoUtil.numeroDoCandidatoExiste(numero)) {
            if (CandidatoUtil.nomeValido(novoNome)) {
                this.candidatoDao.alterarNomeDeCandidato(numero, novoNome);
            } else {
                throw new ServiceException("Nome novo invalido");
            }
        } else {
            throw new ServiceException("Numero do candidato nao existe");
        }
    }

    public void alterarNumeroDeCandidato(Integer numeroNovo, Integer numeroAntigo) {
        if (!candidatoUtil.numeroDoCandidatoExiste(numeroNovo)) {
            System.out.println("numero nao existe");
            this.candidatoDao.alterarNumeroDeCandidato(numeroAntigo, numeroNovo);
        } else {
            System.out.println("numero existe");
            throw new ServiceException("Numero de candidato ja foi cadastrado");
        }
    }

    public void alterarPartidoDeCandidato(Integer numero, String partido) {
        if (candidatoUtil.partidoExiste(partido)) {
            this.candidatoDao.alterarPartidoDeCandidato(numero, partido);
        } else {
            throw new ServiceException("Partido não cadastrado no banco de dados");
        }
    }

    public void alterarFotoDeCandidato(Integer numero, String fotoURL) {
        if(candidatoUtil.numeroDoCandidatoExiste(numero)) {
            byte[] fotoBytes = InterfaceUsuarioUtil.converterImagemParaBytes(fotoURL);
            this.candidatoDao.alterarFotoDeCandidato(numero, fotoBytes);
        } else{
            throw new ServiceException("Numero do candidato não existe");
        }
    }
}
