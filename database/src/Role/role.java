package Role;

import java.sql.*;

public class role {
    PreparedStatement prepare;
    ResultSet result;
    Connection con;

    private String tableName = "Role";

    public role(String host, String userName, String userPassword) throws SQLException {
        con = DriverManager.getConnection(host, userName, userPassword);
    }

    // Store a role on Role table
    public String SaveRoleOnTable(int id, String name, String description) {
        if (id == 0) {
            return "Missing id";
        }

        if (name == "") {
            return "Missing name";
        }

        if (description == "") {
            return "Missing description";
        }

        try {
            prepare = con.prepareStatement("INSERT INTO " + tableName + " (id, name, description) VALUES(?,?,?)");
            prepare.setInt(1, id);
            prepare.setString(2, name);
            prepare.setString(3, description);

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

    // Load the role information
    public String LoadRoleInformation(int id) {
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

            String message = "id: " + result.getString("id") + "name: " + result.getString("name") + "description: " + result.getString("description");
            return  message;
        } catch (SQLException e) {
            return "connection failed";
        }
    }
}
