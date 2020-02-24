package Users;

import java.sql.*;

public class users {
    PreparedStatement prepare;
    ResultSet result;
    Connection con;

    private String tableName;
    String host;
    String userName;
    private String userPassword;

    public users() throws SQLException {
        tableName = "Users";
        host = "jdbc:mysql://localhost:3306/Desarrollo";
        userName = "root";
        userPassword = "Johan219";

        con = DriverManager.getConnection(host, userName, userPassword);
    }

    // Store an user on Users table
    public String SaveUsersOnTable(int id, String name, int role, int status) {
        if (id == 0) {
            return "Missing id";
        }

        if (name == "") {
            return "Missing name";
        }

        if (role == 0) {
            return "Missing role";
        }

        if (status == 0) {
            return "Missing status";
        }

        try {
            prepare = con.prepareStatement("INSERT INTO " + tableName + " (id, name, roleID, status) VALUES(?,?,?,?)");
            prepare.setInt(1, id);
            prepare.setString(2, name);
            prepare.setInt(3, role);
            prepare.setInt(4, status);

            int response = prepare.executeUpdate();
            if (response == 0) {
                return "store information failed";
            }

            con.close();
        } catch (SQLException e) {
            return "connection failed";
        }

        return "success";
    }

}
