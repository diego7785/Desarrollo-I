package cliente;

import DB_Connection.DBConnection;
import Excepciones.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Vector;

import functionalities.CreateGraphic;
import org.jfree.chart.ChartPanel;

import javax.swing.*;

public class ControllerClientView implements Initializable {

    @FXML
    private TextField textFieldNumber;
    @FXML
    private Label labelError;
    @FXML
    private Label labelSeleccioneMes;
    @FXML
    private Label labelSeleccioneRango;
    @FXML
    private MenuItem menuClose;
    @FXML
    private ComboBox monthPickerComboBox;
    @FXML
    private ComboBox monthPickerComboBox1;
    @FXML
    private ComboBox monthPickerComboBox2;
    @FXML
    private ToggleGroup group1;
    @FXML
    private RadioButton mesEspecifico;
    @FXML
    private RadioButton rangoMeses;

    private final Calendar cal = Calendar.getInstance();
    private final String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
            "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    private RadioButton selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthPickerComboBox.getItems().removeAll(monthPickerComboBox.getItems());
        monthPickerComboBox.getItems().addAll((Object[]) months);
        monthPickerComboBox.getSelectionModel().select(months[cal.get(Calendar.MONTH)]);
        monthPickerComboBox1.getItems().removeAll(monthPickerComboBox1.getItems());
        monthPickerComboBox1.getItems().addAll((Object[]) months);
        monthPickerComboBox1.getSelectionModel().select(months[cal.get(Calendar.MONTH)-1]);
        monthPickerComboBox2.getItems().removeAll(monthPickerComboBox2.getItems());
        monthPickerComboBox2.getItems().addAll((Object[]) months);
        monthPickerComboBox2.getSelectionModel().select(months[cal.get(Calendar.MONTH)]);
        monthPickerComboBox.setVisible(false);
        labelSeleccioneMes.setVisible(false);
        monthPickerComboBox1.setVisible(false);
        labelSeleccioneRango.setVisible(false);
        monthPickerComboBox2.setVisible(false);
        group1 = new ToggleGroup();
        mesEspecifico.setToggleGroup(group1);
        rangoMeses.setToggleGroup(group1);
        group1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                selected = (RadioButton) group1.getSelectedToggle();
                if(selected.getText().equals("Consulta por rango de meses")){
                    labelSeleccioneRango.setVisible(true);
                    monthPickerComboBox1.setVisible(true);
                    monthPickerComboBox2.setVisible(true);
                    labelSeleccioneMes.setVisible(false);
                    monthPickerComboBox.setVisible(false);
                } else if(selected.getText().equals("Consulta por mes")){
                    labelSeleccioneMes.setVisible(true);
                    monthPickerComboBox.setVisible(true);
                    labelSeleccioneRango.setVisible(false);
                    monthPickerComboBox1.setVisible(false);
                    monthPickerComboBox2.setVisible(false);
                }
            }
        });
    }

    public void handleButtonSearchText(ActionEvent actionEvent) {
        labelError.setText("");
        try{
            String number=(String) textFieldNumber.getText();
            if(number.equals("")){
                throw new EmptyFields();
            } if (!isNumeric(number)){
                throw new NotNumber();
            } if(number.length()< 10 || number.length() > 10 || Long.parseLong(number)<0){
                throw new InvalidNumber();
            } if(group1.getSelectedToggle() == null){
                throw new NotRBSelected();
            }
            if(selected.getText().equals("Consulta por mes")) {
                int selectedMonthNumber = monthPickerComboBox.getSelectionModel().getSelectedIndex();
                if (selectedMonthNumber > cal.get(Calendar.MONTH)) {
                    throw new InvalidMonth();
                }
                DBConnection connection = new DBConnection("", "", "", "", "", "");
                Object[] resultO = connection.read_DB("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND EXTRACT(YEAR FROM date) = "+cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+(selectedMonthNumber+1)+";");
                if (resultO[0].equals("Error")) {
                    throw new QueryError();
                } else {

                    Vector<String[]> result = (Vector) resultO[1];
                    Vector<String[]> infoPlan = (Vector) connection.read_DB("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;")[1];

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("clientViewTextQuery.fxml"));
                    Parent root = loader.load();
                    ControllerClientViewTextQuery controller = loader.<ControllerClientViewTextQuery>getController();
                    controller.setInfo(result, infoPlan.get(0),1);
                    Scene queryScene = new Scene(root);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setTitle("Consulta consumo por texto");
                    window.setScene(queryScene);
                    window.show();

                }
            } else if (selected.getText().equals("Consulta por rango de meses")) {
                int initialMonth = monthPickerComboBox1.getSelectionModel().getSelectedIndex();
                int finalMonth = monthPickerComboBox2.getSelectionModel().getSelectedIndex();
                DBConnection connection = new DBConnection("", "", "", "", "", "");
                if (initialMonth == finalMonth){
                    throw new UseAnotherQuery();
                } else if(initialMonth == 1){
                    Object[] resultO = connection.read_DB("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date BETWEEN '"+cal.get(Calendar.YEAR)+"/02/28' AND '"+cal.get(Calendar.YEAR)+"/"+(finalMonth+1)+"/30';");
                    if (resultO[0].equals("Error")) {
                        throw new QueryError();
                    }
                    if(finalMonth>cal.get(Calendar.MONTH)){
                        throw new QueryError();
                    }
                    Vector<String[]> result = (Vector) resultO[1];
                    Vector<String[]> infoPlan = (Vector) connection.read_DB("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;")[1];

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("clientViewTextQuery.fxml"));
                    Parent root = loader.load();
                    ControllerClientViewTextQuery controller = loader.<ControllerClientViewTextQuery>getController();
                    controller.setInfo(result, infoPlan.get(0),2);
                    Scene queryScene = new Scene(root);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setTitle("Consulta consumo por texto");
                    window.setScene(queryScene);
                    window.show();

                } else if(finalMonth == 1){
                    Object[] resultO = connection.read_DB("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date BETWEEN '"+cal.get(Calendar.YEAR)+"/"+(initialMonth+1)+"/30' AND '"+cal.get(Calendar.YEAR)+"/02/28';");
                    if (resultO[0].equals("Error")) {
                        throw new QueryError();
                    }
                    Vector<String[]> result = (Vector) resultO[1];
                    Vector<String[]> infoPlan = (Vector) connection.read_DB("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;")[1];

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("clientViewTextQuery.fxml"));
                    Parent root = loader.load();
                    ControllerClientViewTextQuery controller = loader.<ControllerClientViewTextQuery>getController();
                    controller.setInfo(result, infoPlan.get(0),2);
                    Scene queryScene = new Scene(root);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setTitle("Consulta consumo por texto");
                    window.setScene(queryScene);
                    window.show();
                } else{
                    Object[] resultO = connection.read_DB("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date BETWEEN '"+cal.get(Calendar.YEAR)+"/"+(initialMonth+1)+"/30' AND '"+cal.get(Calendar.YEAR)+"/"+(finalMonth+1)+"/30';");
                    if (resultO[0].equals("Error")) {
                        throw new QueryError();
                    }
                    if(finalMonth>cal.get(Calendar.MONTH)){
                        throw new QueryError();
                    }
                    Vector<String[]> result = (Vector) resultO[1];
                    Vector<String[]> infoPlan = (Vector) connection.read_DB("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;")[1];

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("clientViewTextQuery.fxml"));
                    Parent root = loader.load();
                    ControllerClientViewTextQuery controller = loader.<ControllerClientViewTextQuery>getController();
                    controller.setInfo(result, infoPlan.get(0),2);
                    Scene queryScene = new Scene(root);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setTitle("Consulta consumo por texto");
                    window.setScene(queryScene);
                    window.show();
                }
            }

        } catch(EmptyFields e){
            labelError.setText("El campo número de celular no debe quedar vacío");
        } catch(NotNumber e){
            labelError.setText("El campo numero de celular debe contener solamente números");
        } catch(InvalidMonth e){
            labelError.setText("No hay registros del mes que escogió \nPor favor intente con un mes anterior");
        } catch(InvalidNumber e){
            labelError.setText("El número no es válido");
        } catch(NotRBSelected e){
            labelError.setText("Por favor seleccione algún tipo de consulta");
        } catch(QueryError e){
            labelError.setText("La consulta no arrojó resultados");
        } catch(UseAnotherQuery e){
            labelError.setText("Rango de meses iguales \nPor favor utilice la consulta por mes");
        } catch (IOException e) {
            labelError.setText("No se puede acceder a la consulta ");
        }
    }

    public void handleButtonSearchGraphic(ActionEvent actionEvent) {
        labelError.setText("");
        try{
            String number=(String) textFieldNumber.getText();
            if(number.equals("")){
                throw new EmptyFields();
            } if (!isNumeric(number)){
                throw new NotNumber();
            } if(number.length()< 10 || number.length() > 10 || Long.parseLong(number)<0){
                throw new InvalidNumber();
            } if(group1.getSelectedToggle() == null){
                throw new NotRBSelected();
            }

            if(selected.getText().equals("Consulta por mes")) {
                int selectedMonthNumber = monthPickerComboBox.getSelectionModel().getSelectedIndex();
                if (selectedMonthNumber > cal.get(Calendar.MONTH)) {
                    throw new InvalidMonth();
                }
                DBConnection connection = new DBConnection("", "", "", "", "", "");
                Object[] resultO = connection.read_DB("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND EXTRACT(YEAR FROM date) = "+cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+(selectedMonthNumber+1)+";");
                if (resultO[0].equals("Error")) {
                    throw new QueryError();
                } else {
                    Vector<String[]> result = (Vector) resultO[1];
                    Vector<String[]> infoPlan = (Vector) connection.read_DB("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;")[1];

                    CreateGraphic chart = new CreateGraphic();
                    ChartPanel panel = chart.Createchart(result, infoPlan.get(0), 1);
                    JFrame Window = new JFrame("Consulta consumo por gráfico");
                    Window.getContentPane().add(panel);
                    Window.pack();
                    Window.setVisible(true);
                    Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            } else if (selected.getText().equals("Consulta por rango de meses")) {
                int initialMonth = monthPickerComboBox1.getSelectionModel().getSelectedIndex();
                int finalMonth = monthPickerComboBox2.getSelectionModel().getSelectedIndex();
                DBConnection connection = new DBConnection("", "", "", "", "", "");
                if (initialMonth == finalMonth){
                    throw new UseAnotherQuery();
                } else if(initialMonth == 1){
                    Object[] resultO = connection.read_DB("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date BETWEEN '"+cal.get(Calendar.YEAR)+"/02/28' AND '"+cal.get(Calendar.YEAR)+"/"+(finalMonth+1)+"/30';");
                    if (resultO[0].equals("Error")) {
                        throw new QueryError();
                    }
                    if(finalMonth>cal.get(Calendar.MONTH)){
                        throw new QueryError();
                    }
                    Vector<String[]> result = (Vector) resultO[1];
                    Vector<String[]> infoPlan = (Vector) connection.read_DB("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;")[1];

                    CreateGraphic chart = new CreateGraphic();
                    ChartPanel panel = chart.Createchart(result, infoPlan.get(0), 2);
                    JFrame Window = new JFrame("Consulta consumo por gráfico");
                    Window.getContentPane().add(panel);
                    Window.pack();
                    Window.setVisible(true);
                    Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                } else if(finalMonth == 1){
                    Object[] resultO = connection.read_DB("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date BETWEEN '"+cal.get(Calendar.YEAR)+"/"+(initialMonth+1)+"/30' AND '"+cal.get(Calendar.YEAR)+"/02/28';");
                    if (resultO[0].equals("Error")) {
                        throw new QueryError();
                    }
                    Vector<String[]> result = (Vector) resultO[1];
                    Vector<String[]> infoPlan = (Vector) connection.read_DB("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;")[1];

                    CreateGraphic chart = new CreateGraphic();
                    ChartPanel panel = chart.Createchart(result, infoPlan.get(0), 2);
                    JFrame Window = new JFrame("Consulta consumo por gráfico");
                    Window.getContentPane().add(panel);
                    Window.pack();
                    Window.setVisible(true);
                    Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else{
                    Object[] resultO = connection.read_DB("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date BETWEEN '"+cal.get(Calendar.YEAR)+"/"+(initialMonth+1)+"/30' AND '"+cal.get(Calendar.YEAR)+"/"+(finalMonth+1)+"/30';");
                    if (resultO[0].equals("Error")) {
                        throw new QueryError();
                    }
                    if(finalMonth>cal.get(Calendar.MONTH)){
                        throw new QueryError();
                    }
                    Vector<String[]> result = (Vector) resultO[1];
                    Vector<String[]> infoPlan = (Vector) connection.read_DB("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;")[1];

                    CreateGraphic chart = new CreateGraphic();
                    ChartPanel panel = chart.Createchart(result, infoPlan.get(0), 2);
                    JFrame Window = new JFrame("Consulta consumo por gráfico");
                    Window.getContentPane().add(panel);
                    Window.pack();
                    Window.setVisible(true);
                    Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }

        } catch(EmptyFields e){
            labelError.setText("El campo número de celular no debe quedar vacío");
        } catch(NotNumber e){
            labelError.setText("El campo numero de celular debe contener solamente números");
        } catch(InvalidMonth e){
            labelError.setText("No hay registros del mes que escogió \nPor favor intente con un mes anterior");
        } catch(NotRBSelected e){
            labelError.setText("Por favor seleccione algún tipo de consulta");
        } catch(QueryError e){
            labelError.setText("La consulta no arrojó resultados");
        } catch(InvalidNumber e){
            labelError.setText("El número no es válido");
        } catch(UseAnotherQuery e){
            labelError.setText("Rango de meses iguales \nPor favor utilice la consulta por mes");
        } catch (Exception e) {
            labelError.setText("No se puede acceder a la consulta ");
        }
    }

    public void handleMenuClose(ActionEvent actionEvent) {
        System.exit(0);
    }


    public boolean isNumeric(String cadena){
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}


