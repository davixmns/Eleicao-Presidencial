package dao;

import dao.interfaces.EleitorDaoInterface;
import db.DB;
import db.DBException;
import entidades.Eleitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            PreparedStatement ps = this.connection.prepareStatement(
                    "INSERT INTO eleitor(id_candidato, nome, cpf)\n" +
                            "VALUES(?, ?, ?)"
            );
            ps.setInt(1, getIdDoCandidato(eleitor.getCandidatoNumero()));
            ps.setString(2, eleitor.getNome());
            ps.setLong(3, eleitor.getCpf());
            ps.executeUpdate();

        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }

    private Integer getIdDoCandidato(Integer numero){
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    "SELECT id_candidato FROM candidato WHERE numero = ?"
            );
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id_candidato");

        }catch (SQLException e){
            e.printStackTrace();
            return null;
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

    public static void main(String[] args) {
        Connection c = DB.getConnection();
        EleitorDao eleitorDao = new EleitorDao(c);
        Eleitor eleitor = new Eleitor("davi ximenes", 63417951305L, 12);
        eleitorDao.insert(eleitor);
    }
}
