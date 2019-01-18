package com.palesd.gui.meeting;

import com.palesd.gui.main.MainMenu;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MeetingController implements Initializable {
    
    @FXML
    private Button exitButton;
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
