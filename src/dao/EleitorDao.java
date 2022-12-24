package dao;

import dao.interfaces.EleitorDaoInterface;
import db.DB;
import db.DBException;
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
                            "SELECT id_candidato, ?, ? FROM candidato WHERE numero = ?"
            );
            ps.setString(1, eleitor.getNome());
            ps.setLong(2, eleitor.getCpf());
            ps.setInt(3, eleitor.getCandidatoNumero());
            ps.executeUpdate();
        } catch (SQLException e) {
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
        Eleitor eleitor = new Eleitor("luquinhas", 63417951305L, 12);
        eleitorDao.insert(eleitor);
    }
}
