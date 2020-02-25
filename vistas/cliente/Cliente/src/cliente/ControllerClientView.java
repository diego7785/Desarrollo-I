package cliente;

import Excepciones.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ControllerClientView implements Initializable {

    public Button btnSearchText;
    public Label label;
    public Button btnSearchGraphic;
    public TextField textFieldNumber;
    public Label labelError;
    public MenuItem menuClose;
    public ComboBox monthPickerComboBox;

    private final Calendar cal = Calendar.getInstance();
    private final String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
            "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthPickerComboBox.getItems().removeAll(monthPickerComboBox.getItems());
        monthPickerComboBox.getItems().addAll((Object[]) months);
        monthPickerComboBox.getSelectionModel().select(months[cal.get(Calendar.MONTH)]);
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
            }
            int selectedMonthNumber = getMonthNumber((String)monthPickerComboBox.getValue());
            if(selectedMonthNumber>cal.get(Calendar.MONTH)){
                throw new InvalidMonth();
            }
            System.out.println("Consultando por texto");
            Parent queryView = FXMLLoader.load(getClass().getResource("clientViewTextQuery.fxml"));

            Scene queryScene = new Scene(queryView);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setTitle("Consulta consumo por texto");
            window.setScene(queryScene);
            window.show();

        } catch(EmptyFields e){
            labelError.setText("El campo número de celular no debe quedar vacío");
        } catch(NotNumber e){
            labelError.setText("El campo numero de celular debe contener solamente números");
        } catch(InvalidMonth e){
            labelError.setText("No hay registros del mes que escogió \nPor favor intente con un mes anterior");
        } catch(InvalidNumber e){
            labelError.setText("El número no es válido");
        } catch (IOException e) {
            labelError.setText("No se puede acceder a la consulta");
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
            }
            int selectedMonthNumber = getMonthNumber((String)monthPickerComboBox.getValue());
            if(selectedMonthNumber>cal.get(Calendar.MONTH)){
                throw new InvalidMonth();
            }
            System.out.println("Consultando por gráfico");
            Parent queryView = FXMLLoader.load(getClass().getResource("clientViewGraphicQuery.fxml"));

            Scene queryScene = new Scene(queryView);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setTitle("Consulta consumo por gráfico");
            window.setScene(queryScene);
            window.show();
        } catch(EmptyFields e){
            labelError.setText("El campo número de celular no debe quedar vacío");
        } catch(NotNumber e){
            labelError.setText("El campo numero de celular debe contener solamente números");
        } catch(InvalidMonth e){
            labelError.setText("No hay registros del mes que escogió \nPor favor intente con un mes anterior");
        } catch(InvalidNumber e){
            labelError.setText("El número no es válido");
        } catch (IOException e) {
            labelError.setText("No se puede acceder a la consulta");
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


