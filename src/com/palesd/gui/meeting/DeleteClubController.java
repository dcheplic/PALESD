/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.meeting;

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
public class DeleteClubController implements Initializable {
    @FXML private Button exitButton;
    @FXML private Button deleteButton;
    @FXML private ListView<String> clubList;
    @FXML private TableColumn<Guest, String> firstNameCol;
    @FXML private TableColumn<Guest, String> lastNameCol;
    @FXML private TableColumn<Guest, String> numberCol;
    @FXML private TableView clubMemberTable;
    
    private String selectedClub;
    
    @FXML
    private void handleDeleteButtonAction() {
            Database.deleteTable(selectedClub);
            clubList.getItems().setAll(createClubList("_clu%"));
    }
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handleSelectedEventAction() {
        selectedClub = clubList.getSelectionModel().getSelectedItem();
        clubMemberTable.getItems().setAll(createMemberList(selectedClub));
        System.out.println(selectedClub);
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
        clubList.getItems().setAll(createClubList("_clu%"));
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
    
    private List<Guest> createMemberList(String eventName) {
        List<Guest> guestList = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuests(eventName);
            while(rs.next()) {
                Guest guest = new Guest(rs.getString("firstName"), rs.getString("lastName"), rs.getString("titanCard"));
                guestList.add(guest);
            }
        } catch (SQLException ex) {
        }
        return guestList;
    }
    
}
