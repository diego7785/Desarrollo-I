package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller {

    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXTextField tf_user;
    @FXML
    private JFXPasswordField tf_pass;
    @FXML
    private JFXComboBox cb_rol;
    @FXML
    private JFXButton btn_reg;
    @FXML
    private JFXTextField tf_nombre;
    @FXML
    private ImageView agregar_user;
    @FXML
    private ImageView realizar_venta;
    @FXML
    private AnchorPane pane_venta;
    @FXML
    private AnchorPane pane_user;


    @FXML
    public void log_in(ActionEvent event) throws Exception {


        if(tf_user.getText().equals("user")  && tf_pass.getText().equals("pass")){

            btn_login.getScene().getWindow().hide();

            Stage primaryStage = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            Scene login = new Scene(root);

            primaryStage.setTitle("");


            primaryStage.setScene(login);
            primaryStage.show();
        }

    }

    @FXML
    public void registro(ActionEvent event) throws Exception{
        String nombre = tf_nombre.getText();

    }

    @FXML
    public void cambiar_pestana(MouseEvent event) throws Exception{
        if(event.getSource().equals(agregar_user)){
            pane_user.setVisible(true);
            pane_venta.setVisible(false);
        }
        if(event.getSource().equals(realizar_venta)){
            pane_user.setVisible(false);
            pane_venta.setVisible(true);
        }
    }

}