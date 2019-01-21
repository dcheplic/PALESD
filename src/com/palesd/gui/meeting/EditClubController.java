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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class EditClubController implements Initializable {

    @FXML private Button addMemberButton;
    @FXML private Button deleteMemberButton;
    @FXML private Button exitButton;
    @FXML private ListView<String> clubList;
    @FXML private TableColumn firstNameCol;
    @FXML private TableColumn lastNameCol;
    @FXML private TableColumn numberCol;
    @FXML private TableView<Guest> clubMemberTable;
    @FXML private TextField nameField;
    @FXML private TextField cardField;
    @FXML private TextField delNameField;
    
    private Guest clubMember;
    private String clubName;
    
    @FXML
    private void handleAddMemberButtonAction() {
        String fixedNum = cardField.getText();
        if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?")))
            fixedNum = fixedNum.substring(4,10);
        if(!nameField.getText().trim().isEmpty() && !cardField.getText().trim().isEmpty()) {
            Database.insert(clubName, nameField.getText().split(" ")[0], nameField.getText().split(" ")[1], fixedNum);
            Database.insert("Master List", nameField.getText().split(" ")[0], nameField.getText().split(" ")[1], fixedNum);
        }
        
        clubMemberTable.getItems().setAll(createMemberList(clubName));
        nameField.clear();
        cardField.clear();
    }
    
    @FXML
    private void handleDeleteMemberButtonAction() {
        Database.deleteRow(clubName, clubMember.getFirstName());
        clubMemberTable.getItems().setAll(createMemberList(clubName));
        delNameField.clear();
    }
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handleSelectedClubAction() {
        clubName = clubList.getSelectionModel().getSelectedItem();
        clubMemberTable.getItems().setAll(createMemberList(clubName));
    }
    
    @FXML
    private void handleSelectedMemberAction() {
        clubMember = clubMemberTable.getSelectionModel().getSelectedItem();
        delNameField.setText(clubMember.getFirstName() + " " + clubMember.getLastName());
    }
    
    @FXML
    private void onEnter(ActionEvent ae) {
        handleAddMemberButtonAction();
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
    
    private List<Guest> createMemberList(String clubName) {
        List<Guest> guestListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuests(clubName);
            while(rs.next()) {
                Guest guest = new Guest(rs.getString("firstName"), rs.getString("lastName"), rs.getString("titanCard"));
                guestListLoc.add(guest);
            }
        } catch (SQLException ex) {
        }
        return guestListLoc;
    }

}
