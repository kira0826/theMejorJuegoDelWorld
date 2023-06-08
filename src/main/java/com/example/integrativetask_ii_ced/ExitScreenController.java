package com.example.integrativetask_ii_ced;

import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import com.example.integrativetask_ii_ced.model.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ExitScreenController {

    @FXML
    private Button exitBtn;

    @FXML
    private Button replayBtn;

    @FXML
    public void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void play(ActionEvent event) {
        HelloApplication.openWindow("startingPage.fxml");
        HelloController.character = new Player(0,0, 60,60,3);
        HelloController.levels = new ArrayList<>();
        closeWindow(event);
    }

    @FXML
    private void closeWindow(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();


    }
}
