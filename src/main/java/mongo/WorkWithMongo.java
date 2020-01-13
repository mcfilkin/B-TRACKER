package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import controllers.LoginController;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import info.Bug;
import info.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkWithMongo {
    private static MongoClient mongoClient;
    private static DBCollection table;
    private static boolean authenticate;
    private static MongoDatabase db;

    public static void connect() {
        if (mongoClient == null) {
            MongoClientURI uri = new MongoClientURI(
                    "mongodb+srv://admin:admin@cluster0-kexk2.mongodb.net");
            mongoClient = new MongoClient(uri);
            db = mongoClient.getDatabase("btracker");
        }
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static void authorize(String login, String password) throws IncorrectPasswordException, UserNotFoundException {
        BasicDBObject query = new BasicDBObject();
        query.put("email", login);
        FindIterable<Document> result = db.getCollection("users").find(query);
        List<Document> list = new ArrayList<>();
        for (Document a : result) {
            list.add(a);
        }

        if (!list.isEmpty()) {
            if (!password.equals(result.first().get("password"))) {
                throw new IncorrectPasswordException();
            } else {
                LoginController.username = list.get(0).get("user").toString();
            }
        } else {
            throw new UserNotFoundException();
        }

    }

    public static ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        MongoCollection<Document> a = db.getCollection("users");
        MongoCursor<Document> cur = a.find().cursor();
        while (cur.hasNext()) {
            User user = new User();
            Document c = cur.next();
            user.setId(c.get("_id").toString());
            user.setLogin(c.get("user").toString());
            user.setEmail(c.get("email").toString());
            users.add(user);
        }
        return users;
    }

    public static ObservableList<Bug> getShortInfo() {
        MongoCollection<Document> a = db.getCollection("bugs");
        ObservableList<Bug> bugs = FXCollections.observableArrayList();
        MongoCursor<Document> cur = a.find().cursor();
        while (cur.hasNext()) {
            Bug bug = new Bug();
            Document c = cur.next();
            bug.setShortDescription(new SimpleStringProperty(c.get("short_description").toString()));
            bug.setFullDescription(new SimpleStringProperty(c.get("full_description").toString()));
            bug.setStepsToReproduce(new SimpleStringProperty(c.get("steps_to_reproduce").toString()));
            bug.setId(new SimpleStringProperty(c.get("_id").toString()));
            bug.setCreate(new SimpleStringProperty(c.get("create").toString()));
            bug.setUpdate(new SimpleStringProperty(c.get("update").toString()));
            bug.setStatus(new SimpleStringProperty(c.get("status").toString()));
            bug.setReproducibility(new SimpleStringProperty(c.get("reproducibility").toString()));
            bug.setComment(new SimpleStringProperty(c.get("comment").toString()));
            bug.setSeverity(new SimpleStringProperty(c.get("severity").toString()));
            bug.setPriority(new SimpleStringProperty(c.get("priority").toString()));
            bugs.add(bug);
        }
        return bugs;
    }

}
