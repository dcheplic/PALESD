/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.main;

import com.palesd.database.Database;
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
import javafx.scene.control.Label;
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
public class MasterListController implements Initializable {

    @FXML private Button addAdminButton;
    @FXML private Button deleteAdmin;
    @FXML private Button deleteButton;
    @FXML private Button exitButton;
    @FXML private Label currentAdminLabel;
    @FXML private ListView<Integer> adminListView;
    @FXML private TableColumn firstNameCol;
    @FXML private TableColumn lastNameCol;
    @FXML private TableColumn numberCol;
    @FXML private TableView<Guest> masterListTable;
    @FXML private TextField adminField;
    
    private Guest selectedGuest;
    private int currentAdmin;
    private int selectedAdmin;
    
    @FXML
    private void handleAddAdminButtonAction() {
        String fixedNum = adminField.getText();
        if (adminField.getText().contains(";000") && (adminField.getText().endsWith("?") || adminField.getText().endsWith("?+E?"))) {
            fixedNum = fixedNum.substring(4, 10);
        }
        Database.insert("Admin List", "", "", Integer.parseInt(fixedNum), "");
        adminListView.getItems().setAll(createAdminList());
        adminField.clear();
    }
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handleDeleteAdminButtonAction() {
        if(selectedAdmin != currentAdmin) {
            Database.deleteRow("Admin List", selectedAdmin);
            adminListView.getItems().setAll(createAdminList());
        }
    }
    
    @FXML
    private void handleDeleteButtonAction() {
        Database.deleteRow("Master List", selectedGuest.getNumber());
        masterListTable.getItems().setAll(createGuestList("Master List"));
    }
    
    @FXML
    private void handleSelectedAdminAction() {
        selectedAdmin = adminListView.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleSelectedItemAction() {
        selectedGuest = masterListTable.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void onEnter(ActionEvent ae) {
        handleAddAdminButtonAction();
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
        masterListTable.getItems().setAll(createGuestList("Master List"));
        
        adminListView.getItems().setAll(createAdminList());
    }
    
    private List<Integer> createAdminList() {
        List<Integer> adminList = new ArrayList();
        
        for(Guest g : createGuestList("Admin List"))
            adminList.add(g.getNumber());
        
        return adminList;
    }
    
    private List<Guest> createGuestList(String eventName) {
        List<Guest> guestListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuests(eventName);
            while(rs.next()) {
                Guest guest = new Guest(rs.getString("firstName"), rs.getString("lastName"), rs.getInt("titanCard"), "");
                guestListLoc.add(guest);
            }
        } catch (SQLException ex) {
        }
        return guestListLoc;
    }

    public void setCurrentAdmin(int currentAdmin) {
        this.currentAdmin = currentAdmin;
        currentAdminLabel.setText(currentAdmin + "");
    }
}
