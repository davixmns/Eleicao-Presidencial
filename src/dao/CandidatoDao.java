package dao;

import dao.interfaces.CandidatoDaoInterface;
import db.DB;
import entidades.Candidato;
import utilidade.InterfaceUsuarioUtil;

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
    public void insert(Candidato candidato) { //OK
        try {
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
            stmt.setBytes(4, InterfaceUsuarioUtil.converterImagemParaBytes(candidato.getFotoURL()));
            stmt.executeUpdate();
            System.out.println(stmt.getGeneratedKeys() + " rows added");

        } catch (SQLException e) {
            System.err.println("erro ao inserir candidato");
            e.printStackTrace();
        }
    }

    @Override
    public Candidato findById(Integer candidatoID) { //OK
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    """
                            SELECT c.nome, p.sigla, c.foto, numero
                            FROM candidato c
                            JOIN partido p ON c.id_partido = p.id_partido
                            WHERE c.id_partido = ?"""
            );
            stmt.setInt(1, candidatoID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String partido = rs.getString("sigla");
                byte[] fotoBytes = rs.getBytes("foto");
                ImageIcon foto = InterfaceUsuarioUtil.converterBlobParaImagem(fotoBytes);
                int numero = rs.getInt("numero");

                return new Candidato(nome, partido, numero, foto);

            } else {
                throw new SQLException("Candidato não encontrado no banco de dados");
            }
        } catch (SQLException e) {
            System.err.println("erro ao procurar candidato");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateById(Integer candidatoID, Candidato candidato) { //OK
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    """
                            UPDATE candidato SET id_partido = ?, nome = ?, numero = ?, foto = ?
                            WHERE id_candidato = ?
                            """
            );

            stmt.setInt(1, getPartidoId(candidato.getPartido()));
            stmt.setString(2, candidato.getNome());
            stmt.setInt(3, candidato.getNumero());
            stmt.setBytes(4, InterfaceUsuarioUtil.converterImagemParaBytes(candidato.getFotoURL()));
            stmt.setInt(5, candidatoID);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Integer getPartidoId(String sigla) { //OK
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT id_partido FROM partido WHERE sigla = ?");
            stmt.setString(1, sigla);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("id_partido");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Integer candidatoID) { //OK
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM candidato WHERE id_candidato = ?");
            stmt.setInt(1, candidatoID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Candidato> findAll() { //OK
        try {
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
                ImageIcon foto = InterfaceUsuarioUtil.converterBlobParaImagem(fotoBytes);
                candidatos.add(new Candidato(nome, partido, numero, foto));
            }
            return candidatos;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        Connection c = DB.getConnection();

        //inserir
//        new CandidatoDao(c).insert(new Candidato("PEDROs", "PT", 14, "fotos/justica_eleitoral.jpg"));

        //alterar
//        Candidato candidato = new Candidato("CARLINHOS", "PT", 14, "fotos/justica_eleitoral.jpg");
//        new CandidatoDao(c).updateById(11, candidato);

        //findall
//        List<Candidato> candidatos = new CandidatoDao(c).findAll();
//        candidatos.forEach(System.out::println);

        //deletar
//        new CandidatoDao(c).deleteById(11);
    }
}
