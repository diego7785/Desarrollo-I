package cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;

public class ControllerClientViewTextQuery {

    public TextArea textAreaConsumo;

    public Button buttonOk;
    public Label labelError;

    @FXML
    public void initialize()
    {
        textAreaConsumo.setEditable(false);
        textAreaConsumo.setText("prueba");

    }

    public void handleButtonOk(ActionEvent actionEvent) {
        try{
            Parent queryView = FXMLLoader.load(getClass().getResource("clientView.fxml"));

            Scene queryScene = new Scene(queryView);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(queryScene);
            window.setTitle("Telefonía Raja");
            window.show();

            labelError.setText("prueba");
        } catch(IOException e){
            labelError.setText("Se ha producido un error \nPor favor intente de nuevo");
        }
    }
}
