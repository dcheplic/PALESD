/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.view_lists;

import com.palesd.database.Database;
import com.palesd.gui.main.MainMenu;
import com.palesd.models.Guest;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.awt.print.*;
import javax.print.PrintService;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class ViewListsController implements Initializable, Printable {

    @FXML private Button exitButton;
    @FXML private Button printButton;
    @FXML private ListView<String> eventList;
    @FXML private TableColumn<Guest, String> firstNameCol;
    @FXML private TableColumn<Guest, String> lastNameCol;
    @FXML private TableColumn<Guest, String> numberCol;
    @FXML private TableColumn<Guest, String> timeCol;
    @FXML private TableView<Guest> eventGuestTable;
    
    private int[] pageBreaks;
    private String[] textLines;
    
    @FXML
    private void handleExitButtonAction() {
        MainMenu.popAndSetScene();
    }
    
    @FXML
    private void handlePrintButtonAction() {
        initTextLines();
        PrintService[] services = PrinterJob.lookupPrintServices();
        for(PrintService s : services)
            System.out.println(s.getName());
//        PrinterJob job = PrinterJob.getPrinterJob();
//         job.setPrintable(this);
//         boolean ok = job.printDialog();
//         if (ok) {
//             try {
//                  job.print();
//             } catch (PrinterException ex) {
//              /* The job did not successfully complete */
//             }
//         }
    }
    
    @FXML
    private void handleSelectedEventAction() {
        eventGuestTable.getItems().setAll(createGuestList(eventList.getSelectionModel().getSelectedItem()));
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

    public void setIdentifier(String identifier) {
        eventList.getItems().setAll(createEventList(identifier));
    }
    
    private void initTextLines() {
        if (textLines == null) {
            textLines = new String[eventGuestTable.getItems().size()];
            for (int i=0;i<textLines.length;i++) {
                textLines[i]= eventGuestTable.getItems().get(i).getFirstName() + " " +
                        eventGuestTable.getItems().get(i).getLastName() + "        " +
                        eventGuestTable.getItems().get(i).getNumber();
            }
        }
    }
    

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        Font font = new Font("Serif", Font.PLAIN, 10);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
 
        if (pageBreaks == null) {
            int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
            int numBreaks = (textLines.length-1)/linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b=0; b<numBreaks; b++) {
                pageBreaks[b] = (b+1)*linesPerPage; 
            }
        }
 
        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }
 
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         * Since we are drawing text we
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
 
        /* Draw each line that is on this page.
         * Increment 'y' position by lineHeight for each line.
         */
        int y = 75;
        g.drawString(eventList.getSelectionModel().getSelectedItem().substring(0, eventList.getSelectionModel().getSelectedItem().length()-4), 75, y);
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end   = (pageIndex == pageBreaks.length)
                         ? textLines.length : pageBreaks[pageIndex];
        for (int line=start; line<end; line++) {
            y += lineHeight;
            g.drawString(textLines[line], 75, y);
        }
 
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
}
