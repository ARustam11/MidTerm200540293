package com.example.midterm200540293;

import javafx.beans.property.*;

public class Diagnosis {
    private final StringProperty patientId;
    private final StringProperty symptoms;
    private final StringProperty diagnosis;
    private final StringProperty medicines;
    private final BooleanProperty wardRequired;

    public Diagnosis(String patientId, String symptoms, String diagnosis, String medicines, boolean wardRequired) {
        this.patientId = new SimpleStringProperty(patientId);
        this.symptoms = new SimpleStringProperty(symptoms);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.medicines = new SimpleStringProperty(medicines);
        this.wardRequired = new SimpleBooleanProperty(wardRequired);
    }

    public String getPatientId() {
        return patientId.get();
    }

    public StringProperty patientIdProperty() {
        return patientId;
    }

    public String getSymptoms() {
        return symptoms.get();
    }

    public StringProperty symptomsProperty() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public StringProperty diagnosisProperty() {
        return diagnosis;
    }

    public String getMedicines() {
        return medicines.get();
    }

    public StringProperty medicinesProperty() {
        return medicines;
    }

    public boolean isWardRequired() {
        return wardRequired.get();
    }

    public BooleanProperty wardRequiredProperty() {
        return wardRequired;
    }
}
