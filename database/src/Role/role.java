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
}
