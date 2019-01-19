/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palesd.gui.main;

import com.palesd.database.Database;
import java.io.File;
import java.util.Stack;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
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
        Database.openConnection();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        
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
    
    public static void popAndSetScene() {
        sceneStack.pop();
        bigStage.setScene(sceneStack.peek());
    }
    
    public static void pushAndSetScene(Scene scene) {
        sceneStack.push(scene);
        bigStage.setScene(sceneStack.peek());
    }
}
