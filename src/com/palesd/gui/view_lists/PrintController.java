/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.view_lists;

import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javax.print.PrintService;

/**
 * FXML Controller class
 *
 * @author devin
 */
public class PrintController implements Initializable {
    
    @FXML private Button exitButton;
    @FXML private Button printButton;
    @FXML private ListView<String> printerList;
    
    private String selectedPrinter;
    private String eventName;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @FXML
    private void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handlePrintButtonAction() {
        if(selectedPrinter != null){
            String path = "src/com/palesd/printable/" + eventName + ".txt";
            String[] command = { "lp", "-d", selectedPrinter, path};
            try {
                Process proc = new ProcessBuilder(command).start();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }        
    }
    
    @FXML
    private void handleSelectedPrinterAction() {
        selectedPrinter = printerList.getSelectionModel().getSelectedItem();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PrintService[] printers = PrinterJob.lookupPrintServices();
        List<String> printerNames = new ArrayList();
        for(PrintService s : printers)
            printerNames.add(s.getName());
        printerList.getItems().setAll(printerNames);
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
}
