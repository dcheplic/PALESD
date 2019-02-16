/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.main;

import com.palesd.database.Database;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Devin
 */
public class InitialStartupController implements Initializable {

    @FXML Button addAdminButton;
    @FXML TextField cardField;

    @FXML
    private void handleAddAdminButtonAction() {
        if (!cardField.getText().equals("")) {
            try {
                String fixedNum = cardField.getText();
                if (cardField.getText().contains(";000") && (cardField.getText().endsWith("?") || cardField.getText().endsWith("?+E?"))) {
                    fixedNum = fixedNum.substring(4, 10);
                }
                Database.insert("Admin List", "", "", Integer.parseInt(fixedNum), "");

                URL url = new File("src/com/palesd/gui/main/MainMenu.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Scene scene = new Scene((Pane) loader.load());
                scene.getStylesheets().clear();
                MainMenu.pushAndSetScene(scene);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    @FXML
    private void onEnter(ActionEvent ae) {
        handleAddAdminButtonAction();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
