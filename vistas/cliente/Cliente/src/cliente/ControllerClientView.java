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

import javax.management.ValueExp;
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
    private DBConnection connection = new DBConnection("", "", "", "", "", "");

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
        JOptionPane.showMessageDialog(null, "Realizando consulta por texto, por favor espere");
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
                Vector<String[]> result = this.query("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND EXTRACT(YEAR FROM date_pdf) = "+cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date_pdf) = "+(selectedMonthNumber+1)+";");

                Vector<String[]> infoPlan = this.query("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;");

                createTextQuery(result, infoPlan, 1, actionEvent);

            } else if (selected.getText().equals("Consulta por rango de meses")) {
                int initialMonth = monthPickerComboBox1.getSelectionModel().getSelectedIndex();
                int finalMonth = monthPickerComboBox2.getSelectionModel().getSelectedIndex();
                if (initialMonth == finalMonth){
                    throw new UseAnotherQuery();
                } else if(initialMonth == 1){
                    if(finalMonth>cal.get(Calendar.MONTH)){
                        throw new QueryError();
                    }
                    Vector<String[]> result = this.query("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date_pdf BETWEEN '"+cal.get(Calendar.YEAR)+"/02/28' AND '"+cal.get(Calendar.YEAR)+"/"+(finalMonth+1)+"/30';");

                    Vector<String[]> infoPlan = this.query("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;");

                    createTextQuery(result, infoPlan, 2, actionEvent);

                } else if(finalMonth == 1){
                    Vector<String[]> result = this.query("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date_pdf BETWEEN '"+cal.get(Calendar.YEAR)+"/"+(initialMonth+1)+"/30' AND '"+cal.get(Calendar.YEAR)+"/02/28';");

                    Vector<String[]> infoPlan = this.query("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;");

                    createTextQuery(result, infoPlan, 2, actionEvent);

                } else{
                    if(finalMonth>cal.get(Calendar.MONTH)){
                        throw new QueryError();
                    }
                    Vector<String[]> result = this.query("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date_pdf BETWEEN '"+cal.get(Calendar.YEAR)+"/"+(initialMonth+1)+"/30' AND '"+cal.get(Calendar.YEAR)+"/"+(finalMonth+1)+"/30';");
                    Vector<String[]> infoPlan = this.query("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;");

                    createTextQuery(result, infoPlan,2, actionEvent);
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
        }
    }

    public void handleButtonSearchGraphic(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "Realizando consulta gráfica, por favor espere a que el gráfico aparezca");
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
                Vector<String[]> result = this.query("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND EXTRACT(YEAR FROM date_pdf) = "+cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date_pdf) = "+(selectedMonthNumber+1)+";");

                Vector<String[]> infoPlan = this.query("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;");

                createGraphic(result,infoPlan,1);

            } else if (selected.getText().equals("Consulta por rango de meses")) {
                int initialMonth = monthPickerComboBox1.getSelectionModel().getSelectedIndex();
                int finalMonth = monthPickerComboBox2.getSelectionModel().getSelectedIndex();
                if (initialMonth == finalMonth){
                    throw new UseAnotherQuery();
                } else if(initialMonth == 1){
                    if(finalMonth>cal.get(Calendar.MONTH)){
                        throw new QueryError();
                    }
                    Vector<String[]> result = this.query("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date_pdf BETWEEN '"+cal.get(Calendar.YEAR)+"/02/28' AND '"+cal.get(Calendar.YEAR)+"/"+(finalMonth+1)+"/30';");

                    Vector<String[]> infoPlan = this.query("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;");

                    createGraphic(result,infoPlan,2);

                } else if(finalMonth == 1){
                    Vector<String[]> result = this.query("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date_pdf BETWEEN '"+cal.get(Calendar.YEAR)+"/"+(initialMonth+1)+"/30' AND '"+cal.get(Calendar.YEAR)+"/02/28';");

                    Vector<String[]> infoPlan = this.query("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;");

                    createGraphic(result,infoPlan,2);

                } else{
                    if(finalMonth>cal.get(Calendar.MONTH)){
                        throw new QueryError();
                    }
                    Vector<String[]> result = this.query("SELECT * FROM Bill WHERE linenumber = '"+number+"' AND date_pdf BETWEEN '"+cal.get(Calendar.YEAR)+"/"+(initialMonth+1)+"/30' AND '"+cal.get(Calendar.YEAR)+"/"+(finalMonth+1)+"/30';");

                    Vector<String[]> infoPlan= this.query("SELECT planid, name FROM Lines, Customer WHERE number= '" + number + "' AND customerid=id;");

                    createGraphic(result, infoPlan, 2);
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

    public Vector<String[]> query(String query1){
        Vector<String[]> result = null;
        try {
            Object[] resultO = connection.read_DB(query1);
            if (resultO[0].equals("Error")) {
                throw new QueryError();
            }
            result = (Vector<String[]>) resultO[1];

        } catch (QueryError e){
            labelError.setText("La consulta no arrojó resultados");
        }
        return result;
    }

    public void createGraphic(Vector<String[]> result, Vector<String[]> infoPlan, int tipo){
        CreateGraphic chart = new CreateGraphic();
        ChartPanel panel = chart.Createchart(result, infoPlan.get(0), tipo);
        JFrame Window = new JFrame("Consulta consumo por gráfico");
        Window.getContentPane().add(panel);
        Window.pack();
        Window.setVisible(true);
        Window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void createTextQuery(Vector<String[]> result, Vector<String[]> infoPlan, int tipo, ActionEvent actionEvent){
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("clientViewTextQuery.fxml"));
            root = loader.load();
            ControllerClientViewTextQuery controller = loader.<ControllerClientViewTextQuery>getController();
            controller.setInfo(result, infoPlan.get(0),tipo);
            Scene queryScene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setTitle("Consulta consumo por texto");
            window.setScene(queryScene);
            window.show();
        } catch (IOException e) {
            labelError.setText("No se puede acceder a la consulta ");
        }
    }
}


