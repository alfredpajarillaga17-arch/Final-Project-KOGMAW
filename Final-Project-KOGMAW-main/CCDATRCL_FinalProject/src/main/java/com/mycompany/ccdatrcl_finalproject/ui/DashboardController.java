package com.mycompany.ccdatrcl_finalproject.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.mycompany.ccdatrcl_finalproject.App;
import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.utils.DataManager;
import com.mycompany.ccdatrcl_finalproject.utils.DatabaseConnection;
import com.mycompany.ccdatrcl_finalproject.utils.SortingAlgorithms;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DashboardController {

    @FXML private VBox dashboardView;
    @FXML private VBox createApptView;
    @FXML private VBox searchView;
    @FXML private TextField contactNumberField; // New field for contact number
    @FXML private TextField customerNameField;
    @FXML private ComboBox<String> deviceCombo;
    @FXML private TextField brandField;
    @FXML private TextField modelField;
    @FXML private TextField issueField;
    @FXML private ComboBox<String> phaseCombo;

    @FXML private TextField searchIdField;
    @FXML private ComboBox<String> updatePhaseCombo; 
    
    // UI Elements for displaying search results
    @FXML private Label searchResultName;
    @FXML private Label searchResultDevice;
    @FXML private Label searchResultIssue;

    // Track currently loaded ticket in search view for updating
    private Appointment activeSearchedTicket = null;

    // Table setup using the Appointment model
    @FXML private TableView<Appointment> recordsTable; 
    @FXML private TableColumn<Appointment, String> colId;
    @FXML private TableColumn<Appointment, String> colName;
    @FXML private TableColumn<Appointment, String> colContactNumber;
    @FXML private TableColumn<Appointment, String> colDevice;
    @FXML private TableColumn<Appointment, String> colBrand;
    @FXML private TableColumn<Appointment, String> colModel;
    @FXML private TableColumn<Appointment, String> colIssue;
    @FXML private TableColumn<Appointment, String> colPhase;
    @FXML private TableColumn<Appointment, String> colDate;

    private DataManager dataManager;

    @FXML
    public void initialize() {
        dataManager = DataManager.getInstance();

        deviceCombo.getItems().addAll("Smartphone", "Tablet", "Laptop", "Desktop");
        deviceCombo.getSelectionModel().selectFirst();

        phaseCombo.getItems().addAll(
            "Initial Diagnostic", "Disassembly", "Part Ordering", "Cleaning", 
            "Component Swap", "Reassembly", "Firmware Flash", "Stress Test", 
            "Final QA", "Ready for Pickup"
        );
        phaseCombo.getSelectionModel().selectFirst();

        if (updatePhaseCombo != null) {
            updatePhaseCombo.getItems().addAll(phaseCombo.getItems());
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIssue.setCellValueFactory(new PropertyValueFactory<>("reportedIssue"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colDevice.setCellValueFactory(new PropertyValueFactory<>("deviceType"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colPhase.setCellValueFactory(new PropertyValueFactory<>("assessmentPhase"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

        // Fetch existing records from database
        com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList dbRecords = 
            DatabaseConnection.fetchAllAppointments();
        
        com.mycompany.ccdatrcl_finalproject.models.CustomLinkedList.Node current = dbRecords.getHead();
        
        while (current != null) {
            Appointment appt = current.data;
            dataManager.getInstantCache().put(appt.getId(), appt);
            dataManager.getSearchTree().insert(appt);
            dataManager.getPriorityQueue().insert(appt);
            dataManager.getIntakeQueue().enqueue(appt);
            dataManager.getHistoryLog().insert(appt);
            current = current.next;
        }

        showDashboardView();
    }
    
    @FXML
    private void showDashboardView() {
        dashboardView.setVisible(true);
        createApptView.setVisible(false);
        searchView.setVisible(false);
        dashboardView.toFront();
        refreshTableData();
        fadeIn(dashboardView);
    }

    @FXML
    private void showCreateApptView() {
        dashboardView.setVisible(false);
        createApptView.setVisible(true);
        searchView.setVisible(false);
        createApptView.toFront();
        fadeIn(createApptView);
    }

    @FXML
    private void showSearchView() {
        dashboardView.setVisible(false);
        createApptView.setVisible(false);
        searchView.setVisible(true);
        searchView.toFront();
        fadeIn(searchView);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Always return to the public welcome screen, not the admin login.
            App.setRoot("/com/mycompany/ccdatrcl_finalproject/ui/landing");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fadeIn(Node node) {
        node.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(320), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private String generateRandomTicketId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder("KGW-");
        java.util.Random rnd = new java.util.Random();
        for (int i = 0; i < 4; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @FXML
    private void handleSaveAppointment(ActionEvent event) {
        String customerName = customerNameField.getText();
        String contactNumber = contactNumberField.getText(); // Capture contact number
        String device = deviceCombo.getValue();
        String brand = brandField.getText();
        String model = modelField.getText();
        String issue = issueField.getText();
        String phase = phaseCombo.getValue();

        // Include contactNumber in validation check
        if (customerName.isEmpty() || contactNumber.isEmpty() || brand.isEmpty() || model.isEmpty() || issue.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all text fields.");
            return;
        }

        String newId = generateRandomTicketId();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Pass contactNumber into Appointment constructor
        Appointment newAppointment = new Appointment(
            newId, customerName, contactNumber, device, brand, model, issue, currentDate, phase
        );

        boolean isSavedToDb = DatabaseConnection.insertAppointment(newAppointment);
        
        if (!isSavedToDb) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the appointment to XAMPP database.");
            return;
        }

        dataManager.getInstantCache().put(newId, newAppointment);
        dataManager.getSearchTree().insert(newAppointment);
        dataManager.getPriorityQueue().insert(newAppointment);
        dataManager.getIntakeQueue().enqueue(newAppointment);
        dataManager.getHistoryLog().insert(newAppointment);
        dataManager.getUndoStack().push(newAppointment);

        // Clear fields including contact number
        customerNameField.clear();
        contactNumberField.clear();
        brandField.clear();
        modelField.clear();
        issueField.clear();
        deviceCombo.getSelectionModel().selectFirst();
        phaseCombo.getSelectionModel().selectFirst();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Appointment " + newId + " created successfully!");
        refreshTableData();
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchId = searchIdField.getText().trim();
        
        if (searchId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a Ticket ID.");
            return;
        }

        activeSearchedTicket = dataManager.getInstantCache().get(searchId.toUpperCase());

        if (activeSearchedTicket != null) {
            if (searchResultName != null) searchResultName.setText("Name: " + activeSearchedTicket.getCustomerName());
            if (searchResultDevice != null) searchResultDevice.setText("Device: " + activeSearchedTicket.getBrand() + " " + activeSearchedTicket.getModel());
            if (searchResultIssue != null) searchResultIssue.setText("Issue: " + activeSearchedTicket.getReportedIssue());
            if (updatePhaseCombo != null) updatePhaseCombo.setValue(activeSearchedTicket.getAssessmentPhase());
            
            showAlert(Alert.AlertType.INFORMATION, "Found", "Ticket loaded successfully.");
        } else {
            activeSearchedTicket = null;
            showAlert(Alert.AlertType.ERROR, "Not Found", "No ticket found with ID: " + searchId);
        }
    }

    @FXML
    private void handleUpdatePhase(ActionEvent event) {
        if (activeSearchedTicket == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please search and load a valid ticket first.");
            return;
        }

        String newPhase = updatePhaseCombo.getValue();
        if (newPhase == null || newPhase.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a phase to update.");
            return;
        }

        // Update database record
        boolean dbSuccess = DatabaseConnection.updateAppointmentPhase(activeSearchedTicket.getId(), newPhase);

        if (dbSuccess) {
            // Update local object phase
            activeSearchedTicket.setAssessmentPhase(newPhase);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Ticket phase updated successfully to: " + newPhase);
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update assessment phase in the database.");
        }
    }

    @FXML
    private void handleProcessNextTicket(ActionEvent event) {
        Appointment nextTicket = dataManager.getIntakeQueue().dequeue();
        if (nextTicket != null) {
            showAlert(Alert.AlertType.INFORMATION, "Workbench Status", 
                "Now repairing: " + nextTicket.getId() + "\n" +
                "Device: " + nextTicket.getBrand() + " " + nextTicket.getModel() + "\n" +
                "Issue: " + nextTicket.getReportedIssue());
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Queue Empty", "No pending tickets in the intake queue.");
        }
    }

    @FXML
    private void handleUndoLastEntry(ActionEvent event) {
        Appointment lastTicket = dataManager.getUndoStack().pop();
        if (lastTicket != null) {
            String targetId = lastTicket.getId();
            dataManager.getInstantCache().remove(targetId);
            dataManager.getSearchTree().delete(targetId);
            dataManager.getHistoryLog().delete(targetId);
            refreshTableData();
            showAlert(Alert.AlertType.INFORMATION, "Undo Successful", "Removed accidental ticket " + targetId + " from the system.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Undo Failed", "No recent actions to undo.");
        }
    }

    @FXML
    private void handleCheckWorkflowSteps(ActionEvent event) {
        Appointment selectedAppt = recordsTable.getSelectionModel().getSelectedItem();
        if (selectedAppt != null) {
            String currentPhase = selectedAppt.getAssessmentPhase();
            dataManager.getWorkflowGraph().bfs(currentPhase);
            showAlert(Alert.AlertType.INFORMATION, "Workflow Mapped", "Check console output for BFS traversal starting from: " + currentPhase);
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a ticket from the table first.");
        }
    }

    @FXML
    private void handleDeleteFromSearch(ActionEvent event) {
        // Ensure a ticket is actually loaded from a search
        if (activeSearchedTicket == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please search and load a valid ticket first.");
            return;
        }

        String targetId = activeSearchedTicket.getId();

        // 1. Delete from Database (Requires the deleteAppointment method in DatabaseConnection)
        boolean dbSuccess = DatabaseConnection.deleteAppointment(targetId);
        
        if (dbSuccess) {
            // 2. Delete from local Data Structures
            dataManager.getInstantCache().remove(targetId);
            dataManager.getSearchTree().delete(targetId);
            dataManager.getHistoryLog().delete(targetId);
            dataManager.getPriorityQueue().removeById(targetId);
            
            // 3. Clear the search UI and reset the active ticket
            if (searchResultName != null) searchResultName.setText("Name: ");
            if (searchResultDevice != null) searchResultDevice.setText("Device: ");
            if (searchResultIssue != null) searchResultIssue.setText("Issue: ");
            searchIdField.clear();
            activeSearchedTicket = null;
            
            // 4. Refresh the main table in the background
            refreshTableData();
            
            showAlert(Alert.AlertType.INFORMATION, "Deleted", "Ticket " + targetId + " was permanently removed.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not delete ticket from the database.");
        }
    }

    @FXML
    private void handleExportHistoryLog(ActionEvent event) {
        dataManager.getHistoryLog().traverse();
        showAlert(Alert.AlertType.INFORMATION, "Log Exported", "Sequential repair history printed to console.");
    }

    @FXML
    private void handleCheckWorkflowStepsDFS(ActionEvent event) {
        Appointment selectedAppt = recordsTable.getSelectionModel().getSelectedItem();
        if (selectedAppt != null) {
            String currentPhase = selectedAppt.getAssessmentPhase();
            dataManager.getWorkflowGraph().dfs(currentPhase);
            showAlert(Alert.AlertType.INFORMATION, "Workflow Mapped", "Check console output for DFS traversal starting from: " + currentPhase);
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a ticket from the table first.");
        }
    }

    @FXML
    private void handleSortByName(ActionEvent event) {
        Appointment[] dataArray = dataManager.getPriorityQueue().getHeapForDisplay();
        
        SortingAlgorithms.SortMetrics metrics = SortingAlgorithms.selectionSortByName(dataArray);
        System.out.println("Selection Sort by Name Complete. Comparisons: " + metrics.comparisons + " | Movements: " + metrics.movements);
        
        ObservableList<Appointment> sortedList = FXCollections.observableArrayList();
        for (Appointment appt : dataArray) {
            if (appt != null) sortedList.add(appt);
        }
        recordsTable.setItems(sortedList);
    }

    @FXML
    private void handleSortByDate(ActionEvent event) {
        Appointment[] dataArray = dataManager.getPriorityQueue().getHeapForDisplay();
        
        // Execute Bubble Sort algorithm for date sorting metrics demonstration
        SortingAlgorithms.SortMetrics metrics = SortingAlgorithms.bubbleSort(dataArray);
        System.out.println("Bubble Sort by Date Complete. Comparisons: " + metrics.comparisons + " | Movements: " + metrics.movements);
        
        ObservableList<Appointment> sortedList = FXCollections.observableArrayList();
        for (Appointment appt : dataArray) {
            if (appt != null) sortedList.add(appt);
        }
        recordsTable.setItems(sortedList);
    }

    private void refreshTableData() {
        if (recordsTable == null || dataManager == null) return;
        Appointment[] currentData = dataManager.getPriorityQueue().getHeapForDisplay();
        ObservableList<Appointment> tableData = FXCollections.observableArrayList();
        for (Appointment appt : currentData) {
            if (appt != null) tableData.add(appt);
        }
        recordsTable.setItems(tableData);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}