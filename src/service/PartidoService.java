package service;

import dao.CandidatoDao;
import dao.PartidoDao;
import entidades.Partido;
import utilidade.PartidoUtil;

import java.util.List;

public class PartidoService {
    private final PartidoDao partidoDao;
    private final PartidoUtil partidoUtil;
    private final CandidatoDao candidatoDao;

    public PartidoService(PartidoDao dao, PartidoUtil util, CandidatoDao candidatoDao) {
        this.partidoDao = dao;
        this.partidoUtil = util;
        this.candidatoDao = candidatoDao;
    }

    public void inserirPartido(Partido p) {
        if (PartidoUtil.nomeSiglaValidos(p)) {
            if (!partidoUtil.partidoExiste(p.getSigla())) {
                partidoDao.insert(p);
            } else {
                System.err.println("Partido ja existe");
            }
        } else {
            System.err.println("Nome ou sigla invalidos");
        }
    }

    public void editarPartido(Integer partidoID, Partido p) {
        if (PartidoUtil.nomeSiglaValidos(p)) {
            partidoDao.updateById(partidoID, p);
        }
    }

    public void deletarPartido(Integer partidoID) {
        partidoDao.deleteById(partidoID);
    }

    public void deletarPartido(String sigla) {
        if (partidoUtil.partidoExiste(sigla)) {
            partidoDao.deletarPorSigla(sigla);
        } else {
            throw new ServiceException("Partido não existe");
        }
    }

    public void alterarNomeDePartido(String sigla, String novoNome) {
        if (PartidoUtil.isNomeValido(novoNome) && PartidoUtil.isSiglaValida(sigla)) {
            if (partidoUtil.partidoExiste(sigla)) {
                partidoDao.alterarNome(sigla, novoNome);
            } else {
                throw new ServiceException("Partido Digitado não existe");
            }
        } else {
            throw new ServiceException("nome ou sigla invalidos");
        }
    }

    public void alterarSiglaDePartido(String siglaAntiga, String novaSigla) {
        if (PartidoUtil.isSiglaValida(siglaAntiga) && PartidoUtil.isSiglaValida(novaSigla)) {
            partidoDao.alterarSigla(siglaAntiga, novaSigla);
        } else {
            throw new ServiceException("Sigla invalida");
        }
    }

    public List<Partido> getPartidos() {
        return partidoDao.findAll();
    }
}
