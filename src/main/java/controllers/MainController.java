package controllers;

import info.Bug;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import main.Main;
import mongo.WorkWithMongo;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainController {
    @FXML
    private TableView table;
    @FXML
    private TableColumn<Bug, String> tableId;
    @FXML
    private TableColumn<Bug, String> defectId;
    @FXML
    private TableColumn<Bug, String> createId;
    @FXML
    private TableColumn<Bug, String> updateId;
    @FXML
    private TableColumn<Bug, String> statusId;

    @FXML
    void initialize() {
        WorkWithMongo.connect();

        Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        update();
                    }
                });
            }
        }, 0, 1000);
}

    @FXML
    void update() {
        ObservableList<Bug> people = WorkWithMongo.getShortInfo();
        table.setItems(people);
        table.setRowFactory( tv -> {
            TableRow<Bug> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Bug rowData = row.getItem();
                    BugController.BUG = rowData;
                    System.out.println(rowData);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/bug.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(Main.mainStage);
                    stage.getIcons().add(new Image("/bug.png"));
                    stage.setResizable(false);
                    stage.setTitle("Управление дефектом");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            });
            return row ;
        });
        tableId.setCellValueFactory(x->x.getValue().getId());
        defectId.setCellValueFactory(x->x.getValue().getShortDescription());
        createId.setCellValueFactory(x->x.getValue().getCreate());
        updateId.setCellValueFactory(x->x.getValue().getUpdate());
        statusId.setCellValueFactory(x->x.getValue().getStatus());
    }

    @FXML
    void exit() {
        System.exit(0);
    }

    @FXML
    void showAbout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/about.fxml"));
        Stage stage = new Stage();
        stage.setTitle("О программе");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.mainStage);
        stage.getIcons().add(new Image("/bug.png"));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void showNewBug() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/new_bug.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Новый дефект");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.mainStage);
        stage.getIcons().add(new Image("/bug.png"));
        stage.setResizable(false);
        stage.show();
    }
}
