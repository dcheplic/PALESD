/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.main;

import com.palesd.gui.event_start.EventStartController;
import com.palesd.gui.view_lists.DeleteListsController;
import com.palesd.gui.view_lists.ViewListsController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class AttendanceModeController implements Initializable {
    
    @FXML private Button deletePastAttendanceEventButton;
    @FXML private Button returnToMainMenuButton;
    @FXML private Button startAttendanceEventButton;
    @FXML private Button viewPastAttendanceEventButton;
    @FXML private TextArea buttonDescriptionTextArea;
    
    private String styleSheet;
    
    @FXML
    private void handleDeletePastAttendanceEventButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/view_lists/DeleteLists.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            DeleteListsController controller = loader.<DeleteListsController>getController();
            controller.setIdentifier("_att");
            stage.show();
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleDeletePastAttendanceEventButtonHold() {
        buttonDescriptionTextArea.setText("View and delete previously created lists.");
    }
    
    @FXML
    private void handleHoldRelease() {
        buttonDescriptionTextArea.clear();
    }
    
    @FXML
    private void handleReturnToMainMenuButtonAction() {
        Stage stage = (Stage) returnToMainMenuButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleReturnToMainMenuButtonHold() {
        buttonDescriptionTextArea.setText("Return to the main menu.");
    }
    
    @FXML
    private void handleStartAttendanceEventButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/event_start/EventStart.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Stage stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            EventStartController controller = loader.<EventStartController>getController();
            controller.setIdentifier("_att");
            stage.show();
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleStartAttendanceEventButtonHold() {
        buttonDescriptionTextArea.setText("Name and create a new attendance list, and take attendance.");
    }
    
    @FXML
    private void handleViewPastAttendanceEventButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/view_lists/ViewLists.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            ViewListsController controller = loader.<ViewListsController>getController();
            controller.setIdentifier("_att");
            stage.show();
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleViewPastAttendanceEventButtonHold() {
        buttonDescriptionTextArea.setText("View previously created lists.");
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
