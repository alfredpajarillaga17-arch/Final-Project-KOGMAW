package com.mycompany.ccdatrcl_finalproject.ui;

import com.mycompany.ccdatrcl_finalproject.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LandingController {

    @FXML
    private void handleBookNow(ActionEvent event) {
        navigate("/com/mycompany/ccdatrcl_finalproject/ui/appointment");
    }

    @FXML
    private void handleAdminLogin(ActionEvent event) {
        navigate("/com/mycompany/ccdatrcl_finalproject/ui/login");
    }

    private void navigate(String fxml) {
        try {
            App.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
