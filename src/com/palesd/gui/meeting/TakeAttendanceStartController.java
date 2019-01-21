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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class TakeAttendanceStartController implements Initializable {

    @FXML private Button exitButton;
    @FXML private Button beginButton;
    @FXML private ListView<String> clubList;
    
    private final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    private String clubName;
    private String styleSheet;
    
    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleBeginButtonAction() {
        Date date = new Date();
        try {
            Database.createTable(clubName.substring(0, clubName.length()-4) + " Meeting " + sdf.format(date) + "_clumet");
            URL url = new File("src/com/palesd/gui/meeting/Meeting.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().add(styleSheet);
            MeetingController controller = loader.<MeetingController>getController();
            controller.setClubTableName(clubName.substring(0, clubName.length()-4) + " Meeting " + sdf.format(date) + "_clumet");
            controller.setClubName(clubName);
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
        }
        Stage stage = (Stage) beginButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleSelectedClubAction() {
        clubName = clubList.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clubList.getItems().setAll(createClubList());
    }
    
    private List<String> createClubList() {
        List<String> eventListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllTables("_clu");
            while(rs.next()) {
                eventListLoc.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
        }
        return eventListLoc;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
    
    
    
}
