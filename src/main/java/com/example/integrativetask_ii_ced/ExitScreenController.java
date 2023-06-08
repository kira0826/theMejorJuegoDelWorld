package com.example.integrativetask_ii_ced;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    }

}
