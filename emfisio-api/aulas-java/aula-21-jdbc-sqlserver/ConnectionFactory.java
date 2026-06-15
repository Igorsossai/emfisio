import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL =
            "jdbc:sqlserver://IGORLS\\SQLEXPRESS;"
            + "databaseName=emfisio_db;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException erro) {
            System.out.println("Erro ao conectar no banco de dados.");
            System.out.println("Mensagem: " + erro.getMessage());
            return null;
        }
    }
}