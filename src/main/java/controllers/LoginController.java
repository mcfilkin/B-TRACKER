package controllers;

import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import mongo.WorkWithMongo;
import windows.NotificationServise;

import java.awt.*;
import java.io.IOException;

public class LoginController {
    public static String username;
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
        NotificationServise.showNotification("Авторизация в системе B-Tracker", "Вы вошли как " + username);
        Stage stage = (Stage) sux.getScene().getWindow();
        stage.close();

        Main.mainStage.show();
    }


}
