/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.meeting;

import com.palesd.gui.main.MainMenu;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class TakeAttendanceStartController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private Button beginButton;
    
    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleBeginButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/meeting/Meeting.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
        }
        Stage stage = (Stage) beginButton.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
