/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_app;

import Excepciones.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;


/**
 *
 * @author Diego Andrés Bonilla
 */
public class FXMLViewController implements Initializable {
    
    @FXML
    private Button btnSearchText;
    @FXML
    private Button btnSearchGraphic;
    @FXML
    private TextField textFieldNumber;
    @FXML
    private Label labelError;
    @FXML
    private ComboBox monthPickerComboBox;
    @FXML
    private MenuItem menuClose;
    
    private final Calendar cal = Calendar.getInstance();
    private final String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
                "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    
    @FXML
    private void handleButtonSearchText(ActionEvent event) {
        labelError.setText("");
        try{
        String number=(String) textFieldNumber.getText();
        if(number.equals("")){
            throw new EmptyFields();
        } if (!isNumeric(number)){
            throw new NotNumber();
        } if(number.length()< 10 || number.length() > 10){
            throw new InvalidNumber();
        }
        int selectedMonthNumber = getMonthNumber((String)monthPickerComboBox.getValue());
        if(selectedMonthNumber>cal.get(Calendar.MONTH)){
            throw new InvalidMonth();
        }
        System.out.println("Consultando por texto");
        Parent queryView = FXMLLoader.load(getClass().getResource("QueryTextView.fxml"));  
        
        Scene queryScene = new Scene(queryView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
    
    @FXML
    private void handleButtonSearchGraphic(ActionEvent event) {
        labelError.setText("");
        try{
        String number=(String) textFieldNumber.getText();
        if(number.equals("")){
            throw new EmptyFields();
        } if (!isNumeric(number)){
            throw new NotNumber();
        } if(number.length()< 10 || number.length() > 10){
            throw new InvalidNumber();
        }
        int selectedMonthNumber = getMonthNumber((String)monthPickerComboBox.getValue());
        if(selectedMonthNumber>cal.get(Calendar.MONTH)){
            throw new InvalidMonth();
        }
        System.out.println("Consultando por gráfico");
        Parent queryView = FXMLLoader.load(getClass().getResource("QueryTextView.fxml"));  
        
        Scene queryScene = new Scene(queryView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
    
    @FXML
    public void handleMenuClose(ActionEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthPickerComboBox.getItems().removeAll(monthPickerComboBox.getItems());
        monthPickerComboBox.getItems().addAll((Object[]) months);
        monthPickerComboBox.getSelectionModel().select(months[cal.get(Calendar.MONTH)]);
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


