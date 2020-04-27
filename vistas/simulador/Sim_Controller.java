import DB_Connection.DBConnection;
import Exceptions.Password_exception;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

public class Sim_Controller implements Initializable {

    String number = "";

    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    int month = cal.get(Calendar.MONTH);
    int year = cal.get(Calendar.YEAR);
    String dateSearch = year + "-" + month;

    @FXML
    AnchorPane wpp_main;
    @FXML
    AnchorPane main_pane;
    @FXML
    AnchorPane phone_pane;
    @FXML
    javafx.scene.image.ImageView fb_icon;
    @FXML
    javafx.scene.image.ImageView BTback;
    @FXML
    javafx.scene.image.ImageView wpp_icon;
    @FXML
    javafx.scene.image.ImageView sms_icon;
    @FXML
    javafx.scene.image.ImageView phone_icon;
    @FXML
    javafx.scene.image.ImageView waze_icon;
    @FXML
    javafx.scene.image.ImageView nat_call;
    @FXML
    javafx.scene.image.ImageView inter_call;


    private DBConnection connection= new DBConnection("", "", "", "", "", "");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void set_number(String num){
        number = num;
    }

    public void open_wpp(javafx.scene.input.MouseEvent mouseEvent) {
        wpp_main.setVisible(true);
        main_pane.setVisible(false);
    }
    public void back_to_main(javafx.scene.input.MouseEvent mouseEvent){
        wpp_main.setVisible(false);
        main_pane.setVisible(true);
        phone_pane.setVisible(false);
    }
    public void open_phone(MouseEvent mouseEvent) {
        main_pane.setVisible(false);
        phone_pane.setVisible(true);
    }
    public void call_wpp(MouseEvent mouseEvent) {

        double randomDouble = Math.random()*10 + 1;
        int minutosUsados = (int) randomDouble;
        Object[] wpp = connection.read_DB("SELECT minutes_wpp FROM Bill WHERE lineNumber = '"+number+"' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";");
        Vector<String[]> bill = (Vector<String[]>) wpp[1];
        int min = Integer.parseInt(bill.get(0)[0]);

        if(min == 0){
            JOptionPane.showMessageDialog(null, "Ya usaste todos los minutos de Whatsapp");
        }else if(minutosUsados > min){
            min = 0;
            if(connection.modify_DB("UPDATE Bill SET minutes_wpp = "+ min +" WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";")) {
                JOptionPane.showMessageDialog(null, "Consumiste todos los minutos que te quedaban");
            }else{
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
            }
        }else{
            min -= minutosUsados;
            if(connection.modify_DB("UPDATE Bill SET minutes_wpp = "+ min +" WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";")) {
                JOptionPane.showMessageDialog(null, "Consumiste "+minutosUsados+" minutos, te quedan " + min);
            }else{
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
            }
        }
    }

    public void sms_wpp(MouseEvent mouseEvent) {

        double randomDouble = Math.random()*10 + 1;
        int megasUsadas = (int) randomDouble;
        Object[] wpp = connection.read_DB("SELECT data_wpp, data_consuption FROM Bill WHERE lineNumber = '"+number+"' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";");
        Vector<String[]> bill = (Vector<String[]>) wpp[1];
        float megasWpp = Float.parseFloat(bill.get(0)[0]);
        float   megas = Float.parseFloat(bill.get(0)[1]);

        if(megasWpp == 0 && megas == 0){
            JOptionPane.showMessageDialog(null, "Ya consumiste todas tus megas de Whatsapp y del paquete de datos");
        }else if(megas+megasWpp-megasUsadas >= 0){
            float megas_sobrantes = (megasWpp - megasUsadas)*-1;
            megasWpp = 0;
            megas = megas - megas_sobrantes;
            if(connection.modify_DB("UPDATE Bill SET data_wpp = "+ megasWpp + ", data_consuption = " + megas + " WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";")) {
                JOptionPane.showMessageDialog(null, "Consumiste el resto de megas de wpp, se han usado además " + megas_sobrantes + " megas de tu paquete de datos, te quedan " + megas + " megas para navegar");
            }else{
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
            }
        }else if(megas+megasWpp < megasUsadas){
            megas = 0;
            megasWpp = 0;
            if(connection.modify_DB("UPDATE Bill SET data_wpp = "+ megasWpp + ", data_consuption = " + megas + " WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";")) {
                JOptionPane.showMessageDialog(null, "Consumiste todas tus megas. Recuerda que puedes recargar otro plan en la Raja App");
            }else{
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
            }
        }
    }

    public void send_sms(MouseEvent mouseEvent) {

        Object[] msg = connection.read_DB("SELECT sms_consuption FROM Bill WHERE lineNumber = '"+number+"' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";");
        Vector<String[]> bill = (Vector<String[]>) msg[1];
        int sms = Integer.parseInt(bill.get(0)[0]);

        if(sms > 1){
            if(connection.modify_DB("UPDATE Bill SET sms_consuption = "+ (sms - 1) + " WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";")) {
                JOptionPane.showMessageDialog(null, "Consumiste un mensaje de texto. Te restan " + (sms - 1) + " mensajes de texto.");
            }else{
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
            }
        }else if(sms == 1){
            if(connection.modify_DB("UPDATE Bill SET data_wpp = "+ 0 + " WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";")) {
                JOptionPane.showMessageDialog(null, "Consumiste todas tus mensaje de texto. Recuerda que puedes recargar otro plan en la Raja App");
            }else{
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
            }
        }else if(sms == 0){
            JOptionPane.showMessageDialog(null, "No se pudo enviar el mensaje. Ya consumiste todos tus mensajes de texto");
        }
    }
    public void national_call(MouseEvent mouseEvent) {
        Object[] msg = connection.read_DB("SELECT minutes_consuption FROM Bill WHERE lineNumber = '"+number+"' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";");
        Vector<String[]> bill = (Vector<String[]>) msg[1];
        int mins = Integer.parseInt(bill.get(0)[0]);
        double randomDouble = Math.random()*10 + 1;
        int minsUsados = (int) randomDouble;
        if(mins == 0){
            JOptionPane.showMessageDialog(null, "Ya consumiste todas tus llamadas nacionales. Recuerda que puedes recargar otro plan en la Raja App");
        }else if(mins < minsUsados){
            mins = 0;
            if(connection.modify_DB("UPDATE Bill SET minutes_consuption = "+ (0 + " WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";")))
                JOptionPane.showMessageDialog(null, "Consumiste " + (minsUsados-mins) + ". Te restan " + mins + " minutos.");
            else
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
        }else{
            if(connection.modify_DB("UPDATE Bill SET minutes_consuption = "+ (mins-minsUsados) + " WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";"))
                JOptionPane.showMessageDialog(null, "Consumiste " + minsUsados+ " minutos. Te restan " + (mins-minsUsados) + " minutos.");
            else
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
        }
    }

    public void international_call(MouseEvent mouseEvent) {
        Object[] int_mins = connection.read_DB("SELECT minutes_international FROM Bill WHERE lineNumber = '"+number+"' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";");
        Vector<String[]> bill = (Vector<String[]>) int_mins[1];
        int mins = Integer.parseInt(bill.get(0)[0]);
        double randomDouble = Math.random()*10 + 1;
        int minsUsados = (int) randomDouble;
        if(mins == 0){
            JOptionPane.showMessageDialog(null, "Ya consumiste todas tus llamadas internacionales. Recuerda que puedes recargar otro plan en la Raja App");
        }else if(mins < minsUsados){
            mins = 0;
            if(connection.modify_DB("UPDATE Bill SET minutes_international = "+ (0 + " WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";")))
                JOptionPane.showMessageDialog(null, "Consumiste " + (minsUsados-mins) + ". Te restan " + mins + " minutos.");
            else
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
        }else{
            if(connection.modify_DB("UPDATE Bill SET minutes_international = "+ (mins-minsUsados) + " WHERE lineNumber = '" + number + "' AND EXTRACT(YEAR FROM date) = "+ cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+ cal.get((Calendar.MONTH)) +";"))
                JOptionPane.showMessageDialog(null, "Consumiste " + minsUsados+ " minutos. Te restan " + (mins-minsUsados) + " minutos.");
            else
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
        }
    }
}
