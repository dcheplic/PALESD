/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.meeting;

import com.palesd.database.Database;
import com.palesd.gui.main.MainMenu;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class AddClubController implements Initializable {
    
    @FXML private Button addClubBitton;
    @FXML private Button exitButton;
    @FXML private TextField clubNameField;
    
    private String identifier;
    private String styleSheet;
    
    @FXML
    private void handleAddClubButtonAction() {
        if (!clubNameField.getText().equals("")) {
            try {
                Database.createTable(clubNameField.getText() + identifier);
                URL url = new File("src/com/palesd/gui/meeting/EditClub.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                scene.getStylesheets().add(styleSheet);
                EditClubController controller = loader.<EditClubController>getController();
                MainMenu.pushAndSetScene(scene);
            } catch (IOException ex) {
            }
            Stage stage = (Stage) addClubBitton.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
     @FXML
    private void onEnter(ActionEvent ae) {
        handleAddClubButtonAction();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
}
