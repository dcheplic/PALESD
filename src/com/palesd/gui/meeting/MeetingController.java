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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MeetingController implements Initializable {
    
    @FXML private Button exitButton;
    @FXML private TableColumn firstNameCol;
    @FXML private TableColumn lastNameCol;
    @FXML private TableColumn numberCol;
    @FXML private TableView<Guest> attendanceTable;
    @FXML private TextField cardField;
    
    private String clubName;
    private String clubTableName;
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void onEnter(ActionEvent ae) {
        String fixedNum = cardField.getText();
        if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?")))
            fixedNum = fixedNum.substring(4,10);
        for(Guest guest : createGuestList(clubName)) {
            if(guest.getNumber().equals(fixedNum)) {
                Database.insert(clubTableName, guest.getFirstName(), guest.getLastName(), fixedNum);
                Database.insert("Master List", guest.getFirstName(), guest.getLastName(), fixedNum);
                break;
            }
        }
        
        attendanceTable.getItems().setAll(createGuestList(clubTableName));
        cardField.clear();
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
    }
    
    private List<Guest> createGuestList(String eventName) {
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

    public void setClubTableName(String clubTableName) {
        this.clubTableName = clubTableName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    
    
}
