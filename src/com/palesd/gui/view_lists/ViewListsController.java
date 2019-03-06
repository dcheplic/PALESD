/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.view_lists;

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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class ViewListsController implements Initializable{

    @FXML private Button exitButton;
    @FXML private Button printButton;
    @FXML private ListView<String> eventList;
    @FXML private TableColumn<Guest, String> firstNameCol;
    @FXML private TableColumn<Guest, String> lastNameCol;
    @FXML private TableColumn<Guest, String> numberCol;
    @FXML private TableColumn<Guest, String> timeCol;
    @FXML private TableView<Guest> eventGuestTable;
    private String selectedEvent;
    private String styleSheet;
    
    private final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handlePrintButtonAction() {
        Date date = new Date();
        String time = sdf.format(date);
        if (selectedEvent != null) {
            PrintWriter writer = null;
            try {
                String[] textLines = initTextLines();
                writer = new PrintWriter("src/com/palesd/printable/" + selectedEvent + ".txt", "UTF-8");
                writer.println("\n\n\n\n");
                writer.println("          " + selectedEvent.substring(0, selectedEvent.length()-4));
                writer.println("          Current Date and Time: " + time + "\n");
                for (String textLine : textLines) {
                    writer.print(textLine);
                }
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                System.out.println(ex);
            } finally {
                writer.close();
            }

            try {
                URL url = new File("src/com/palesd/gui/view_lists/Print.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Print");
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                scene.getStylesheets().add(styleSheet);
                PrintController controller = loader.<PrintController>getController();
                controller.setEventName(selectedEvent);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

    }
    
    @FXML
    private void handleSelectedEventAction() {
        eventGuestTable.getItems().setAll(createGuestList(eventList.getSelectionModel().getSelectedItem()));
        selectedEvent = eventList.getSelectionModel().getSelectedItem();
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
    }    
    
    private List<String> createEventList(String identifier) {
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
    
    private String[] initTextLines() {
        String[] lines = null;
        if (lines == null) {
            lines = new String[eventGuestTable.getItems().size()];
            for (int i=0;i<lines.length;i++) {
                String formatStr = "%-35s %-25s %-20s%n";
                lines[i]= String.format(formatStr, "          " + eventGuestTable.getItems().get(i).getFirstName() + " " + eventGuestTable.getItems().get(i).getLastName(),
                                                   eventGuestTable.getItems().get(i).getNumber(),
                                                   "Time In: " + eventGuestTable.getItems().get(i).getTime());
            }
        }
        return lines;
    }

    public void setIdentifier(String identifier) {
        eventList.getItems().setAll(createEventList(identifier));
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }
}
