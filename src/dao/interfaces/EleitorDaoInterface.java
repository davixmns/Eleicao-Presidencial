package dao.interfaces;

import entidades.Eleitor;

import java.util.List;

public interface EleitorDaoInterface {
    void insert(Eleitor eleitor);
    void updateById(Integer eleitorId, Eleitor eleitor);
    void deleteById(Integer eleitorID);
    Eleitor findById(Integer eleitorId);
    List<Eleitor> findAll();
}
