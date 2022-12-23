package usuario;

import db.DB;
import entities.Candidato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class InterfaceUsuario {





//    public static void exibirCandidato(Candidato candidato) {
//        JOptionPane.showOptionDialog(
//                null,
//                "ola",
//                "ola",
//                JOptionPane.DEFAULT_OPTION,
//                JOptionPane.INFORMATION_MESSAGE,
//                candidato.getFotoURL(),
//                new String[]{"ok", "avancar"},
//                null
//        );
//    }

    public static void coletarCandidato() throws SQLException, IOException {
        Connection connection = DB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM candidato");

        resultSet.next();
        System.out.println(resultSet.getInt("id_partido"));
        System.out.println(resultSet.getString("nome"));
        System.out.println(resultSet.getInt("numero"));
        System.out.println(resultSet.getBlob("foto").toString());

//        exibirCandidato(resultSet);
    }

    public static void gravarCandidato(String urlImagem) throws Exception {
        System.out.println("urlImagem = " + urlImagem);
        File file = new File(urlImagem);

        if (file.exists()) {
            Connection connection = DB.getConnection();
            BufferedImage img = ImageIO.read(file);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", b);
            byte[] imgArray = b.toByteArray();

            PreparedStatement stm = connection.prepareStatement(
                    "INSERT INTO candidato(id_partido, nome, numero, foto)" +
                            "VALUES (?, ?, ?, ?)"
            );
            stm.setInt(1, 7);
            stm.setString(2, "Simone Tebet");
            stm.setInt(3, 15);
            stm.setBytes(4, imgArray);
            stm.executeUpdate();
            stm.close();
        }
    }

    public static void main(String[] args) throws Exception {
//        coletarCandidato();
        gravarCandidato("fotos/tebet.jpg");
    }
}
