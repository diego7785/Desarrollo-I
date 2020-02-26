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
    public String SavePlanOnTable(int id, int cost, int minutes, int dataPlan, int messages, String description) {
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

    // Load the plan information
    public String LoadPlanInformation(int id) {
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

            String message = "id: " + result.getString("id") + "cost: " + result.getString("cost") + "minutes: " +
                    result.getString("minutes") + "dataPlan: " + result.getString("dataPlan") + "messages: " +
                    result.getString("messages") + "description: " + result.getString("description");

            return  message;
        } catch (SQLException e) {
            return "connection failed";
        }
    }
}
