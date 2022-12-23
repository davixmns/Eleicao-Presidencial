package dao;

import entities.Candidato;

import java.sql.SQLException;
import java.util.List;

public interface CandidatoDaoInterface {
    void insert(Candidato candidato) throws SQLException;
    void updateById(Integer candidatoID, Candidato candidato) throws SQLException;
    void deleteById(Integer candidatoID) throws SQLException;
    Candidato findById(Integer candidatoID) throws SQLException;
    List<Candidato> findAll() throws SQLException;
}
