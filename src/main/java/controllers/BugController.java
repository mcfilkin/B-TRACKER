package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.UpdateOptions;
import info.Bug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mongo.WorkWithMongo;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Optional;

public class BugController {
    public static Bug BUG;

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
        //Document doc = new Document();
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
        id.getScene().getWindow().hide();
    }
}
