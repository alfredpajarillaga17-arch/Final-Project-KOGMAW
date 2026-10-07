package com.mycompany.ccdatrcl_finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize the first scene using the Login.fxml file
        scene = new Scene(loadFXML("/com/mycompany/ccdatrcl_finalproject/ui/login"), 600, 400);
        
        // Set the window title for the gadget maintenance system
        stage.setTitle("K.O.G.M.A.W. - System Login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Call this method from your controllers to switch screens.
     * Example: App.setRoot("/com/mycompany/ccdatrcl_finalproject/ui/Dashboard");
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}