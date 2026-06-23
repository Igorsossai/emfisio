// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
   private static final String URL = "jdbc:sqlserver://IGORLS\\SQLEXPRESS;databaseName=emfisio_db;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

   public ConnectionFactory() {
   }

   public static Connection conectar() {
      try {
         return DriverManager.getConnection("jdbc:sqlserver://IGORLS\\SQLEXPRESS;databaseName=emfisio_db;integratedSecurity=true;encrypt=true;trustServerCertificate=true");
      } catch (SQLException var1) {
         System.out.println("Erro ao conectar no banco de dados.");
         System.out.println("Mensagem: " + var1.getMessage());
         return null;
      }
   }
}
