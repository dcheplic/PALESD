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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class ViewClubListsController implements Initializable {
    
    @FXML
    private Button exitButton;
    @FXML
    private Button viewReportButton;
    
    private String styleSheet;
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handleViewReportButtonAction() {
        try {
                URL url = new File("src/com/palesd/gui/meeting/MeetingReport.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                scene.getStylesheets().add(styleSheet);
                MainMenu.pushAndSetScene(scene);
            } catch (IOException ex) {
            }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
    
}
