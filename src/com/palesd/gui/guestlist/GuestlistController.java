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
public class GuestlistController implements Initializable {
    
    @FXML private Button addGuestButton;
    @FXML private Button exitButton;
    @FXML private Label status;
    @FXML private ListView guestList;
    @FXML private TableColumn nameCol;
    @FXML private TableColumn numberCol;
    @FXML private TableView<Guest> attendanceTable;
    @FXML private TextField cardField;
    @FXML private TextField nameField;
    
    private String eventName;
    private String guestListName;
    
    @FXML
    private void handleAddGuestButtonAction() {
        boolean onList = false;
        for(Guest guest : attendanceTable.getItems())
            if(guest.getName().equals(nameField.getText()))
                onList = true;
        String fixedNum = cardField.getText();
        if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?")))
            fixedNum = fixedNum.substring(4,10);
        if(!nameField.getText().trim().isEmpty() && !cardField.getText().trim().isEmpty())
            addGuestHelperNoSwipe(eventName, onList, fixedNum);
        else if(nameField.getText().trim().isEmpty())
            for(Guest guest : createGuestList("Master List")) {
                if(fixedNum.equals(guest.getNumber()))
                    addGuestHelperSwipe(onList, eventName, guest.getName(), guest.getNumber());
                else
                    status.setText("NOT ON LIST");
            }
                        
        
        attendanceTable.getItems().setAll(createGuestList(eventName));
        nameField.clear();
        cardField.clear();
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
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        status.setText(guestListName);
    }
    
    private void addGuestHelperNoSwipe(String eventName, boolean onList, String fixedNum) {
        if(!guestList.getItems().contains(nameField.getText())) {
            status.setText("NOT ON LIST");
        } else if(onList) {
            status.setText("ALREADY SIGNED IN");
        } else {
            Database.insert(eventName, nameField.getText(), fixedNum);
            status.setText("ON LIST");
        }
    }
    
    private void addGuestHelperSwipe(boolean onList, String eventName, String name, String cardNum) {
        if(!guestList.getItems().contains(name)) {
            status.setText("NOT ON LIST");
        } else if(onList) {
            status.setText("ALREADY SIGNED IN");
        } else {
            Database.insert(eventName, name, cardNum);
            status.setText("ON LIST");
        }
    }
    
    private List<Guest> createGuestList(String eventName) {
        List<Guest> guestListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuests(eventName);
            while(rs.next()) {
                Guest guest = new Guest(rs.getString("name"), rs.getString("titanCard"));
                guestListLoc.add(guest);
            }
        } catch (SQLException ex) {
        }
        return guestListLoc;
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
    
    public void setGuestlistName(String guestListName) {
        guestList.getItems().setAll(createNameList(guestListName));
    }
    
}
