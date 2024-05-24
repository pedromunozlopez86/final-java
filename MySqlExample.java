
// import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.xdevapi.Client;

public class MySqlExample {
    public static void main(String[] args) throws ClassNotFoundException {
        String host = "mysql-1d9cf496-sistema-ventas.g.aivencloud.com";
        String port = "19068";
        String databaseName = "final";
        String userName = "avnadmin";
        String password = "AVNS_Hp45VnSLc7_dunfbGIJ";

        class Client {
            private int id;
            private String name;
            private String last_name;
            private String rut;
            private String email;
            private String phone;
            private int id_address;

            public Client(String name, String last_name, String rut, String phone, String email, int id_address) {
                this.id = id;
                this.name = name;
                this.last_name = last_name;
                this.rut = rut;
                this.phone = phone;
                this.email = email;
                this.id_address = id_address;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLastName() {
                return last_name;
            }

            public void setLastName(String last_name) {
                this.last_name = last_name;
            }

            public String getRut() {
                return rut;
            }

            public void setRut(String rut) {
                this.rut = rut;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getIdAddress() {
                return id_address;
            }

            public void setIdAddress(int id_address) {
                this.id_address = id_address;
            }
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);
            System.out.println("Connection successful to MySQL database");
            // Perform database operations here

            String[] options = { "Create", "Read", "Update", "Delete" };
            int choice = JOptionPane.showOptionDialog(null, "Select CRUD operation:", "CRUD Operation",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0: // Create
                    // Create a new client
                    String insertQuery = "INSERT INTO clients (name, last_name, rut, phone, email, id_address) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                        String name = JOptionPane.showInputDialog("Enter client name:");
                        String last_name = JOptionPane.showInputDialog("Enter client last name:");
                        String rut = JOptionPane.showInputDialog("Enter client rut:");
                        String email = JOptionPane.showInputDialog("Enter client email:");
                        String phone = JOptionPane.showInputDialog("Enter client phone:");
                        // TODO Get address from database
                        String address = JOptionPane.showInputDialog("Enter client address:");
                        int id_address = Integer.parseInt(address);

                        Client client = new Client(name, last_name, rut, email, phone, id_address);

                        statement.setString(1, client.name);
                        statement.setString(2, client.last_name);
                        statement.setString(3, client.rut);
                        statement.setString(4, client.email);
                        statement.setString(5, client.phone);
                        statement.setInt(6, client.id_address);
                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(null, "Client created successfully");
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error creating client: " + e.getMessage());
                    }
                    break;
                case 1: // Read
                    // Read clients
                    String selectQuery = "SELECT * FROM clients";
                    try (PreparedStatement statement = connection.prepareStatement(selectQuery);
                            ResultSet resultSet = statement.executeQuery(selectQuery)) {

                        // while (resultSet.next()) {
                        // int id = resultSet.getInt("id");
                        // String name = resultSet.getString("name");
                        // String last_name = resultSet.getString("last_name");
                        // String rut = resultSet.getString("rut");
                        // String phone = resultSet.getString("phone");
                        // String email = resultSet.getString("email");
                        // // TODO mostrar dirección completa y no solo el ID!
                        // int id_address = resultSet.getInt("id_address");
                        // // System.out.println("Client ID: " + id + ", Name: " + name + ", Email: " +
                        // // email);
                        Client[] clients = new Client[100]; // Assuming you want to create an array of 100 clients
                        int index = 0; // Index to keep track of the current position in the array

                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            String last_name = resultSet.getString("last_name");
                            String rut = resultSet.getString("rut");
                            String phone = resultSet.getString("phone");
                            String email = resultSet.getString("email");
                            int id_address = resultSet.getInt("id_address");

                            Client client = new Client(name, last_name, rut, email, phone, id_address);
                            clients[index] = client; // Add the client to the array
                            index++; // Increment the index

                            // JOptionPane.showMessageDialog(null,
                            //         "Client ID: " + id + ", Name: " + name + ",Last Name:" + last_name + ", Email: "
                            //                 + email + ", Address: " + id_address);
                        }
                        // Stringify clients
                        StringBuilder sb = new StringBuilder();
                        for (Client client : clients) {
                            sb.append(", Name: ").append(client.name)
                                    .append(", Last Name: ").append(client.getLastName()).append(", Email: ").append(client.getEmail())
                                    .append(", Address: ").append(client.getIdAddress()).append("\n");
                        }
                        String clientString = sb.toString();
                        JOptionPane.showMessageDialog(null, clientString);
                        JOptionPane.showMessageDialog(null, clients);
                    } catch (SQLException e) {
                        System.out.println("Error retrieving address: " + e.getMessage());
                    }
                    break;
                case 2: // Update
                    // TODO: Implement update operation
                    break;
                case 3: // Delete
                    // TODO: Implement delete operation
                    break;
                default:
                    // Invalid choice
                    break;
            }

            // // Update a client
            // String updateQuery = "UPDATE clients SET email = ? WHERE id = ?";
            // try (PreparedStatement statement = connection.prepareStatement(updateQuery))
            // {
            // statement.setString(1, "new-email@example.com");
            // statement.setInt(2, 1); // Assuming the client with ID 1 needs to be updated
            // int rowsUpdated = statement.executeUpdate();
            // if (rowsUpdated > 0) {
            // System.out.println("Client updated successfully");
            // }
            // } catch (SQLException e) {
            // System.out.println("Error updating client: " + e.getMessage());
            // }

            // // Delete a client
            // String deleteQuery = "DELETE FROM clients WHERE id = ?";
            // try (PreparedStatement statement = connection.prepareStatement(deleteQuery))
            // {
            // statement.setInt(1, 1); // Assuming the client with ID 1 needs to be deleted
            // int rowsDeleted = statement.executeUpdate();
            // if (rowsDeleted > 0) {
            // System.out.println("Client deleted successfully");
            // }
            // } catch (SQLException e) {
            // System.out.println("Error deleting client: " + e.getMessage());
            // }
            // ==================== Cerrar conexión ====================

            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to MySQL database: " + e.getMessage());
        }

    }
}