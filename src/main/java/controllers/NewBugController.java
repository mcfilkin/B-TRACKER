package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mongo.WorkWithMongo;
import org.bson.Document;

import java.util.Date;

public class NewBugController {
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
    void initialize() {
        ObservableList<String> rep = FXCollections.observableArrayList("Always", "Sometimes");
        reproduce.setItems(rep);

        ObservableList<String> sev = FXCollections.observableArrayList("Critical", "Major", "Medium", "Minor");
        severity.setItems(sev);

        ObservableList<String> pri = FXCollections.observableArrayList("Urgent", "High", "Normal", "Low");
        priority.setItems(pri);
    }

    @FXML
    void addDefect() {
        Document doc = new Document();
        doc.put("short_description", short_description.getText());
        doc.put("full_description", full_description.getText());
        doc.put("steps_to_reproduce", steps.getText());
        doc.put("comment", comment.getText());
        doc.put("reproducibility", reproduce.getValue().toString());
        doc.put("severity", severity.getValue().toString());
        doc.put("priority", priority.getValue().toString());
        doc.put("create", new Date());
        doc.put("update", new Date());
        doc.put("status", "Open");
        WorkWithMongo.getMongoClient().getDatabase("btracker").getCollection("bugs").insertOne(doc);
        short_description.getScene().getWindow().hide();
    }

}
