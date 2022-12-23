package dao;

import entities.Candidato;
import entities.Eleitor;

import java.util.List;

public interface CandidatoDAO {
    void insert(Candidato eleitor);
    void updateById(Integer eleitorId);
    void deleteById(Integer eleitorID);
    Candidato findById(Integer eleitorId);
    List<Candidato> findAll();
}
