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
}
