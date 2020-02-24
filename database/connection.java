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
}
