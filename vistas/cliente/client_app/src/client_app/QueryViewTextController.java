/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Diego Andrés Bonilla
 */
public class QueryViewTextController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label labelInformation;
    @FXML
    private Button buttonOk;
    @FXML
    private Label labelError;
    
    @FXML
    private void handleButtonOk(ActionEvent event){
        try{
        Parent queryView = FXMLLoader.load(getClass().getResource("FXMLView.fxml"));  
        
        Scene queryScene = new Scene(queryView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(queryScene);
        window.show();
        } catch(IOException e){
            labelError.setText("Se ha producido un error \nPor favor intente de nuevo");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
