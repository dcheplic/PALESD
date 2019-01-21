/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.main;

import com.palesd.gui.meeting.AddClubController;
import com.palesd.gui.meeting.TakeAttendanceStartController;
import com.palesd.gui.meeting.ViewClubListsController;
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
public class MeetingModeController implements Initializable {
    
    @FXML  private Button addClubButton;
    @FXML private Button deleteClubButton;
    @FXML private Button editClubButton;
    @FXML private Button returnToMainMenuButton;
    @FXML private Button takeMeetingAttendanceButton;
    @FXML private Button viewClubListsButton;
    @FXML private TextArea buttonDescriptionTextArea;
    
    private String identifier;
    private String styleSheet;
    
    @FXML
    private void handleAddClubButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/meeting/AddClub.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("AddClub");
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            AddClubController controller = loader.<AddClubController>getController();
            controller.setIdentifier("_clu");
            controller.setStyleSheet(styleSheet);
            stage.show();
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleAddClubButtonHold() {
        buttonDescriptionTextArea.setText("Create a new club member list.");
    }

    @FXML
    private void handleDeleteClubButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/meeting/DeleteClub.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleDeleteClubButtonHold() {
        buttonDescriptionTextArea.setText("View and delete a previously created club member list.");
    }
    
    @FXML
    private void handleHoldRelease() {
        buttonDescriptionTextArea.clear();
    }

    @FXML
    private void handleReturnToMainMenuButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handleReturnToMainMenuButtonHold() {
        buttonDescriptionTextArea.setText("Return to the main menu.");
    }

    @FXML
    private void handleEditClubButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/meeting/EditClub.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleEditClubButtonHold() {
        buttonDescriptionTextArea.setText("Edit a previously created club member list.");
    }

    @FXML
    private void handleTakeMeetingAttendanceButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/meeting/TakeAttendanceStart.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Attendance Start");
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            TakeAttendanceStartController controller = loader.<TakeAttendanceStartController>getController();
            controller.setStyleSheet(styleSheet);
            stage.show();
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleTakeMeetingAttendanceButtonHold() {
        buttonDescriptionTextArea.setText("Take club meeting attendance while referencing a club member list.");
    }

    @FXML
    private void handleViewClubListsButtonAction() {
        try {
            URL url = new File("src/com/palesd/gui/meeting/ViewClubLists.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            ViewClubListsController controller = loader.<ViewClubListsController>getController();
            controller.setStyleSheet(styleSheet);
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleViewClubListsButtonHold() {
        buttonDescriptionTextArea.setText("View previously created club member lists.");
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
