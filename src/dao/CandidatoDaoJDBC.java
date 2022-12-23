package dao;

import db.DB;
import db.DBException;
import entities.Candidato;
import usuario.ConversorDeImagem;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class CandidatoDaoJDBC implements CandidatoDAO {
    Connection connection = DB.getConnection();

    @Override
    public void insert(Candidato candidato) {
        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(
                    String.format("SELECT id_partido FROM partido WHERE sigla = '%s'", candidato.getPartido())
            );
            rs.next();

            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO candidato(id_partido, nome, numero, foto)" +
                            "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            int idPartido = rs.getInt("id_partido");
            byte[] fotoDoCandidato = ConversorDeImagem.converterImagemParaBytes(candidato.getFotoURL());
            String nome = candidato.getNome();
            int numero = candidato.getNumero();

            ps.setInt(1, idPartido);
            ps.setString(2, nome);
            ps.setInt(3, numero);
            ps.setBytes(4, fotoDoCandidato);

            ps.executeUpdate();

            System.out.println(ps.getGeneratedKeys() + " rows added");

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public Candidato findById(Integer candidatoID) {
        try{
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT c.nome, p.sigla, c.foto, numero\n" +
                            "FROM candidato c\n" +
                            "JOIN partido p ON c.id_partido = p.id_partido\n" +
                            "WHERE c.id_partido = " + candidatoID
            );
            rs.next();

            String nome = rs.getString("nome");
            String partido = rs.getString("sigla");
            byte[] fotoBytes = rs.getBytes("foto");
            ImageIcon foto = ConversorDeImagem.converterBlobParaImagem(fotoBytes);
            int numero = rs.getInt("numero");

             return new Candidato(nome, partido, numero, foto);

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
    public List<Candidato> findAll() {
        return null;
    }


    public static void main(String[] args) {
//        new CandidatoDaoJDBC().insert(new Candidato("lucas", "PT", 13, "fotos/justica_eleitoral.jpg"));

        new CandidatoDaoJDBC().findById(3);
    }
}
