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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class ViewClubListsController implements Initializable {
    
    @FXML private Button exitButton;
    @FXML private Button viewReportButton;
    @FXML private ListView<String> clubList;
    @FXML private ListView<String> meetingList;
    
    private String selectedClub;
    private String selectedMeeting;
    private String styleSheet;
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handleSelectedClubAction() {
        selectedClub = clubList.getSelectionModel().getSelectedItem();
        meetingList.getItems().setAll(createClubList(selectedClub.substring(0, selectedClub.length()-4) + "%_clumet"));
    }
    
    @FXML
    private void handleSelectedMeetingAction() {
        selectedMeeting = meetingList.getSelectionModel().getSelectedItem();
        System.out.println(selectedMeeting);
    }
    
    @FXML
    private void handleViewReportButtonAction() {
        try {
                URL url = new File("src/com/palesd/gui/meeting/MeetingReport.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                scene.getStylesheets().add(styleSheet);
                MeetingReportController controller = loader.<MeetingReportController>getController();
                controller.setMeetingName(selectedMeeting);
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
        clubList.getItems().setAll(createClubList("_clu"));
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
    
    private List<String> createClubList(String identifier) {
        List<String> eventListL = new ArrayList();
        try {
            ResultSet rs = Database.selectAllTables(identifier);
            while(rs.next()) {
                eventListL.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
        }
        return eventListL;
    }
    
}
