package dao;

import dao.interfaces.EleitorDaoInterface;
import db.DB;
import db.DBException;
import entidades.Eleitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EleitorDao implements EleitorDaoInterface {
    private final Connection connection;

    public EleitorDao(Connection c){
        this.connection = c;
    }

    @Override
    public void insert(Eleitor eleitor) {
        try{
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO eleitor(id_candidato, nome, cpf)" +
                            "SELECT "
            );
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void updateById(Integer eleitorId) {

    }

    @Override
    public void deleteById(Integer eleitorID) {

    }

    @Override
    public Eleitor findById(Integer eleitorId) {
        return null;
    }

    @Override
    public List<Eleitor> findAll() {
        return null;
    }
}
