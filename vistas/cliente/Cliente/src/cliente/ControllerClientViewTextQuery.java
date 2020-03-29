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
import java.util.Vector;


public class ControllerClientViewTextQuery {

    @FXML
    private TextArea textAreaConsumo;
    @FXML
    private Button buttonOk;
    @FXML
    private Label labelError;

    private final String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
            "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

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

    public void setInfo(Vector<String[]> infoV, String infoPlan[], int type){
        if(type == 1){
            String[] info = infoV.get(0);
            int monthI = Integer.parseInt(info[13].substring(5,7));
            String month = months[monthI];
            int plan = Integer.parseInt(infoPlan[0]);
            String name = infoPlan[1];

            String text = "Plan tipo: "+plan+"\n"+"Nombre cliente: "+name+"\n"+"Mes: "+month+"\n";

            text += "Linea número: "+info[12]+"\n"
                    + "Consumo de datos: "+info[1] + " MB\n"
                    + "Consumo de minutos: "+info[2]+" Minutos\n"
                    + "Consumo de mensajes: "+info[3]+" SMS\n"
                    + "Consumo de datos de Whatsapp: "+info[4]+" MB\n"
                    + "Consumo de minutos de Whatsapp: "+info[5]+" Minutos\n"
                    + "Consumo de datos de Facebook: "+info[6]+" MB\n";

            if(plan == 4){
                text += "Consumo de datos de Waze: "+info[7] +" MB";
            } else if(plan == 5){
                text += "Consumo minutos internacionales: "+info[8]+" Minutos\n"
                        + "Consumo de datos para compartir: "+info[9]+" MB";
            }
            textAreaConsumo.setText(text);
        } else{
            String text="";
            for(int j=0; j<infoV.size(); j++) {
                String[] info = infoV.get(j);
                int monthI = Integer.parseInt(info[13].substring(5,7));
                String month = months[monthI-1];
                int plan = Integer.parseInt(infoPlan[0]);
                String name = infoPlan[1];

                text += "Plan tipo: "+plan+"\n"+"Nombre cliente: "+name+"\n"+"Mes: "+month+"\n";

                text += "Linea número: "+info[12]+"\n"
                        + "Consumo de datos: "+info[1] + " MB\n"
                        + "Consumo de minutos: "+info[2]+" Minutos\n"
                        + "Consumo de mensajes: "+info[3]+" SMS\n"
                        + "Consumo de datos de Whatsapp: "+info[4]+" MB\n"
                        + "Consumo de minutos de Whatsapp: "+info[5]+" Minutos\n"
                        + "Consumo de datos de Facebook: "+info[6]+" MB\n";

                if(plan == 4){
                    text += "Consumo de datos de Waze: "+info[7] +" MB";
                } else if(plan == 5){
                    text += "Consumo minutos internacionales: "+info[8]+" Minutos\n"
                            + "Consumo de datos para compartir: "+info[9]+" MB";
                }
                text += "\n";
            }
            textAreaConsumo.setText(text);
        }

    }

}
