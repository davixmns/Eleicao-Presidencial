package dao;

import db.DB;
import entities.Candidato;
import usuario.ConversorDeImagem;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidatoDao implements CandidatoDaoInterface {
    private final Connection connection;

    public CandidatoDao(Connection c) {
        this.connection = c;
    }

    @Override
    public void insert(Candidato candidato) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(
                "SELECT id_partido FROM partido WHERE sigla = ?"
        );
        stmt.setString(1, candidato.getPartido());
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            throw new SQLException("Partido do candidato não encontrado no banco de dados");
        }
        int idPartido = rs.getInt("id_partido");

        stmt = connection.prepareStatement(
                "INSERT INTO candidato(id_partido, nome, numero, foto)" +
                        "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
        );

        stmt.setInt(1, idPartido);
        stmt.setString(2, candidato.getNome());
        stmt.setInt(3, candidato.getNumero());
        stmt.setBytes(4, ConversorDeImagem.converterImagemParaBytes(candidato.getFotoURL()));
        stmt.executeUpdate();

        System.out.println(stmt.getGeneratedKeys() + " rows added");
    }

    @Override
    public Candidato findById(Integer candidatoID) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                """
                        SELECT c.nome, p.sigla, c.foto, numero
                        FROM candidato c
                        JOIN partido p ON c.id_partido = p.id_partido
                        WHERE c.id_partido = ?"""
        );
        stmt.setInt(1, candidatoID);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            throw new SQLException("Candidato não encontrado no banco de dados");
        }
        String nome = rs.getString("nome");
        String partido = rs.getString("sigla");
        byte[] fotoBytes = rs.getBytes("foto");
        ImageIcon foto = ConversorDeImagem.converterBlobParaImagem(fotoBytes);
        int numero = rs.getInt("numero");

        Candidato c = new Candidato(nome, partido, numero, foto);
        System.out.println(c);
        return c;
    }


    @Override
    public void updateById(Integer candidatoID, Candidato candidato) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "UPDATE candidato SET id_partido = ?, nome = ?, numero = ?, foto = ? WHERE id_partido = ?"
        );
        stmt.setInt(1, getPartidoId(candidato.getPartido()));
        stmt.setString(2, candidato.getNome());
        stmt.setInt(3, candidato.getNumero());
        stmt.setBytes(4, ConversorDeImagem.converterImagemParaBytes(candidato.getFotoURL()));
        stmt.setInt(5, candidatoID);
        stmt.executeUpdate();
    }

    private int getPartidoId(String sigla) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT id_partido FROM partido WHERE sigla = ?");
        stmt.setString(1, sigla);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("id_partido");
    }

    @Override
    public void deleteById(Integer candidatoID) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM candidato WHERE id_partido = ?");
        stmt.setInt(1, candidatoID);
        stmt.executeUpdate();
    }

    @Override
    public List<Candidato> findAll() throws SQLException {
        List<Candidato> candidatos = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
                """
                        SELECT c.id_partido, c.nome, p.sigla, c.numero, c.foto
                        FROM candidato c
                        JOIN partido p ON c.id_partido = p.id_partido"""
        );
        while (rs.next()) {
            String nome = rs.getString("nome");
            String partido = rs.getString("sigla");
            int numero = rs.getInt("numero");
            byte[] fotoBytes = rs.getBytes("foto");
            ImageIcon foto = ConversorDeImagem.converterBlobParaImagem(fotoBytes);
            candidatos.add(new Candidato(nome, partido, numero, foto));
        }
        return candidatos;
    }



    public static void main(String[] args) throws SQLException {
        Connection c = DB.getConnection();

//        new CandidatoDaoJDBC().insert(new Candidato("PEDROs", "PT", 14, "fotos/justica_eleitoral.jpg"));


        List<Candidato> candidatos = new CandidatoDao(c).findAll();

        candidatos.forEach(System.out::println);
    }
}
