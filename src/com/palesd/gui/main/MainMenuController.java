package com.palesd.gui.main;

import com.palesd.database.Database;
import com.palesd.gui.view_lists.DeleteListsController;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainMenuController implements Initializable {

    @FXML private Button exitButton;
    @FXML private Button MASTER;
    @FXML private ImageView attendanceImageButton;
    @FXML private ImageView guestlistImageButton;
    @FXML private ImageView meetingImageButton;
    @FXML private ComboBox<String> themeComboBox;
    
    private String styleSheet;
    
    @FXML
    private void handleAttendanceImageClickAction() {
        try {
            URL url = new File("src/com/palesd/gui/main/AttendanceMode.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            AttendanceModeController controller = loader.<AttendanceModeController>getController();
            controller.setStyleSheet(styleSheet);
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
        }
    }
    
    @FXML
    private void handleGuestlistImageClickAction() {
        try {
            URL url = new File("src/com/palesd/gui/main/GuestlistMode.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            GuestlistModeController controller = loader.<GuestlistModeController>getController();
            controller.setStyleSheet(styleSheet);
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    @FXML
    private void handleMASTERClickAction() {
        try {
            URL url = new File("src/com/palesd/gui/main/MasterList.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            MasterListController controller = loader.<MasterListController>getController();
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    @FXML
    private void handleMeetingImageClickAction() {
        try {
            URL url = new File("src/com/palesd/gui/main/MeetingMode.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Scene scene = new Scene((Pane) loader.load());
            scene.getStylesheets().clear();
            scene.getStylesheets().add(styleSheet);
            MeetingModeController controller = loader.<MeetingModeController>getController();
            controller.setStyleSheet(styleSheet);
            MainMenu.pushAndSetScene(scene);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }


    @FXML
    private void handleExitButtonAction() {
        Database.closeConnection();
        MainMenu.closeStage();
    }

    @FXML
    private void handleThemeSelectionAction() {
        try {
            Scene scene = themeComboBox.getScene();
            scene.getStylesheets().clear();
            styleSheet = new File("src/com/palesd/themes/" + themeComboBox.getValue() + ".css").toURI().toURL().toExternalForm();
            scene.getStylesheets().add(styleSheet);
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            styleSheet = new File("src/com/palesd/themes/Basic.css").toURI().toURL().toExternalForm();
            themeComboBox.getItems().setAll(
                    "Basic",
                    "Brown",
                    "Olive",
                    "Night");
            
            Image attendanceImage = new Image(new File("src/com/palesd/images/attendance.jpg").toURI().toURL().toExternalForm());
            attendanceImageButton.setImage(attendanceImage);
            Image guestlistImage = new Image(new File("src/com/palesd/images/guestlist.jpg").toURI().toURL().toExternalForm());
            guestlistImageButton.setImage(guestlistImage);
            Image meetingImage = new Image(new File("src/com/palesd/images/meeting.jpg").toURI().toURL().toExternalForm());
            meetingImageButton.setImage(meetingImage);
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }
    }

}
