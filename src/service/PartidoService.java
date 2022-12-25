package service;

import dao.PartidoDao;
import entidades.Partido;
import utilidade.PartidoUtil;

import java.util.List;

public class PartidoService {
    private final PartidoDao partidoDao;
    private final PartidoUtil partidoUtil;

    public PartidoService(PartidoDao dao, PartidoUtil util) {
        this.partidoDao = dao;
        this.partidoUtil = util;
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

    public List<Partido> getPartidos(){
        return partidoDao.findAll();
    }
}
