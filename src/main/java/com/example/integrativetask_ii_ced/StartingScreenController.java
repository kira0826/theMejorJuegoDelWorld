package com.example.integrativetask_ii_ced;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartingScreenController {

    @FXML
    private Button playBtn;

    @FXML
    void play(ActionEvent event) {
        HelloApplication.openWindow("hello-view.fxml");
        closeWindow(event);
    }

    @FXML
    private void closeWindow(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();


    }

}
