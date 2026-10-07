package com.mycompany.ccdatrcl_finalproject.ui;

import java.io.IOException;

import com.mycompany.ccdatrcl_finalproject.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("admin123")) {
            try {
                App.setRoot("/com/mycompany/ccdatrcl_finalproject/ui/dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            App.setRoot("/com/mycompany/ccdatrcl_finalproject/ui/landing");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleForgotPassword(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot Password");
        alert.setHeaderText(null);
        alert.setContentText("Please contact the system administrator to reset the admin password.");
        alert.showAndWait();
    }
}
