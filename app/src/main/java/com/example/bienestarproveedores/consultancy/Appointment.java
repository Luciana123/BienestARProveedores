package com.example.bienestarproveedores.consultancy;

public class Appointment {

    private String doctor_id;
    private String patient_id;
    private String consultancy_type;
    private String patient_name;
    private String appointment_time;

    public Appointment(String doctor_id, String patient_id, String consultancy_type, String patient_name, String appointment_time) {
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.consultancy_type = consultancy_type;
        this.patient_name = patient_name;
        this.appointment_time = appointment_time;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String   getConsultancy_type() {
        return consultancy_type;
    }

    public void setConsultancy_type(String consultancy_type) {
        this.consultancy_type = consultancy_type;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }
}
