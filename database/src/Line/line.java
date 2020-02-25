package Line;

import java.sql.*;

public class line {
    PreparedStatement prepare;
    ResultSet result;
    Connection con;

    private String tableName = "Line";

    public line(String host, String userName, String userPassword) throws SQLException {
        con = DriverManager.getConnection(host, userName, userPassword);
    }

    // Store a line on Line table
    public String SaveLineOnTable(int id, int customer, int planID) {
        if (id == 0) {
            return "Missing id";
        }

        if (customer == 0) {
            return "Missing customer";
        }

        if (planID == 0) {
            return "Missing planID";
        }

        try {
            prepare = con.prepareStatement("INSERT INTO " + tableName + " (id, customer, planID) VALUES(?,?,?)");
            prepare.setInt(1, id);
            prepare.setInt(2, customer);
            prepare.setInt(3, planID);

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
