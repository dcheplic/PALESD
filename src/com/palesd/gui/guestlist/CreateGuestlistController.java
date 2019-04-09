/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.guestlist;

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
import javafx.scene.control.TextField;

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
    @FXML private TextField cardField;
    
    private String eventName;
    private String selectedName;
    private String styleSheet;
    
    @FXML
    private void handleAddGuestButtonAction() {
        String fixedNum = cardField.getText();
        if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?")))
            fixedNum = fixedNum.substring(4,10);
        if(!nameField.getText().trim().isEmpty() && !cardField.getText().trim().isEmpty()) {
            Database.insert(eventName, nameField.getText().split(" ")[0], nameField.getText().split(" ")[1], Integer.parseInt(fixedNum), "");
            Database.insert("Master List", nameField.getText().split(" ")[0], nameField.getText().split(" ")[1], Integer.parseInt(fixedNum), "");
        } else if(nameField.getText().trim().isEmpty())
            for(Guest guest : createGuestList("Master List"))
                if(Integer.parseInt(fixedNum) ==  guest.getNumber())
                    Database.insert(eventName, guest.getFirstName(), guest.getLastName(), guest.getNumber(), "");
        
        guestList.getItems().setAll(createNameList(eventName));
        nameField.clear();
        cardField.clear();
    }
    
    @FXML
    private void handleDeleteButtonAction() {
            Database.deleteGuestRow(eventName, selectedName.split(" ")[0], selectedName.split(" ")[1]);
            guestList.getItems().setAll(createNameList(eventName));
    }
    
    @FXML
    private void handleSelectedEventAction() {
        selectedName = guestList.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
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

    private List<String> createNameList(String eventName) {
        List<String> guestListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuests(eventName);
            while(rs.next()) {
                guestListLoc.add(rs.getString("firstName") + " " + rs.getString("lastName"));
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
