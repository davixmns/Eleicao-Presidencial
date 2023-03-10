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
            ps.close();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void updateById(Integer eleitorId, Eleitor eleitor) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE eleitor SET id_candidato = (SELECT id_candidato FROM candidato WHERE numero = ?),\n" +
                            "nome = ?, cpf = ? WHERE id_eleitor = ?"
            );
            ps.setInt(1, eleitor.getCandidatoNumero());
            ps.setString(2, eleitor.getNome());
            ps.setLong(3, eleitor.getCpf());
            ps.setInt(4, eleitorId);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteById(Integer eleitorID) {
        try{
            removerVoto(eleitorID);
            PreparedStatement st = this.connection.prepareStatement(
                    "DELETE FROM eleitor WHERE id_eleitor = ?"
            );
            st.setInt(1, eleitorID);
            st.executeUpdate();
            st.close();

        } catch (SQLException e){
            System.err.println("Erro ao deletar eleitor");
            e.printStackTrace();
        }
    }

    @Override
    public Eleitor findById(Integer eleitorId) {
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    """
                            SELECT c.numero, e.nome, e.cpf
                            FROM eleitor e
                            INNER JOIN candidato c
                            ON e.id_candidato = c.id_candidato
                            WHERE id_eleitor = ?"""
            );
            ps.setInt(1, eleitorId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Integer numeroCandidato = rs.getInt("numero");
                String nome = rs.getString("nome");
                Long cpf = rs.getLong("cpf");
                rs.close();
                ps.close();
                return new Eleitor(nome, cpf, numeroCandidato);

            } else {
                throw new DBException("Eleitor n??o encontrado no banco de dados");
            }
        }catch (SQLException e){
            System.err.println("erro ao capturar eleitor");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Eleitor> findAll() {
        try {
            List<Eleitor> eleitores = new ArrayList<>();
            PreparedStatement ps = this.connection.prepareStatement(
                    """
                            SELECT c.numero, e.nome, e.cpf
                            FROM eleitor e
                            JOIN candidato c ON e.id_candidato = c.id_candidato"""
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("nome");
                Long cpf = rs.getLong("cpf");
                Integer numeroDoCandidato = rs.getInt("numero");
                eleitores.add(new Eleitor(nome, cpf, numeroDoCandidato));
            }
            ps.close();
            rs.close();
            return eleitores;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registrarVoto(Integer numeroDoCandidato) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE candidato SET votos_totais = votos_totais + 1 WHERE numero = ?"
            );
            ps.setInt(1, numeroDoCandidato);
            int rowsAffected = ps.executeUpdate();
            ps.close();
            if (rowsAffected == 0) {
                throw new DBException("Candidato n??o encontrado");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar voto");
            e.printStackTrace();
        }
    }

    public void removerVoto(Integer eleitorID){
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE candidato SET votos_totais = votos_totais - 1\n" +
                            "WHERE id_candidato = (SELECT id_candidato FROM eleitor WHERE id_eleitor = ?)"
            );
            ps.setInt(1, eleitorID);
            ps.executeUpdate();
            ps.close();

        }catch (SQLException e){
            System.err.println("Erro ao remover voto de eleitor");
            e.printStackTrace();
        }
    }

    public void alterarNome(Integer eleitorID, String novoNome){
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE eleitor SET nome = ?\n" +
                            "WHERE id_eleitor = ?"
            );
            ps.setString(1, novoNome);
            ps.setInt(2, eleitorID);
            ps.executeUpdate();
            ps.close();

        }catch (SQLException e){
            System.err.println("Erro ao alterar nome de eleitor");
            e.printStackTrace();
        }
    }

    public void alterarCPF(Integer eleitorID, Long novoCPF){
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    "UPDATE eleitor SET cpf = ?\n" +
                            "WHERE id_eleitor = ?"
            );
            ps.setLong(1, novoCPF);
            ps.setInt(2, eleitorID);
            ps.executeUpdate();
            ps.close();

        }catch (SQLException e){
            System.err.println("Erro ao alterarCPF de eleitor");
        }
    }

    public boolean contemEleitor(Long cpf) {
        Connection conn = DB.getConnection();

        String sql = "SELECT COUNT(*) FROM eleitor WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Erro ao verificar se existe cpf");
        }
        return false;
    }

    public void deletarEleitores(){
        try{
            PreparedStatement ps = this.connection.prepareStatement(
                    "DELETE FROM eleitor"
            );
            ps.executeUpdate();
            ps.close();

        }catch (SQLException e){
            System.err.println("erro ao deletar eleitores");
            e.printStackTrace();
        }
    }

}
