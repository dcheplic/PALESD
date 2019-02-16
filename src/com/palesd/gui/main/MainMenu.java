/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.main;

import com.palesd.database.Database;
import com.palesd.models.Guest;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Devin
 */
public class MainMenu extends Application {

    private static Stage bigStage;
    private static final Stack<Scene> sceneStack = new Stack();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        Database.openConnection();
        //Database.createTable("Admin List");
        //Database.deleteTable("Admin List");
        if (createGuestList("Admin List").isEmpty()) {
            root = FXMLLoader.load(getClass().getResource("InitialStartup.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(new File("src/com/palesd/themes/Basic.css").toURI().toURL().toExternalForm());
        sceneStack.push(scene);
        bigStage = stage;
        bigStage.setScene(sceneStack.peek());
        bigStage.initStyle(StageStyle.UNDECORATED);
        bigStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void closeStage() {
        bigStage.close();
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

    public static void popAndSetScene() {
        sceneStack.pop();
        bigStage.setScene(sceneStack.peek());
    }

    public static void pushAndSetScene(Scene scene) {
        sceneStack.push(scene);
        bigStage.setScene(sceneStack.peek());
    }
}
