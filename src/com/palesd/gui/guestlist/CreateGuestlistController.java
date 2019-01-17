/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.guestlist;

import com.palesd.database.Database;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class CreateGuestlistController implements Initializable {
    
    @FXML private Button addGuestButton;
    @FXML private Button deleteGuestButton;
    @FXML private Button exitButton;
    @FXML private ListView<String> guestList;
    @FXML private TextField nameField;
    
    private String eventName;
    private String selectedName;
    private String styleSheet;
    
    @FXML
    private void handleAddGuestButtonAction() {
        if(!nameField.getText().trim().isEmpty())
            Database.insert(eventName, nameField.getText(), "");
        
        guestList.getItems().setAll(createNameList(eventName));
        nameField.clear();
    }
    
    @FXML
    private void handleDeleteButtonAction() {
            Database.deleteRow(eventName, selectedName);
            guestList.getItems().setAll(createNameList(eventName));
    }
    
    @FXML
    private void handleSelectedEventAction() {
        selectedName = guestList.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void onEnter(ActionEvent ae) {
        handleAddGuestButtonAction();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    private List<String> createNameList(String eventName) {
        List<String> guestListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuests(eventName);
            while(rs.next()) {
                guestListLoc.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
        }
        return guestListLoc;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
    
}
