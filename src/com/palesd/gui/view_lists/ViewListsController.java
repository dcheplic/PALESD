/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.view_lists;

import com.palesd.database.Database;
import com.palesd.models.Guest;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class ViewListsController implements Initializable {

    @FXML private Button cancelButton;
    @FXML private ListView<String> eventList;
    @FXML private TableColumn<Guest, String> nameCol;
    @FXML private TableColumn<Guest, String> numberCol;
    @FXML private TableView eventGuestTable;
    
    @FXML
    private void handleCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleSelectedEventAction() {
        eventGuestTable.getItems().setAll(createGuestList(eventList.getSelectionModel().getSelectedItem()));
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
    }    
    
    private List<String> createEventList(String identifier) {
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
    
    private List<Guest> createGuestList(String eventName) {
        List<Guest> guestList = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuests(eventName);
            while(rs.next()) {
                Guest guest = new Guest(rs.getString("name"), rs.getString("titanCard"));
                guestList.add(guest);
            }
        } catch (SQLException ex) {
        }
        return guestList;
    }

    public void setIdentifier(String identifier) {
        eventList.getItems().setAll(createEventList(identifier));
    }
    
    
    
}
