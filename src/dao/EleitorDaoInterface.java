package dao;

import entities.Eleitor;

import java.util.List;

public interface EleitorDaoInterface {
    void insert(Eleitor eleitor);
    void updateById(Integer eleitorId);
    void deleteById(Integer eleitorID);
    Eleitor findById(Integer eleitorId);
    List<Eleitor> findAll();
}
