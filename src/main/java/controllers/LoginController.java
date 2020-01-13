package controllers;

import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Main;
import mongo.WorkWithMongo;
import windows.NotificationServise;

import java.awt.*;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button loginButton;

    @FXML
    private Label sux;

    @FXML
    void initialize() throws IOException {
        WorkWithMongo.connect();

    }

    @FXML
    void login() throws IOException, AWTException {
        if (loginText.getText().equals("")) {
            sux.setVisible(true);
            sux.setText("Поле 'Логин' обязательно к заполнению!");
            return;
        }

        try {
            WorkWithMongo.authorize(loginText.getText(), passwordText.getText());
        } catch (IncorrectPasswordException e) {
            sux.setVisible(true);
            sux.setText("Неверный пароль!");
            return;
        } catch (UserNotFoundException e) {
            sux.setVisible(true);
            sux.setText("Пользователь '" + loginText.getText() + "' не найден!");
            return;
        }
        NotificationServise.showNotification("Авторизация в системе B-Tracker", "Вы вошли как " + loginText.getText());
        Stage stage = (Stage) sux.getScene().getWindow();
        stage.close();

        Main.mainStage.show();
//        sux.getScene().getWindow().hide();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/src/main/pages/main.fxml"));
//        loader.load();
//        Parent root = loader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.setTitle("B-Tracker: " + loginText.getText());
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                System.exit(0);
//            }
//        });
//
//        stage.show();
    }


}
