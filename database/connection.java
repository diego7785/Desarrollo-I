import java.sql.*;

public class DBConnect {
    public static void main(String[] args) {
        try {
            String host = "jdbc:mysql://localhost:3306/Desarrollo";
            String userName = "root";
            String userPassword = "Johan219";
            Connection con = DriverManager.getConnection(host, userName, userPassword);
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    // Store an user on Users table (i think we should make a java class for each table. those class should contain the functions
    // to store, load, and update the information)
    public String SaveUsersOnTable(int id, String name, int role, int status) {
        if id == null {
            return "Missing id";
        }

        if name == "" {
            return "Missing name";
        }

        if role == null {
            return "Missing role";
        }

        if status == null {
            return "Missing status";
        }
    }
}
