package Plan;

import java.sql.*;

public class plan {
    PreparedStatement prepare;
    ResultSet result;
    Connection con;

    private String tableName = "Plan";

    public plan(String host, String userName, String userPassword) throws SQLException {
        con = DriverManager.getConnection(host, userName, userPassword);
    }

    // Store a plan on Plan table
    public String SaveRoleOnTable(int id, int cost, int minutes, int dataPlan, int messages, String description) {
        if (id == 0) {
            return "Missing id";
        }

        if (cost == 0) {
            return "Missing cost";
        }

        if (minutes == 0) {
            return "Missing minutes";
        }

        if (dataPlan == 0) {
            return "Missing dataPlan";
        }

        if (messages == 0) {
            return "Missing messages";
        }

        if (description == "") {
            return "Missing description";
        }

        try {
            prepare = con.prepareStatement("INSERT INTO " + tableName + " (id, cost, minutes, dataPlan, messages, description) VALUES(?,?,?,?,?,?)");
            prepare.setInt(1, id);
            prepare.setInt(2, cost);
            prepare.setInt(3, minutes);
            prepare.setInt(4, dataPlan);
            prepare.setInt(5, messages);
            prepare.setString(6, description);

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
