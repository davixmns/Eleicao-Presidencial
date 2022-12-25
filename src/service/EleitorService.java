package service;

import dao.CandidatoDao;
import dao.EleitorDao;
import entidades.Candidato;
import entidades.Eleitor;

import java.util.List;

public class EleitorService {
    private final CandidatoDao candidatoDao;
    private final EleitorDao eleitorDao;

    public EleitorService(EleitorDao eleitorDao, CandidatoDao candidatoDao) {
        this.eleitorDao = eleitorDao;
        this.candidatoDao = candidatoDao;
    }

    public void inserirEleitor(Eleitor eleitor) {
        if (eleitor.getCpf().toString().length() == 11) {
            if (!votoNulo(eleitor.getCandidatoNumero())) {
                this.eleitorDao.insert(eleitor);
            } else {
                System.err.println("Voto nulo não cadastrado");
            }
        } else {
            System.err.println("CPF do Eleitor não tem 11 caracteres");
        }
    }

    public void alterarEleitor(Integer eleitorID, Eleitor eleitor){
        if (eleitor.getCpf().toString().length() == 11) {
            if (!votoNulo(eleitor.getCandidatoNumero())) {
                this.eleitorDao.updateById(eleitorID, eleitor);
            } else {
                System.err.println("Voto nulo não cadastrado");
            }
        } else {
            System.err.println("CPF do Eleitor não tem 11 caracteres");
        }
    }

    public void deletarEleitor(Integer eleitorID){
        this.eleitorDao.deleteById(eleitorID);
    }

    public Eleitor getEleitor(Integer eleitorID){
        return this.eleitorDao.findById(eleitorID);
    }

    public List<Eleitor> getEleitores(){
        return this.eleitorDao.findAll();
    }

    public boolean votoNulo(Integer numero) {
        List<Candidato> candidatos = candidatoDao.findAll();
        return candidatos.stream().noneMatch(c -> c.getNumero() == numero);
    }
}
