/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.guestlist;

import com.palesd.database.Database;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class GuestlistStartController implements Initializable {
    
    private final String identifier = "_guat";
    
    @FXML private Button beginButton;
    @FXML private Button cancelButton;
    @FXML private ListView<String> eventList;
    @FXML private TextField eventNameField;
    @FXML private TextField guestListNameField;
    
    private String selectedEvent;
    private String styleSheet;
    
    @FXML
    private void handleBeginButtonAction() {
        if(!eventNameField.getText().equals("")) {
            try {
                Database.createTable(eventNameField.getText() + identifier);
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Guestlist.fxml"));
                Stage stage = new Stage(StageStyle.UNDECORATED);
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);
                GuestlistController controller = loader.<GuestlistController>getController();
                controller.setGuestlistName(selectedEvent);
                controller.setEventName(eventNameField.getText() + identifier);
                stage.show();
            } catch (IOException ex) {
            }
        
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void handleCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleSelectedEventAction() {
        selectedEvent = eventList.getSelectionModel().getSelectedItem();
        eventNameField.setText(selectedEvent.substring(0, selectedEvent.length()-4));
        guestListNameField.setText(selectedEvent);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventList.getItems().setAll(createEventList());
    }
    
    private List<String> createEventList() {
        List<String> eventListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllTables("_gue");
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
