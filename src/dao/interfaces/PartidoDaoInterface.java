package dao.interfaces;

import entidades.Partido;

import java.util.List;

public interface PartidoDaoInterface {
    void insert(Partido p);
    Partido findById(Integer partidoID);
    void deleteById(Integer partidoID);
    void updateById(Integer partidoID, Partido p);
    List<Partido> findAll();
}
