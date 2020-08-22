package com.example.bienestarproveedores.consultancy;

public class Appointment {

    private String doctorId;
    private String patientId;
    private String consultancyType;
    private String patientName;
    private String appointmentTime;

    private String appointmentId;

    public Appointment(String doctorId, String patientId, String consultancyType, String patientName, String appointmentTime, String appointmentId) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.consultancyType = consultancyType;
        this.patientName = patientName;
        this.appointmentTime = appointmentTime;
        this.appointmentId = appointmentId;
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
}
