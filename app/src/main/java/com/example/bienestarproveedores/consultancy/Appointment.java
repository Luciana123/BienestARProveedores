package com.example.bienestarproveedores.consultancy;

public class Appointment {

    private String doctorId;
    private String patientId;
    private String consultancyType;
    private String patientName;
    private String appointmentTime;

    private String appointmentId;

    private String date;

    public Appointment(String doctorId, String patientId, String consultancyType, String patientName, String appointmentTime, String appointmentId, String date) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.consultancyType = consultancyType;
        this.patientName = patientName;
        this.appointmentTime = appointmentTime;
        this.appointmentId = appointmentId;
        this.date = date;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getConsultancyType() {
        return consultancyType;
    }

    public void setConsultancyType(String consultancyType) {
        this.consultancyType = consultancyType;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
