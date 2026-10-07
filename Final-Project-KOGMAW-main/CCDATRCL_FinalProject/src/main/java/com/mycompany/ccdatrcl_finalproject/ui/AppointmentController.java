package com.mycompany.ccdatrcl_finalproject.ui;

import com.mycompany.ccdatrcl_finalproject.App;
import com.mycompany.ccdatrcl_finalproject.models.Appointment;
import com.mycompany.ccdatrcl_finalproject.utils.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AppointmentController {

    @FXML private TextField customerNameField;
    @FXML private TextField contactNumberField;
    @FXML private ComboBox<String> deviceCombo;
    @FXML private TextField modelField;
    @FXML private TextField issueField;
    @FXML private DatePicker datePicker;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        deviceCombo.getItems().setAll("Smartphone", "Tablet", "Desktop", "Laptop");

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker picker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        setDisable(empty || date.isBefore(LocalDate.now()));
                    }
                };
            }
        });
    }

    @FXML
    private void handleBook(ActionEvent event) {
        String name = customerNameField.getText().trim();
        String phone = contactNumberField.getText().trim();
        String device = deviceCombo.getValue();
        String model = modelField.getText().trim();
        String issue = issueField.getText().trim();
        LocalDate date = datePicker.getValue();

        if (name.isEmpty() || phone.isEmpty() || device == null ||
                model.isEmpty() || issue.isEmpty() || date == null) {
            showAlert(Alert.AlertType.WARNING, "Incomplete Form",
                    "Please fill in all appointment fields.");
            return;
        }

        String ticketId = generateTicketId();

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Appointment");
        confirm.setHeaderText("Please double-check your appointment details");
        confirm.setContentText(
                "Ticket ID: " + ticketId +
                "\nCustomer: " + name +
                "\nPhone: " + phone +
                "\nDevice: " + device +
                "\nModel: " + model +
                "\nIssue: " + issue +
                "\nDate: " + date.format(DATE_FORMAT)
        );

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        // The public booking page creates an appointment with the same fields
        // used by the team's existing Appointment model.
        Appointment appointment = new Appointment(
                ticketId, name, phone, device, "", model, issue,
                date.format(DATE_FORMAT), "Pending"
        );

        if (!DatabaseConnection.insertAppointment(appointment)) {
            showAlert(Alert.AlertType.ERROR, "Database Error",
                    "The appointment could not be saved. Please make sure the KOGMAW database is running.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Appointment Booked",
                "Your appointment has been booked successfully.\nTicket ID: " + ticketId);

        clearFields();

        try {
            App.setRoot("/com/mycompany/ccdatrcl_finalproject/ui/landing");
        } catch (IOException e) {
            e.printStackTrace();
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

    private void clearFields() {
        customerNameField.clear();
        contactNumberField.clear();
        deviceCombo.getSelectionModel().clearSelection();
        modelField.clear();
        issueField.clear();
        datePicker.setValue(null);
    }

    private String generateTicketId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder id = new StringBuilder("KGW-");
        for (int i = 0; i < 4; i++) {
            id.append(chars.charAt(random.nextInt(chars.length())));
        }
        return id.toString();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
