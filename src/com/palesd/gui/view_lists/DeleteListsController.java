/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.view_lists;

import com.palesd.database.Database;
import com.palesd.gui.main.MainMenu;
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

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class DeleteListsController implements Initializable {
    
    @FXML private Button exitButton;
    @FXML private Button deleteButton;
    @FXML private ListView<String> eventList;
    @FXML private TableColumn<Guest, String> firstNameCol;
    @FXML private TableColumn<Guest, String> lastNameCol;
    @FXML private TableColumn<Guest, String> numberCol;
    @FXML private TableColumn<Guest, String> timeCol;
    @FXML private TableView eventGuestTable;
    
    private String identifier;
    private String selectedEvent;
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handleDeleteButtonAction() {
            Database.deleteTable(selectedEvent);
            if(selectedEvent.contains("_gue"))
                Database.deleteTable(selectedEvent.substring(0, selectedEvent.length()-4) + "_guat");
            if(selectedEvent.contains("_guat"))
                Database.deleteTable(selectedEvent.substring(0, selectedEvent.length()-5) + "_gue");
            eventList.getItems().setAll(createEventList(identifier));
            eventGuestTable.getItems().clear();
    }
    
    @FXML
    private void handleSelectedEventAction() {
        selectedEvent = eventList.getSelectionModel().getSelectedItem();
        eventGuestTable.getItems().setAll(createGuestList(selectedEvent));
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        //eventList.getItems().setAll(createEventList());
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
                Guest guest = new Guest(rs.getString("firstName"), rs.getString("lastName"), rs.getInt("titanCard"), rs.getString("time"));
                guestList.add(guest);
            }
        } catch (SQLException ex) {
        }
        return guestList;
    }

    public void setIdentifier(String identifier) {
        eventList.getItems().setAll(createEventList(identifier));
        this.identifier = identifier;
    }
    
}
