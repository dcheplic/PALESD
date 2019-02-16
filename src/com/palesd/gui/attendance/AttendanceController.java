package com.palesd.gui.attendance;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AttendanceController implements Initializable {

    @FXML private Button addGuestButton;
    @FXML private Button exitButton;
    @FXML private TableColumn<Guest, String> firstNameCol;
    @FXML private TableColumn<Guest, String> lastNameCol;
    @FXML private TableColumn<Guest, String> numberCol;
    @FXML private TableColumn<Guest, String> timeCol;
    @FXML private TableView<Guest> attendanceTable;
    @FXML private TextField cardField;
    @FXML private TextField nameField;
    
    private String eventName;
    
    private final DateFormat sdf = new SimpleDateFormat("hh:mm a");
    
    @FXML
    private void handleAddGuestButtonAction() {
        Date date = new Date();
        String time = sdf.format(date);
        String fixedNum = cardField.getText();
        if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?")))
            fixedNum = fixedNum.substring(4,10);
        if(!nameField.getText().trim().isEmpty() && !cardField.getText().trim().isEmpty()) {
            Database.insert(eventName, nameField.getText().split(" ")[0], nameField.getText().split(" ")[1], Integer.parseInt(fixedNum), time);
            Database.insert("Master List", nameField.getText().split(" ")[0], nameField.getText().split(" ")[1], Integer.parseInt(fixedNum), time);
        } else if(nameField.getText().trim().isEmpty())
            for(Guest guest : createGuestList("Master List"))
                if(Integer.parseInt(fixedNum) ==  guest.getNumber())
                    Database.insert(eventName, guest.getFirstName(), guest.getLastName(), guest.getNumber(), time);
        
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
                Guest guest = new Guest(rs.getString("firstName"), rs.getString("lastName"), rs.getInt("titanCard"), rs.getString("time"));
                guestList.add(guest);
            }
        } catch (SQLException ex) {
        }
        return guestList;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
}
