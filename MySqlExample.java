import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

        // Create a new client
        String insertQuery = "INSERT INTO clients (name, email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, "John Doe");
            statement.setString(2, "john.doe@example.com");
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Client created successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error creating client: " + e.getMessage());
        }

        // Read clients
        String selectQuery = "SELECT * FROM clients";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println("Client ID: " + id + ", Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Error reading clients: " + e.getMessage());
        }

        // Update a client
        String updateQuery = "UPDATE clients SET email = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, "new-email@example.com");
            statement.setInt(2, 1); // Assuming the client with ID 1 needs to be updated
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client updated successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error updating client: " + e.getMessage());
        }

        // Delete a client
        String deleteQuery = "DELETE FROM clients WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, 1); // Assuming the client with ID 1 needs to be deleted
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client deleted successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting client: " + e.getMessage());
        }
    }
}