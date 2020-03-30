import DB_Connection.DBConnection;
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
    //****

    @FXML
    public void log_in(ActionEvent event) throws Exception {

        try
        {
            int id_user = Integer.parseInt(tf_user.getText());
            Vector<String[]> result = (Vector) conection.read_DB("SELECT password, roleID from Users WHERE id =" + id_user)[1];
            String user_password = result.get(0)[0];
            System.out.println(user_password);

            //password entered by user
            String password_entered = tf_pass.getText();
            try
            {
                if (password_entered.equals(user_password) && event.getSource().equals(btn_login))
                {
                    btn_login.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
                    Parent root = (Parent) loader.load();

                    Stage primaryStage = new Stage();
                    primaryStage.initStyle(StageStyle.TRANSPARENT);
                    primaryStage.setTitle("");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                    Controller controller = loader.getController();
                    controller.set_user_role(Integer.parseInt(result.get(0)[1]));
                }
                else
                {
                    throw new Password_exception("Contraseña incorrecta");
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                tf_user.setText("");
                tf_pass.setText("");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "ID incorrecto");
            tf_user.setText("");
            tf_pass.setText("");
        }
    }
}