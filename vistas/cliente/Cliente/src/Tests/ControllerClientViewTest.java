package Tests;

import DB_Connection.DBConnection;
import cliente.ControllerClientView;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerClientViewTest {

    private final ControllerClientView controller = new ControllerClientView();
    @InjectMocks
    private DBConnection dbConnection;
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;

    ControllerClientViewTest() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @org.junit.jupiter.api.Test
    void isNumeric() {
        assertEquals(true, controller.isNumeric("12345"));
    }

    @org.junit.jupiter.api.Test
    void isNumericError() {
        assertEquals(false, controller.isNumeric("hola"));
    }

    @org.junit.jupiter.api.Test
    void query() throws Exception{
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        ResultSet rs = mockStatement.executeQuery("SELECT * FROM Bill");
        Mockito.when(mockConnection.createStatement().executeQuery(Mockito.any())).thenReturn(rs);
        dbConnection = new DBConnection("","","","","","");
        //assertEquals(dbConnection.read_DB("SELECT * FROM Bill"), controller.query("SELECT * FROM Bill", "SELECT * FROM Lines")[0]);
    }
}