package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("B-TRACKER");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("/bug.png"));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(false);
        mainStage = primaryStage;
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        //primaryStage.show();

        root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Stage stage = new Stage();
        stage.initOwner(Main.mainStage);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/bug.png"));
        stage.setTitle("Авторизация");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
