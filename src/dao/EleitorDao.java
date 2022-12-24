package dao;

import dao.interfaces.EleitorDaoInterface;
import db.DB;
import db.DBException;
import entidades.Candidato;
import entidades.Eleitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EleitorDao implements EleitorDaoInterface {
    private final Connection connection;

    public EleitorDao(Connection c) {
        this.connection = c;
    }

    @Override
    public void insert(Eleitor eleitor) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "INSERT INTO eleitor(id_candidato, nome, cpf)\n" +
                            "VALUES(?, ?, ?)"
            );
            ps.setInt(1, getIdDoCandidato(eleitor.getCandidatoNumero()));
            ps.setString(2, eleitor.getNome());
            ps.setLong(3, eleitor.getCpf());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

    }

    private Integer getIdDoCandidato(Integer numero) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "SELECT id_candidato FROM candidato WHERE numero = ?"
            );
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id_candidato");

        } catch (SQLException e) {
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
        try{
            List<Eleitor> eleitores = new ArrayList<>();
            PreparedStatement ps = this.connection.prepareStatement(
                    """
                            SELECT c.numero, e.nome, e.cpf
                            FROM eleitor e
                            JOIN candidato c ON e.id_candidato = c.id_candidato"""
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String nome = rs.getString("nome");
                Long cpf = rs.getLong("cpf");
                Integer numeroDoCandidato = rs.getInt("numero");
                eleitores.add(new Eleitor(nome, cpf, numeroDoCandidato));
            }
            return eleitores;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        Connection c = DB.getConnection();
        EleitorDao eleitorDao = new EleitorDao(c);
//        Eleitor eleitor = new Eleitor("davi ximenes", 63417951305L, 12);
//        eleitorDao.insert(eleitor);
    }
}
