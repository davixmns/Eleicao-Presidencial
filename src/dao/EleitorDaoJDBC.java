package dao;

import db.DB;
import db.DBException;
import entities.Eleitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EleitorDaoJDBC implements EleitorDAO {
    private static Connection connection = DB.getConnection();

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
