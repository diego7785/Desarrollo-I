package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Parent logged =FXMLLoader.load(getClass().getResource("sample.fxml"));
        //primaryStage.initStyle(StageStyle.TRANSPARENT);

        Scene login = new Scene(root);

        primaryStage.setTitle("Log In");


        primaryStage.setScene(login);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
