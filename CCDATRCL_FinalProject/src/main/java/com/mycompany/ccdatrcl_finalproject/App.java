package com.mycompany.ccdatrcl_finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/com/mycompany/ccdatrcl_finalproject/ui/landing"), 1125, 829);

        stage.setTitle("K.O.G.M.A.W");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        Parent nextRoot = loadFXML(fxml);
        nextRoot.setOpacity(0);
        scene.setRoot(nextRoot);

        FadeTransition fade = new FadeTransition(Duration.millis(420), nextRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
