package resources;
import java.util.Vector;
import DB_Connection.DBConnection;

public class EnsayoConsultas {
    public static void main (String args[]){
        // Se crea la instancia del manager:
        DBConnection aQuery = new DBConnection("","","","","","");

        // Se guarda el resultado de la consulta en una arreglo de Objetos
        // Nota: Siempre va a tener dos posiciones.
        Object[] objeto= aQuery.read_DB("SELECT * FROM users;");

        // En la posición 0 va a contener el tipo de resultado, solo existen
        // dos tipos de resultados, "Error" cuando algo falló en el query,
        // o "QueryResult" cuando la consulta arrojó al menos un registro.
        String tipo_resultado = (String) objeto[0];
        System.out.println("\n El tipo de resultado que arrojo la consulta fue un " + tipo_resultado + "\n");

        // Luego, en la posicion 1 el objeto contendrá el resultado en un vector
        // específicamente el vector contiene arreglos de Strings (Vector<String[]>).

        // En caso que el resultado sea "QueryResult", el manejo de los registros
        // sería el siguiente:
        Vector<String[]> misRegistros = (Vector<String[]>) objeto[1];

        // Para acceder al primer registro utilizamos el método get
        String[] tupla_Uno = misRegistros.get(0);

        // Este arreglo de Strings contiene cada atributo del registro,
        // en este caso la consulta no nos dice cuantos atributos
        // traerá (SELECT * FROM empleados), si deseamos saber cuantos
        // aributos trajo, solo basta con utilizar el metodo .lenght para
        // saberlo.
        int numeroAtributos = tupla_Uno.length;
        System.out.println("La consulta contiene " + numeroAtributos + " atributos por registro \n");

        // Ya conociendo la cantidad de atributos, podríamos acceder a cada
        // uno de ellos, una forma puede ser por medio de un ciclo:
        System.out.println("Estos son los atributos del primer registro:");
        for (int j = 0; j < numeroAtributos; j++){
            System.out.println("  " + (j+1) + ": " + tupla_Uno[j] );
        }
        System.out.println(""); // <=== SOLO ES UN SALTO DE LÍNEA

        // Si deseamos conocer la cantidad de registros contenidos en la consulta,
        // lo podremos saber con el método size() del vector misRegistros:
        int cantidadRegistros = misRegistros.size();
        System.out.println("La consulta contiene " + cantidadRegistros + " registros (tuplas) \n");

        // Yendonos más a profundidad, si queremos acceder a cada atributo de cada registro,
        // usaríamos un ciclo adicional que va a recorrer TODOS los registros existentes en
        // el vector misRegistros así:
        String[] tupla;
        for(int i = 0; i < cantidadRegistros; i++){
            tupla = misRegistros.get(i);
            System.out.println("Atributos de la tupla "+ (i+1) + ":");

            for(int j = 0; j < numeroAtributos; j++){
                System.out.println("  " + (j+1) + ": " + tupla[j] );
            }
        }

        // Si el resultado es un error, el vector tendría UNA SOLA POSICIÓN
        // con un arreglo de Strings con DOS posiciones.
        // EJEMPLO: Vector<String[]> miError =  (Vector<String[]>) objeto[1];
        // El vector miError, como dije anteriormente, solo tendría una posicion
        // para acceder a ella
    }
}
