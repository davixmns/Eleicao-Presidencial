package dao;

import dao.interfaces.PartidoDaoInterface;
import db.DBException;
import entidades.Partido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartidoDao implements PartidoDaoInterface {
    private final Connection connection;

    public PartidoDao(Connection c){
        this.connection = c;
    }

    @Override
    public void insert(Partido p) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO partido(sigla, nome) VALUES (?, ?)"
            );
            stmt.setString(1, p.getSigla());
            stmt.setString(2, p.getNome());
            stmt.executeUpdate();

        }catch (SQLException e){
            System.err.println("erro ao inserir partido no banco");
            e.printStackTrace();
        }
    }

    @Override
    public Partido findById(Integer partidoID) {
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    "SELECT (sigla, nome) FROM partido\n" +
                            "WHERE id_partido = ?"
            );
            ps.setInt(1, partidoID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String sigla = rs.getString("sigla");
                String nome = rs.getString("nome");
                return new Partido(nome, sigla);

            } else {
                throw new DBException("Partido não existe");
            }
        }catch (SQLException e){
            System.err.println("Erro ao coletar partido");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Integer partidoID) {
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    "DELETE FROM partido WHERE id_partido = ?"
            );
            ps.setInt(1, partidoID);
            ps.executeUpdate();

        }catch (SQLException e){
            System.err.println("Erro ao deletar partido");
            e.printStackTrace();
        }
    }

    @Override
    public void updateById(Integer partidoID, Partido p) {
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    """
                            UPDATE partido
                            SET sigla = ?, nome = ?\s
                            WHERE id_partido = ?"""
            );
            ps.setString(1, p.getSigla());
            ps.setString(2, p.getNome());
            ps.setInt(3, partidoID);
            ps.executeUpdate();

        }catch (SQLException e){
            System.err.println("Erro ao editar partido");
            e.printStackTrace();
        }
    }

    @Override
    public List<Partido> findAll() {
        try{
            List<Partido> partidos = new ArrayList<>();
            PreparedStatement ps = this.connection.prepareStatement(
                    "SELECT * FROM partido"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String nome = rs.getString("nome");
                String sigla = rs.getString("sigla");
                partidos.add(new Partido(nome, sigla));
            }
            return partidos;

        }catch (SQLException e){
            System.err.println("Erro ao coletar partidos");
            e.printStackTrace();
            return null;
        }
    }
}



















