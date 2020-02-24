import java.sql.*;

public class Users {
    public static String tableName = "Users";

    // Store an user on Users table
    public String SaveUsersOnTable(int id, String name, int role, int status) {
        if (id == 0) {
            return "Missing id"
        }

        if (name == "") {
            return "Missing name"
        }

        if (role == 0) {
            return "Missing role"
        }

        if (status == 0) {
            return "Missing status"
        }
    }
}