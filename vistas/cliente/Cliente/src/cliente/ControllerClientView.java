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

import javax.swing.JFrame;

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
            } if(number.length()< 10 || number.length() > 10 || Integer.parseInt(number)<0){
                throw new InvalidNumber();
            } if(group1.getSelectedToggle() == null){
                throw new NotRBSelected();
            }
            if(selected.getText().equals("Consulta por mes")){
                int selectedMonthNumber = getMonthNumber((String)monthPickerComboBox.getValue());
                if(selectedMonthNumber>cal.get(Calendar.MONTH)){
                    throw new InvalidMonth();
                }
                DBConnection conexion = new DBConnection("","","","","","");
                Vector<String[]>  result = (Vector) conexion.read_DB("SELECT * FROM Bill WHERE linenumber =" + number)[1];
                Vector<String[]> infoPlan = (Vector) conexion.read_DB("SELECT planid, name FROM Lines, Customer WHERE number="+number+" AND customerid=id")[1];

                System.out.println("Consultando por texto");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("clientViewTextQuery.fxml"));
                Parent root = loader.load();
                ControllerClientViewTextQuery controller = loader.<ControllerClientViewTextQuery>getController();
                controller.setInfo(Arrays.toString(result.get(0)), Arrays.toString(infoPlan.get(0)));
                Scene queryScene = new Scene(root);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setTitle("Consulta consumo por texto");
                window.setScene(queryScene);
                window.show();
            } else if(selected.getText().equals("Consulta por rango de meses")){
                System.out.println("Jejej esto falta");
                System.out.println(monthPickerComboBox1.getSelectionModel().getSelectedItem());
                System.out.println(monthPickerComboBox2.getSelectionModel().getSelectedItem());
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
            } if(number.length()< 10 || number.length() > 10 || Integer.parseInt(number)<0){
                throw new InvalidNumber();
            } if(group1.getSelectedToggle() == null){
                throw new NotRBSelected();
            }

            if(selected.getText().equals("Consulta por mes")){
                int selectedMonthNumber = getMonthNumber((String)monthPickerComboBox.getValue());
                if(selectedMonthNumber>cal.get(Calendar.MONTH)){
                    throw new InvalidMonth();
                }

                DBConnection conexion = new DBConnection("","","","","","");
                Vector<String[]>  result = (Vector) conexion.read_DB("SELECT * FROM Bill WHERE linenumber =" + number)[1];
                Vector<String[]> infoPlan = (Vector) conexion.read_DB("SELECT planid, name FROM Lines, Customer WHERE number="+number+" AND customerid=id")[1];

                CreateGraphic chart = new CreateGraphic(Arrays.toString(result.get(0)), Arrays.toString(infoPlan.get(0)));
                ChartPanel panel = chart.Createchart(selectedMonthNumber+1);
                JFrame Ventana = new JFrame("Consulta consumo por gráfico");
                Ventana.getContentPane().add(panel);
                Ventana.pack();
                Ventana.setVisible(true);
                Ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            } else if(selected.getText().equals("Consulta por rango de meses")){
                System.out.println("Jejej esto falta");
                System.out.println(monthPickerComboBox1.getSelectionModel().getSelectedItem());
                System.out.println(monthPickerComboBox2.getSelectionModel().getSelectedItem());
            }

        } catch(EmptyFields e){
            labelError.setText("El campo número de celular no debe quedar vacío");
        } catch(NotNumber e){
            labelError.setText("El campo numero de celular debe contener solamente números");
        } catch(InvalidMonth e){
            labelError.setText("No hay registros del mes que escogió \nPor favor intente con un mes anterior");
        } catch(NotRBSelected e){
            labelError.setText("Por favor seleccione algún tipo de consulta");
        } catch(InvalidNumber e){
            labelError.setText("El número no es válido");
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

    public int getMonthNumber(String item){
        int j=0;
        for(int i=0; i<months.length; i++){
            if(months[i].equals(item)){
                j=i;
                break;
            }
        }
        return j;
    }
}


