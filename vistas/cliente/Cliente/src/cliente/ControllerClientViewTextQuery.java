package cliente;

import DB_Connection.DBConnection;
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
    private DBConnection connection = new DBConnection("", "", "", "", "", "");
    

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

    public void setInfo(Vector<String[]> infoV, String infoPlan[], int type) {
        if (type == 1) {
            String text = infoQuery(infoV, infoPlan, 0);
            textAreaConsumo.setText(text);
        } else {
            String text = "";
            for (int j = 0; j < infoV.size(); j++) {
                text += infoQuery(infoV, infoPlan, j);
            }
            textAreaConsumo.setText(text);

        }


    }

    public String infoQuery(Vector<String[]> infoV, String infoPlan[], int pos){
        String text ="";
        String[] info = infoV.get(pos);
        float data_consumption = Float.parseFloat(info[1]);
        int minutes_consumption = Integer.parseInt(info[2]);
        int sms_consumption = Integer.parseInt(info[3]);
        float data_wpp = Float.parseFloat(info[4]);
        int minutes_wpp = Integer.parseInt(info[5]);
        float data_fb = Float.parseFloat(info[6]);
        float data_waze = Float.parseFloat(info[7]);
        int minutes_international = Integer.parseInt(info[8]);
        float data_shared = Float.parseFloat(info[9]);

        Object[] resultO = connection.read_DB("SELECT minutes, dataplan, messages, data_wpp, minutes_wpp, data_fb, data_waze, minutes_international, data_shared " +
                "FROM Plan,Lines WHERE lines.number='"+info[13]+"' AND planID = id; ");
        Vector<String[]> result = (Vector<String[]>) resultO[1];
        String[] newResult = result.get(0);

        if(Integer.parseInt(info[12]) != 0){
            data_consumption = 2 * Integer.parseInt(newResult[1])-data_consumption;
            minutes_consumption = 2 * Integer.parseInt(newResult[0])-minutes_consumption;
            sms_consumption = 2 * Integer.parseInt(newResult[2])-sms_consumption;
            data_wpp = 2 * Integer.parseInt(newResult[3])-data_wpp;
            minutes_wpp = 2 * Integer.parseInt(newResult[4])-minutes_wpp;
            data_fb = 2 * Integer.parseInt(newResult[5])-data_fb;
            data_waze = 2 * Integer.parseInt(newResult[6])-data_waze;
            minutes_international = 2 * Integer.parseInt(newResult[7])-minutes_international;
            data_shared = 2 * Integer.parseInt(newResult[8])-data_shared;
        } else{
            data_consumption = Integer.parseInt(newResult[1])-data_consumption;
            minutes_consumption = Integer.parseInt(newResult[0])-minutes_consumption;
            sms_consumption = Integer.parseInt(newResult[2])-sms_consumption;
            data_wpp = Integer.parseInt(newResult[3])-data_wpp;
            minutes_wpp = Integer.parseInt(newResult[4])-minutes_wpp;
            data_fb = Integer.parseInt(newResult[5])-data_fb;
            data_waze = Integer.parseInt(newResult[6])-data_waze;
            minutes_international = Integer.parseInt(newResult[7])-minutes_international;
            data_shared = Integer.parseInt(newResult[8])-data_shared;
        }
        int monthI = Integer.parseInt(info[14].substring(5,7));
        String month = months[monthI-1];
        int plan = Integer.parseInt(infoPlan[0]);
        String name = infoPlan[1];
        text +="_______________________________________________________________\n" +
                "|                          Plan tipo                              |                    "+plan+"\n" +
                "_______________________________________________________________\n" +
                "|                        Nombre cliente                     |           "+name+"\n" +
                "_______________________________________________________________\n" +
                "|                               Mes                                 |                  "+month+"\n" +
                "_______________________________________________________________\n" +
                "|                       Linea número                         |          "+info[13]+"\n" +
                "_______________________________________________________________\n" +
                "|                    Consumo de datos                    |            "+data_consumption+" MB\n" +
                "_______________________________________________________________\n" +
                "|                    Consumo de minutos                |           "+minutes_consumption+" Minutos\n" +
                "_______________________________________________________________\n" +
                "|                   Consumo de mensajes              |               "+sms_consumption+" SMS\n" +
                "_______________________________________________________________\n" +
                "|       Consumo de datos de Whatsapp         |            "+data_wpp+" MB\n" +
                "_______________________________________________________________\n" +
                "|      Consumo de minutos de Whatsapp      |           "+minutes_wpp+" Minutos\n" +
                "_______________________________________________________________\n" +
                "|       Consumo de datos de Facebook         |            "+data_fb+" MB\n" +
                "_______________________________________________________________\n";
        if(plan == 4){
            text += "|             Consumo de datos de Waze           |         "+data_waze+" MB\n" +
                    "_______________________________________________________________\n";
        } else if(plan == 5){
            text += "|             Consumo de datos de Waze           |         "+data_waze+" MB\n" +
                    "_______________________________________________________________\n"+
                    "|     Consumo minutos internacionales       |           "+minutes_international+" Minutos\n" +
                    "_______________________________________________________________\n"+
                    "|     Consumo de datos para compartir       |              "+data_shared+" MB\n" +
                    "_______________________________________________________________\n";
        }
        return text;
    }

}
