import DB_Connection.DBConnection;
import Exceptions.EmptyFieldException;
import Exceptions.Password_exception;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.*;
import java.util.Vector;

public class login
{
    //Database connection
    private DBConnection conection= new DBConnection("", "", "", "", "", "");

    //Login interface
    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXTextField tf_user;
    @FXML
    private JFXPasswordField tf_pass;

    @FXML
    public void log_in(ActionEvent event) throws Exception {
        try {

            String password_entered = tf_pass.getText();
            if(tf_user.getText().equals("")||password_entered.equals("")){
                throw new EmptyFieldException("Debes llenar todos los campos");
            }
            int id_user = Integer.parseInt(tf_user.getText());
            Object[] objectResult = conection.read_DB("SELECT password, roleID from Users " +
                    "WHERE id = '"+id_user+"';");

            if(objectResult[0].equals("Error")){
                String error= (String) objectResult[1];
                throw new Password_exception(error);
            }
            Vector<String[]> result = (Vector<String[]>) objectResult[1];
            String user_password = result.get(0)[0];

            password_entered = tf_pass.getText();
            try {
                if (password_entered.equals(user_password) && event.getSource().equals(btn_login)) {
                    btn_login.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainUI.fxml"));

                    Parent root = (Parent) loader.load();
                    Stage primaryStage = new Stage();

                    primaryStage.initStyle(StageStyle.TRANSPARENT);
                    primaryStage.setTitle("");

                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();

                    Controller controller = loader.getController();
                    controller.set_user_role(Integer.parseInt(result.get(0)[1]));
                    controller.set_user_id(id_user);
                }
                else {
                    throw new Password_exception("Contraseña incorrecta");
                }
            } catch (Password_exception ex) {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                tf_user.setText("");
                tf_pass.setText("");
            }
        } catch (EmptyFieldException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
                tf_pass.setText("");
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Usuario debe ser numerico");
            tf_user.setText("");
            tf_pass.setText("");
        }catch (Password_exception e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sesion:\n"
                    + e.getMessage());
            tf_user.setText("");
            tf_pass.setText("");
        }
    }
}