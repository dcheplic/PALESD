package com.palesd.gui.attendance;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AttendanceController implements Initializable {

    @FXML private Button addGuestButton;
    @FXML private Button exitButton;
    @FXML private TableColumn<Guest, String> nameCol;
    @FXML private TableColumn<Guest, String> numberCol;
    @FXML private TableView<Guest> attendanceTable;
    @FXML private TextField cardField;
    @FXML private TextField nameField;
    
    private String eventName;
    
    @FXML
    private void handleAddGuestButtonAction() {
        String fixedNum = cardField.getText();
        if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?")))
            fixedNum = fixedNum.substring(4,10);
        if(!nameField.getText().trim().isEmpty() && !cardField.getText().trim().isEmpty()) {
            Database.insert(eventName, nameField.getText(), fixedNum);
            Database.insert("Master List", nameField.getText(), fixedNum);
        } else if(nameField.getText().trim().isEmpty())
            for(Guest guest : createGuestList("Master List"))
                if(fixedNum.equals(guest.getNumber()))
                    Database.insert(eventName, guest.getName(), guest.getNumber());
        
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
}
