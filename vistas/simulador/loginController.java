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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import DB_Connection.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    private DBConnection connection= new DBConnection("", "", "", "", "", "");

    @FXML
    JFXTextField TFpnumber;
    @FXML
    JFXButton BTsimular;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void number_sim(ActionEvent e) throws Password_exception, IOException {

        String number = TFpnumber.getText();
        System.out.println(number);
        Object[] objectResult = connection.read_DB("SELECT * from lines " +
                "WHERE number = '" + number + "';");

        if (objectResult[0].equals("Error")) {
            String error = (String) objectResult[1];
            throw new Password_exception(error);
        } else {
            BTsimular.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulador.fxml"));

            Parent root = (Parent) loader.load();
            Stage primaryStage = new Stage();

            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("");

            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            Sim_Controller controller = loader.getController();
            controller.set_number(number);
        }
    }
}
