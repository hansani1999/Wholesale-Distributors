import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
       /* URL resource = getClass().getResource("views/MainForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);
       // primaryStage.setMaximized(true);
        primaryStage.show();*/

        URL resource = getClass().getResource("views/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        // primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
