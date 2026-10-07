package com.mycompany.ccdatrcl_finalproject.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

public class DashboardController {

    // ==========================================
    // MAIN VIEWS (StackPane Children)
    // ==========================================
    @FXML private VBox dashboardView;
    @FXML private VBox createApptView;
    @FXML private VBox searchView;

    // ==========================================
    // CREATE APPOINTMENT CONTROLS
    // ==========================================
    @FXML private TextField customerNameField;
    @FXML private ComboBox<String> deviceCombo;
    @FXML private TextField brandField;
    @FXML private TextField modelField;
    @FXML private TextField issueField;
    @FXML private ComboBox<String> phaseCombo;

    // ==========================================
    // SEARCH CONTROLS
    // ==========================================
    @FXML private TextField searchIdField;
    @FXML private ComboBox<String> updatePhaseCombo; // Note: Ensure you gave this fx:id in Scene Builder

    // ==========================================
    // DASHBOARD CONTROLS
    // ==========================================
    // Note: You will need to define a Record/Ticket model class to replace <Object>
    @FXML private TableView<Object> recordsTable; 

    @FXML
    public void initialize() {
        // 1. Populate Device Type ComboBox
        deviceCombo.getItems().addAll(
            "Smartphone", "Tablet", "Laptop", "Desktop"
        );
        deviceCombo.getSelectionModel().selectFirst();

        // 2. Populate Repair Phase ComboBox (Creation)
        phaseCombo.getItems().addAll(
            "Initial Diagnostic", "Disassembly", "Part Ordering", "Cleaning", 
            "Component Swap", "Reassembly", "Firmware Flash", "Stress Test", 
            "Final QA", "Ready for Pickup"
        );
        phaseCombo.getSelectionModel().selectFirst();

        // 3. Populate Repair Phase ComboBox (Search/Update screen)
        if (updatePhaseCombo != null) {
            updatePhaseCombo.getItems().addAll(phaseCombo.getItems());
        }

        // 4. Set Default View
        showDashboardView();
        
        // TODO: Initialize TableView columns here
        // TODO: Load existing records from your Custom Linked List / Custom BST here
    }

    // ==========================================
    // SIDEBAR NAVIGATION LOGIC
    // ==========================================
    
    @FXML
    private void showDashboardView() {
        dashboardView.setVisible(true);
        createApptView.setVisible(false);
        searchView.setVisible(false);
        dashboardView.toFront();
        // TODO: Refresh table data from custom data structures
    }

    @FXML
    private void showCreateApptView() {
        dashboardView.setVisible(false);
        createApptView.setVisible(true);
        searchView.setVisible(false);
        createApptView.toFront();
    }

    @FXML
    private void showSearchView() {
        dashboardView.setVisible(false);
        createApptView.setVisible(false);
        searchView.setVisible(true);
        searchView.toFront();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Load the Login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/ccdatrcl_finalproject/ui/Login.fxml"));
            Scene loginScene = new Scene(loader.load(), 600, 400);

            // Get the current stage from the clicked button
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            currentStage.setTitle("K.O.G.M.A.W. - Login");
            currentStage.setScene(loginScene);
            currentStage.centerOnScreen();
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // ACTION HANDLERS
    // ==========================================

    @FXML
    private void handleSaveAppointment(ActionEvent event) {
        // 1. Capture the data from the UI
        String customerName = customerNameField.getText();
        String device = deviceCombo.getValue();
        String brand = brandField.getText();
        String model = modelField.getText();
        String issue = issueField.getText();
        String phase = phaseCombo.getValue();

        // Validate that fields are not empty
        if (customerName.isEmpty() || brand.isEmpty() || model.isEmpty() || issue.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all text fields.");
            return;
        }

        // TODO: 2. Generate new Ticket ID
        // TODO: 3. Create a new Record object
        // TODO: 4. Insert into K.O.G.M.A.W. Custom Data Structures (e.g., Queue or Hash Table)

        System.out.println("Saving Ticket for: " + customerName + " | Device: " + brand + " " + model);

        // 5. Clear fields after successful save
        customerNameField.clear();
        brandField.clear();
        modelField.clear();
        issueField.clear();
        deviceCombo.getSelectionModel().selectFirst();
        phaseCombo.getSelectionModel().selectFirst();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment created successfully!");
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchId = searchIdField.getText();
        
        if (searchId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a Ticket ID.");
            return;
        }

        // TODO: Search for the ticket ID using your Custom Hash Table or BST
        // TODO: Display the found data in the Search View labels/fields
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}