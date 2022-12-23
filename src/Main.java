import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DB.getConnection();

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO candidato(nome, )"
        );
    }
}
