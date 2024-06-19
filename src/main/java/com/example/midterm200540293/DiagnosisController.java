package com.example.midterm200540293;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class DiagnosisController {

    private static final String DB_URL = "jdbc:mysql://localhost:3307/DiagnosisDatabase";
    private static final String USER = "root";
    private static final String PASS = "";

    @FXML
    private TextField patientIdField, symptomsField, diagnosisField, medicinesField;
    @FXML
    private CheckBox wardRequiredCheckBox;
    @FXML
    private Button saveButton, searchButton;
    @FXML
    private TableView<Diagnosis> tableView;
    @FXML
    private TableColumn<Diagnosis, String> columnPatientId, columnSymptoms, columnDiagnosis, columnMedicines;
    @FXML
    private TableColumn<Diagnosis, Boolean> columnWardRequired;

    @FXML
    private Button closeButton;

    @FXML
    public void initialize() {
        saveButton.setOnAction(e -> saveDiagnosis());
        searchButton.setOnAction(e -> searchDiagnosis());
        closeButton.setOnAction(e -> closeApplication());

        columnPatientId.setCellValueFactory(cellData -> cellData.getValue().patientIdProperty());
        columnSymptoms.setCellValueFactory(cellData -> cellData.getValue().symptomsProperty());
        columnDiagnosis.setCellValueFactory(cellData -> cellData.getValue().diagnosisProperty());
        columnMedicines.setCellValueFactory(cellData -> cellData.getValue().medicinesProperty());
    }

    private void closeApplication() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void saveDiagnosis() {
        String patientId = patientIdField.getText();
        String symptoms = symptomsField.getText();
        String diagnosis = diagnosisField.getText();
        String medicines = medicinesField.getText();
        boolean wardRequired = wardRequiredCheckBox.isSelected();

        if (patientId.isEmpty() || symptoms.isEmpty() || diagnosis.isEmpty() || medicines.isEmpty()) {
            showAlert("All fields are required");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO diagnosis (patient_id, symptoms, diagnosis, medicines, ward_required) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, patientId);
            preparedStatement.setString(2, symptoms);
            preparedStatement.setString(3, diagnosis);
            preparedStatement.setString(4, medicines);
            preparedStatement.setBoolean(5, wardRequired);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
        }
    }

    private void searchDiagnosis() {
        tableView.getItems().clear();
        ObservableList<Diagnosis> data = FXCollections.observableArrayList();

        String patientId = patientIdField.getText().trim();
        if (patientId.isEmpty()) {
            showAlert("Please enter a patient ID.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM diagnosis WHERE patient_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String symptoms = resultSet.getString("symptoms");
                String diagnosis = resultSet.getString("diagnosis");
                String medicines = resultSet.getString("medicines");
                boolean wardRequired = resultSet.getBoolean("ward_required");
                data.add(new Diagnosis(patientId, symptoms, diagnosis, medicines, wardRequired));
            }

            tableView.setItems(data);
        } catch (SQLException e) {
            showAlert(e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
