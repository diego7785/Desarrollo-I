package Users;

import com.sun.tools.javac.Main;

import java.sql.*;

public class users {
    PreparedStatement prepare;
    ResultSet result;
    Connection con;

    private String tableName = "Users";

    public users(String host, String userName, String userPassword) throws SQLException {
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

    // Load the user information
    public String LoadUserInformation(int id) {
        if (id == 0) {
            return "missing id";
        }

        try {
            prepare = con.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            prepare.setInt(1, id);

            result = prepare.executeQuery();
            if (!result.next()) {
                return "load information failed";
            }

            String message = "id: " + result.getString("id") + "name: " + result.getString("name") + "roleID: " + result.getString("roleId") + "status: " + result.getString("status");
            return  message;
        } catch (SQLException e) {
            return "connection failed";
        }
    }
}
