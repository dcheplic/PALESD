/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.event_start;

import com.palesd.database.Database;
import com.palesd.gui.attendance.AttendanceController;
import com.palesd.gui.guestlist.CreateGuestlistController;
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
public class EventStartController implements Initializable {
    
    @FXML private Button beginButton;
    @FXML private Button exitButton;
    @FXML private TextField eventNameField;
    
    private String identifier;
    private String styleSheet;
    
    @FXML
    private void handleBeginButtonAction() {
        if(identifier.equals("_att"))
            attedanceEventStartHelper();
       else if (identifier.equals("_gue"))
           guestListCreationStartHelper();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
     @FXML
    private void onEnter(ActionEvent ae) {
        handleBeginButtonAction();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    private void attedanceEventStartHelper() {
        if (!eventNameField.getText().equals("")) {
            try {
                Database.createTable(eventNameField.getText() + identifier);
                //Database.createTable("Master List");
                URL url = new File("src/com/palesd/gui/attendance/Attendance.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                scene.getStylesheets().add(styleSheet);
                AttendanceController controller = loader.<AttendanceController>getController();
                controller.setEventName(eventNameField.getText() + identifier);
                MainMenu.pushAndSetScene(scene);
            } catch (IOException ex) {
            }
        }
    }
    
    private void guestListCreationStartHelper() {
        if(!eventNameField.getText().equals("")) {
            try {
                Database.createTable(eventNameField.getText() + identifier);
                
                URL url = new File("src/com/palesd/gui/guestlist/CreateGuestlist.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                scene.getStylesheets().add(styleSheet);
                CreateGuestlistController controller = loader.<CreateGuestlistController>getController();
                controller.setEventName(eventNameField.getText() + identifier);
                controller.setStyleSheet(styleSheet);
                MainMenu.pushAndSetScene(scene);
            } catch (IOException ex) {
            }
        }
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
}
