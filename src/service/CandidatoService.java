package service;

import dao.CandidatoDao;
import entidades.Candidato;
import utilidade.CandidatoUtil;
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
        if(!candidatoUtil.nomeDoCandidatoExiste(candidato.getNome())){
            if(!candidatoUtil.numeroDoCandidatoExiste(candidato.getNumero())){
                System.out.println("sim");
                if(partidoUtil.partidoExiste(candidato.getPartido())){
                    this.candidatoDao.insert(candidato);
                } else {
                    throw new ServiceException("Partido não existe no banco de dados");
                }
            }else{
                throw new ServiceException("Número do candidato já foi cadastrado");
            }
        } else {
            throw new ServiceException("Nome do candidato já foi cadastrado");
        }
    }

    public void deletarCandidato(Integer candidatoID){
        this.candidatoDao.deleteById(candidatoID);
    }

    public List<Candidato> getCandidatos(){
        return this.candidatoDao.findAll();
    }

    public String getlistaDeCandidatos(){
        List<Candidato> candidatos = candidatoDao.findAll();
        StringBuilder sb = new StringBuilder();
        for (Candidato c: candidatos) {
            sb.append(c.getNumero()).append(" - ").append(c.getNome()).append(" - ").append(c.getPartido()).append('\n');
        }
        return sb.toString();
    }

    public String contarVotos(){
        List<Candidato> candidatos = candidatoDao.findAll();
        candidatos.sort(Comparator.comparing(Candidato::getVotos));
        Collections.reverse(candidatos);
        StringBuilder sb = new StringBuilder();
        for (Candidato c: candidatos) {
            sb.append(c.getVotos()).append(" votos - ").append(c.getNome()).append(" ").append(c.getPartido()).append('\n');
        }
        return sb.toString();
    }
}
