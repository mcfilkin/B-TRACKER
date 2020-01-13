package controllers;

import com.mongodb.BasicDBObject;
import info.Bug;
import info.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mongo.WorkWithMongo;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

public class BugController {
    public static Bug BUG;

    @FXML
    private Label fixLabel;

    @FXML
    private ComboBox fixBox;

    @FXML
    private CheckBox send;

    @FXML
    private Label id;

    @FXML
    private Label create;

    @FXML
    private Label update;

    @FXML
    private TextField short_description;

    @FXML
    private TextArea full_description;

    @FXML
    private TextArea comment;

    @FXML
    private TextArea steps;

    @FXML
    private ChoiceBox reproduce;

    @FXML
    private ChoiceBox severity;

    @FXML
    private ChoiceBox priority;

    @FXML
    private ChoiceBox status;

    @FXML
    void initialize() {
        ObservableList<String> rep = FXCollections.observableArrayList("Always", "Sometimes");
        reproduce.setItems(rep);

        ObservableList<String> sev = FXCollections.observableArrayList("Critical", "Major", "Medium", "Minor");
        severity.setItems(sev);

        ObservableList<String> pri = FXCollections.observableArrayList("Urgent", "High", "Normal", "Low");
        priority.setItems(pri);

        ObservableList<String> sta = FXCollections.observableArrayList("In progress", "Close", "Open", "Fixed", "Reopened");
        status.setItems(sta);

        id.setText("ID: " + BUG.getId().getValue());
        short_description.setText(BUG.getShortDescription().getValue());
        full_description.setText(BUG.getFullDescription().getValue());
        steps.setText(BUG.getStepsToReproduce().getValue());
        reproduce.setValue(rep.get(rep.indexOf(BUG.getReproducibility().getValue())));
        severity.setValue(sev.get(sev.indexOf(BUG.getSeverity().getValue())));
        priority.setValue(pri.get(pri.indexOf(BUG.getPriority().getValue())));
        comment.setText(BUG.getComment().getValue());
        create.setText("Открыт: " + BUG.getCreate().getValue());
        update.setText("Изменён: " + BUG.getUpdate().getValue());
        status.setValue(sta.get(sta.indexOf(BUG.getStatus().getValue())));
    }

    @FXML
    void choise() {
        if (status.getValue().toString().equals("In progress")) {
            fixBox.setVisible(true);
            fixLabel.setVisible(true);
            send.setVisible(true);
            ObservableList<User> users = WorkWithMongo.getUsers();
            ObservableList<String> names = FXCollections.observableArrayList();
            for (User user : users) {
                names.add(user.getLogin());
            }
            fixBox.setItems(names);
        } else {
            fixBox.setVisible(false);
            fixLabel.setVisible(false);
            send.setVisible(false);
        }
    }

    @FXML
    void deleteBug() {
        showConfirmation();
    }

    private void showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление дефекта");
        alert.setHeaderText("Вы действительно хотите удалить дефект из системы? Эта операция не может быть отменена.");
        alert.setContentText("Совет: если вы хотите просто закрыть дефект, переведите его статус в 'Close'");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/bug.png"));
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            WorkWithMongo.getMongoClient().getDatabase("btracker").getCollection("bugs").deleteOne(new Document("_id", new ObjectId(BUG.getId().getValue())));
            id.getScene().getWindow().hide();
        }
    }

    @FXML
    public void updateInfo() {
        BasicDBObject doc = new BasicDBObject();
        doc.put("short_description", short_description.getText());
        doc.put("full_description", full_description.getText());
        doc.put("steps_to_reproduce", steps.getText());
        doc.put("comment", comment.getText());
        doc.put("reproducibility", reproduce.getValue().toString());
        doc.put("severity", severity.getValue().toString());
        doc.put("priority", priority.getValue().toString());
        doc.put("update", new Date());
        doc.put("status", status.getValue().toString());
        WorkWithMongo.getMongoClient().getDatabase("btracker").getCollection("bugs").updateOne(new Document("_id", new ObjectId(BUG.getId().getValue())), new Document("$set", doc));
        short_description.getScene().getWindow().hide();
        if (send.isSelected()) {
            setMember();
        }
        //short_description.getScene().getWindow().hide();
    }

    public void setMember() {
        final String username = "mcfilkin2@outlook.com";  // like yourname@outlook.com
        final String password = "evropaava2";   // password here

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("mcfilkin@gmail.com"));   // like inzi769@gmail.com
            message.setSubject("Дефект #" + BUG.getId().getValue() + " назначен на вас");
            message.setText("Краткое описание: " + short_description.getText() + "\n\nПодробное описание:\n" + full_description.getText() + "\n\nПодробная информация доступна в приложении B-TRACKER");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
