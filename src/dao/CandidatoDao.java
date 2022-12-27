package dao;

import dao.interfaces.CandidatoDaoInterface;
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
            rs.next();
            int idPartido = rs.getInt("id_partido");
            stmt = connection.prepareStatement(
                    "INSERT INTO candidato(id_partido, nome, numero, foto, votos_totais)" +
                            "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, idPartido);
            stmt.setString(2, candidato.getNome());
            stmt.setInt(3, candidato.getNumero());
            stmt.setBytes(4, InterfaceUsuarioUtil.converterImagemParaBytes(candidato.getFotoURL()));
            stmt.setInt(5, 0);
            stmt.executeUpdate();
            System.out.println(stmt.getGeneratedKeys() + " rows added");
            stmt.close();

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
                            SELECT c.nome, p.sigla, c.foto, numero, votos_totais
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
                Long votos = rs.getLong("votos_totais");
                stmt.close();
                return new Candidato(nome, partido, numero, foto, votos);

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
            stmt.close();

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
            Integer idpartido = rs.getInt("id_partido");
            stmt.close();
            return idpartido;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Integer candidatoNumero) {
        try {
            deletarEleitoresDeCandidato(candidatoNumero);
            PreparedStatement ps = this.connection.prepareStatement(
                    "DELETE FROM candidato WHERE numero = ?"
            );
            ps.setInt(1, candidatoNumero);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletarEleitoresDeCandidato(Integer candidatoNumero) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "DELETE FROM eleitor WHERE id_candidato = (SELECT id_candidato FROM candidato WHERE numero = ?)"
            );
            ps.setInt(1, candidatoNumero);
            ps.executeUpdate();
            ps.close();

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
                            SELECT c.id_partido, c.nome, p.sigla, c.numero, c.foto, c.votos_totais
                            FROM candidato c
                            JOIN partido p ON c.id_partido = p.id_partido"""
            );
            while (rs.next()) {
                String nome = rs.getString("nome");
                String partido = rs.getString("sigla");
                int numero = rs.getInt("numero");
                byte[] fotoBytes = rs.getBytes("foto");
                ImageIcon foto = InterfaceUsuarioUtil.converterBlobParaImagem(fotoBytes);
                Long votos = rs.getLong("votos_totais");
                candidatos.add(new Candidato(nome, partido, numero, foto, votos));
            }
            stmt.close();
            return candidatos;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void incrementarVoto(Integer numeroCandidato) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE candidato SET votos_totais = votos_totais + 1 WHERE numero = ?"
            );
            ps.setInt(1, numeroCandidato);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar voto no candidato");
            e.printStackTrace();
        }
    }

    public void alterarNomeDeCandidato(Integer numero, String nome) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE candidato SET nome = ? WHERE numero = ?"
            );
            ps.setString(1, nome);
            ps.setInt(2, numero);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.err.println("erro ao alterar nome do candidato");
            e.printStackTrace();
        }
    }

    public void alterarNumeroDeCandidato(Integer numeroAntigo, Integer numeroNovo) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE candidato SET numero = ? WHERE numero = ?"
            );
            ps.setInt(1, numeroNovo);
            ps.setInt(2, numeroAntigo);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Erro ao alterar numero de candidato");
            e.printStackTrace();
        }
    }

    public void alterarPartidoDeCandidato(Integer numero, String partido) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE candidato SET id_partido = (SELECT id_partido FROM partido WHERE sigla = ?) WHERE numero = ?"
            );
            ps.setString(1, partido);
            ps.setInt(2, numero);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Erro ao alterar partido de candidato");
            e.printStackTrace();
        }
    }

    public void alterarFotoDeCandidato(Integer numero, byte[] fotoBytes){
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE candidato SET foto = ? WHERE numero = ?"
            );
            ps.setBytes(1, fotoBytes);
            ps.setInt(2, numero);
            ps.executeUpdate();
            ps.close();

        }catch (SQLException e){
            System.err.println("Erro ao alterar foto");
        }
    }


}
