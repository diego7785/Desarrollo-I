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

import java.io.IOException;
import java.util.StringTokenizer;


public class ControllerClientViewTextQuery {

    @FXML
    private TextArea textAreaConsumo;
    @FXML
    private Button buttonOk;
    @FXML
    private Label labelError;


    @FXML
    public void initialize()
    {
        textAreaConsumo.setEditable(false);
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

    public void setInfo(String info, String infoPlan){
        StringTokenizer tokens=new StringTokenizer(infoPlan, ",");
        int nDatos=tokens.countTokens();
        String[] consulta = new String[nDatos];
        int i=0;
        while(tokens.hasMoreTokens()) {
            consulta[i] = tokens.nextToken();
            i++;
        }
        int plan = Integer.parseInt(consulta[0].substring(1,2));
        String name = consulta[1].substring(0,consulta[1].length()-1);

        String text = "Plan tipo: "+plan+"\n"+"Nombre cliente: "+name+"\n";
        tokens=new StringTokenizer(info, ",");
        nDatos=tokens.countTokens();
        consulta = new String[nDatos];
        i=0;
        while(tokens.hasMoreTokens()) {
            consulta[i] = tokens.nextToken();
            i++;
        }
        text += "Linea número: "+consulta[12]+"\n"
                    + "Consumo de datos: "+consulta[1] + " MB\n"
                    + "Consumo de minutos: "+consulta[2]+" Minutos\n"
                    + "Consumo de mensajes: "+consulta[3]+" SMS\n"
                    + "Consumo de datos de Whatsapp: "+consulta[4]+" MB\n"
                    + "Consumo de minutos de Whatsapp: "+consulta[5]+" Minutos\n"
                    + "Consumo de datos de Facebook: "+consulta[6]+" MB\n";

        if(plan == 4){
            text += "Consumo de datos de Waze: "+consulta[7] +" MB";
        } else if(plan == 5){
            text += "Consumo minutos internacionales: "+consulta[8]+" Minutos\n"
                    + "Consumo de datos para compartir: "+consulta[9]+" MB";
        }
        textAreaConsumo.setText(text);

    }

}
