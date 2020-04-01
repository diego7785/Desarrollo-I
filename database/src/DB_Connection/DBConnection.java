package DB_Connection;

import java.sql.*;
import java.util.Vector;

public class DBConnection {

    //DATABASE INFO
    private String db_type;
    private String host;
    private String port;
    private String user;
    private String password;
    private String db_name;

    public DBConnection(String db_type, String host, String port, String user, String password, String db_name ) {

        /*
        this.db_type = db_type;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.db_name = db_name;
         */

        this.db_type = "jdbc:postgresql:";
        this.host = "//localhost:";
        this.port = "5432/";
        this.user = "postgres";
        this.password = "pg123";
        this.db_name = "RAJA_DB";

    }

    /*  RETURN A 2 OBJECTS ARRAY, THE FIRST POSITION CONTAINS
        THE TYPE OF THE RESULT (ERROR OR QUERYRESULT) IN STRING
        AND IN THE SECOND ONE IS THE RESULT.    */
    public Object[] read_DB(String query) {

        Object[] result = new Object[2];
        java.sql.Connection con = null;
        boolean connected;
        try{
            con = DriverManager.getConnection(db_type + host + port + db_name,
                    user + "",
                    password + "");
            connected = true;
        }
        catch(SQLException e){
            connected = false;
            System.out.println("Error al conectar en la Base de datos: " + e);
            result[0] = "Error";
            result[1] = "Connecting BD";
            close_connection(con);
        }

        if(connected){
            ResultSet rs;
            try{
                Statement st = con.createStatement();
                rs = st.executeQuery(query);

                Vector<String[]> table = makeTable(rs);
                if((table.size() == 1) && (table.get(0)[0] == "Error")) {
                    result[0] = "Error";
                    result[1] = table.get(0)[1];
                }
                else{
                    result[0] = "QueryResult";
                    result[1] = table;
                }

                close_connection(con);
            }
            catch(SQLException e){
                System.out.println("Error al ejecutar la consulta : " + e);
                result[0] = "Error";
                result[1] = "Executing Query";
                close_connection(con);
            }
        }
        return result;

    }

    private Vector <String[]> makeTable(ResultSet rs) {
        Vector <String[]> result;
        result = new Vector<String[]>(10,10);
        try{
            if(!rs.next()){
                String[] mssg = {"Error","Query didn't yield results"};
                result.add(mssg);
                System.out.println("La consulta no encontró registros");
            }
            else{
                int att = rs.getMetaData().getColumnCount();
                String[] reg;
                do{
                    reg = new String[att];
                    for(int i = 1; i <= att; i++){
                        reg[i-1] = rs.getString(i);
                    }

                    result.add(reg);
                }while(rs.next());
            }
        }
        catch(SQLException e){
            result.clear();
            String[] mssg = {"Error","Query mishandling"};
            result.add(mssg);
            System.out.println("Error en el manejo de ResultSet");
        }

        return result;
    }

    private void close_connection(java.sql.Connection con) {
        try{
            if(!con.isClosed()){
                con.close();
            }
        }
        catch(SQLException e){
            System.out.println("¡PRECAUCIÓN! LA CONEXIÓN NO PUDO SER CERRADA");
        }
        catch(NullPointerException e){
            System.out.println("Conexion inexistente, no es necesario cerrarla");
        }
    }

    public boolean modify_DB(String query){
        java.sql.Connection con = null;
        boolean connected;
        boolean success = false;
        try{
            con = DriverManager.getConnection(db_type + host + port + db_name,
                    user + "",
                    password + "");
            connected = true;
        }
        catch(SQLException e){
            connected = false;
            System.out.println("Error al conectar en la Base de datos: " + e);
            close_connection(con);
        }

        if(connected) {
            try {
                Statement st = con.createStatement();
                int n = st.executeUpdate(query);
                close_connection(con);
                success = true;
            }
            catch (SQLException e) {
                System.out.println("Error al ejecutar la consulta: " + e);
                close_connection(con);
            }
        }

        return success;
    }
}