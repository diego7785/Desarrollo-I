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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class Sim_Controller implements Initializable {

    String number = "";


    @FXML
    AnchorPane wpp_main;
    @FXML
    AnchorPane main_pane;
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
    }

    public void call_wpp(MouseEvent mouseEvent) {

        double randomDouble = Math.random()*10 + 1;
        int minutosUsados = (int) randomDouble;
        Object[] wpp = connection.read_DB("SELECT minutes_wpp FROM Bill WHERE lineNumber = '"+number+"';");
        Vector<String[]> bill = (Vector<String[]>) wpp[1];
        int min = Integer.parseInt(bill.get(0)[0]);

        if(min == 0){
            JOptionPane.showMessageDialog(null, "Ya usaste todos los minutos de Whatsapp");
        }else if(minutosUsados > min){
            min = 0;
            if(connection.modify_DB("UPDATE Bill SET minutes_wpp = "+ min +" WHERE lineNumber = '" + number + "';")) {
                JOptionPane.showMessageDialog(null, "Consumiste todos los minutos que te quedaban");
            }else{
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
            }
        }else{
            min -= minutosUsados;
            if(connection.modify_DB("UPDATE Bill SET minutes_wpp = "+ min +" WHERE lineNumber = '" + number + "';")) {
                JOptionPane.showMessageDialog(null, "Consumiste "+minutosUsados+" te quedan " + min);
            }else{
                JOptionPane.showMessageDialog(null, "Hubo un error al actualizar la base de datos");
            }
        }
    }

    public void sms_wpp(MouseEvent mouseEvent) {
    }
}
