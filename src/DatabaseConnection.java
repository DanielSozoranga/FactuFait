import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Facturacion DAKAEL;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "user1";
    private static final String PASSWORD = "uide12";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
