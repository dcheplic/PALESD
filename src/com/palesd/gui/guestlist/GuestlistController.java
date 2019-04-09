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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class GuestlistController implements Initializable {
    
    @FXML private Button addGuestButton;
    @FXML private Button exitButton;
    @FXML private ComboBox<String> sorter;
    @FXML private Label status;
    @FXML private ListView guestList;
    @FXML private TableColumn firstNameCol;
    @FXML private TableColumn lastNameCol;
    @FXML private TableColumn numberCol;
    @FXML private TableColumn timeCol;
    @FXML private TableView<Guest> attendanceTable;
    @FXML private TextField cardField;
    
    private String eventName;
    private String guestListName;
    
    private final DateFormat sdf = new SimpleDateFormat("hh:mm a");
    
    @FXML
    private void handleAddGuestButtonAction() {
        String fixedNum = cardField.getText();
        List<Integer> cardList = new ArrayList();
        if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?")))
            fixedNum = fixedNum.substring(4,10);
        boolean onList = false;
        for(Guest guest : attendanceTable.getItems())
            if(guest.getNumber() == Integer.parseInt(fixedNum))
                onList = true;
        for(Guest guest : createGuestList(eventName.substring(0, eventName.length()-5) + "_gue")) {
            cardList.add(guest.getNumber());
            if(!cardList.contains(Integer.parseInt(fixedNum))) {
                status.setText("NOT ON LIST");
                status.setTextFill(Color.RED);   
            }
            if(Integer.parseInt(fixedNum) == guest.getNumber())
                addGuestHelper(onList, eventName, guest.getFirstName(), guest.getLastName(), guest.getNumber());
        }
        attendanceTable.getItems().setAll(createGuestList(eventName));
        cardField.clear();
    }
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handleSortSelection() {
        if(sorter.getValue().equals("First In"))
            guestList.getItems().setAll(createNameList(guestListName));
        if(sorter.getValue().equals("First Name"))
            guestList.getItems().setAll(sortNameList(guestListName, "firstName"));
        if(sorter.getValue().equals("Last Name"))
            guestList.getItems().setAll(sortNameList(guestListName, "lastName"));
        if(sorter.getValue().equals("Time"))
            guestList.getItems().setAll(sortNameList(guestListName, "time"));
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
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        sorter.getItems().setAll("First In", "First Name", "Last Name", "Time In");
    }
    
    private void addGuestHelper(boolean onList, String eventName, String firstName, String lastName, int cardNum) {
        if(onList) {
            status.setText("ALREADY SIGNED IN");
            status.setTextFill(Color.BLACK);
        } else {
            Date date = new Date();
            String time = sdf.format(date);
            Database.insert(eventName, firstName, lastName, cardNum, time);
            status.setText("ON LIST");
            status.setTextFill(Color.GREEN);
        }
    }
    
    private List<Guest> createGuestList(String eventName) {
        List<Guest> guestListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuests(eventName);
            while(rs.next()) {
                Guest guest = new Guest(rs.getString("firstName"), rs.getString("lastName"), rs.getInt("titanCard"), rs.getString("time"));
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
                guestListLoc.add(rs.getString("firstName") + " " + rs.getString("lastName"));
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
        this.guestListName = guestListName;
    }
    
    private List<String> sortNameList(String eventName, String sort) {
        List<String> guestListLoc = new ArrayList();
        try {
            ResultSet rs = Database.selectAllGuestsSorted(eventName, sort);
            while(rs.next()) {
                guestListLoc.add(rs.getString("firstName") + " " + rs.getString("lastName"));
            }
        } catch (SQLException ex) {
        }
        return guestListLoc;
    }
    
}
