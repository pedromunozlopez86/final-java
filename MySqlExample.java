import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.cj.jdbc.Driver;


public class MySqlExample {
    public static void main(String[] args) throws ClassNotFoundException {
        String host  = "mysql-1d9cf496-sistema-ventas.g.aivencloud.com";
        String port = "19068";
        String databaseName = "defaultdb";
        String userName = "avnadmin";
        String password = "AVNS_Hp45VnSLc7_dunfbGIJ";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);
            System.out.println("Connection successful to MySQL database");
            // Perform database operations here
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to MySQL database: " + e.getMessage());
        }
    }
}