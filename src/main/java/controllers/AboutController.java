package controllers;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutController {

    @FXML
    void hyperlinkGit() throws IOException, URISyntaxException {
        URI uri = new URI("https://github.com/mcfilkin/BTRACKER");
        Desktop.getDesktop().browse(uri);
    }

    @FXML
    void hyperlinkTel() throws IOException, URISyntaxException {
        URI uri = new URI("https://t.me/max_fil");
        Desktop.getDesktop().browse(uri);
    }

}
