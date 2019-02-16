/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.main;

import com.palesd.database.Database;
import com.palesd.models.Guest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class MasterListStartController implements Initializable {
    
    @FXML Button beginButton;
    @FXML Button exitButton;
    @FXML TextField cardField;
    
    private String styleSheet;
    
    @FXML
    private void handleBeginButtonAction() {
        boolean containsAdmin = false;
        String fixedNum = cardField.getText();
        if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?"))) {
            fixedNum = fixedNum.substring(4, 10);
        }
        
        for (Guest g : createGuestList("Admin List"))
            if (g.getNumber() == Integer.parseInt(fixedNum)) {
                containsAdmin = true;
                break;
            }
        
        if (containsAdmin) {
            try {
                URL url = new File("src/com/palesd/gui/main/MasterList.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                scene.getStylesheets().add(styleSheet);
                MasterListController controller = loader.<MasterListController>getController();
                controller.setCurrentAdmin(Integer.parseInt(fixedNum));
                MainMenu.pushAndSetScene(scene);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void onEnter(ActionEvent ae) {
        handleBeginButtonAction();
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
            while (rs.next()) {
                Guest guest = new Guest(rs.getString("firstName"), rs.getString("lastName"), rs.getInt("titanCard"), rs.getString("time"));
                guestList.add(guest);
            }
        } catch (SQLException ex) {
        }
        return guestList;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
    
}
