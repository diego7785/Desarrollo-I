import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//docker run --name postgres -p 5432:5432 --hostname=postgres --network=pgnetwork -e POSTGRES_PASSWORD=pg123 -d postgres
//docker run -p 80:80 --name pgadmin4 \
//    -e 'PGADMIN_DEFAULT_EMAIL=yo@domain.com' \
//    -e 'PGADMIN_DEFAULT_PASSWORD=pg123' \
//    --network=pgnetwork \
//    -d dpage/pgadmin4

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        //--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Parent logged =FXMLLoader.load(getClass().getResource("mainUI.fxml"));
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
