package service;

import dao.CandidatoDao;
import entidades.Candidato;
import utilidade.CandidatoUtil;

import java.util.List;

public class CandidatoService {
    private final CandidatoDao candidatoDao;
    private final CandidatoUtil candidatoUtil;

    public CandidatoService(CandidatoDao candidatoDao, CandidatoUtil candidatoUtil) {
        this.candidatoDao = candidatoDao;
        this.candidatoUtil = candidatoUtil;
    }

    public void inserirCandidato(Candidato candidato) {
        if(!candidatoUtil.candidatoExiste(candidato.getNome())){
            this.candidatoDao.insert(candidato);
        } else {
            System.err.println("Candidato já cadastrado");
        }
    }

    public void alterarCandidato(Integer candidatoID, Candidato candidato){
        if(CandidatoUtil.nomeValido(candidato.getNome())){
            if(candidatoUtil.partidoExiste(candidato.getPartido())){
                candidatoDao.insert(candidato);
            } else{
                System.err.println("Partido não existe");
            }
        } else {
            System.err.println("Nome do candidato invalido");
        }
    }

    public void deletarCandidato(Integer candidatoNumero){
        if(candidatoUtil.candidatoExiste(candidatoNumero)){
            this.candidatoDao.deleteById(candidatoNumero);
        } else{
            System.err.println("Candidato não existe");
        }
    }

    public List<Candidato> getCandidatos(){
        return this.candidatoDao.findAll();
    }
}
