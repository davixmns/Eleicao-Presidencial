package service;

import dao.CandidatoDao;
import dao.EleitorDao;
import entidades.Eleitor;
import utilidade.EleitorUtil;

import java.util.List;

public class EleitorService {
    private final CandidatoDao candidatoDao;
    private final EleitorDao eleitorDao;
    private final EleitorUtil eleitorUtil;

    public EleitorService(EleitorDao eleitorDao, EleitorUtil eleitorUtil, CandidatoDao candidatoDao) {
        this.eleitorDao = eleitorDao;
        this.candidatoDao = candidatoDao;
        this.eleitorUtil = eleitorUtil;
    }

    public void inserirEleitor(Eleitor eleitor) {
        if (eleitorUtil.cpfValido(eleitor.getCpf())) {
            if (!eleitorUtil.votoNulo(eleitor.getCandidatoNumero())) {
                if(eleitorUtil.nomeValido(eleitor.getNome())){
                    this.eleitorDao.insert(eleitor);
                    this.candidatoDao.incrementarVoto(eleitor.getCandidatoNumero());
                } else {
                    throw new ServiceException("NOME INVÁLIDO");
                }
            }
        } else {
            throw new ServiceException("CPF INVÁLIDO");
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


}
