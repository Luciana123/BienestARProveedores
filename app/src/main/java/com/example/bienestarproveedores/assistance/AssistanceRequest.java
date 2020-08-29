package com.example.bienestarproveedores.assistance;

public class AssistanceRequest {

    private String assistantId;
    private String assistantName;
    private String clientId;
    private String clientName;
    private String details;
    private String status;
    private String type;
    private String key;

    public AssistanceRequest(String assistantId, String assistantName, String clientId, String clientName, String details, String status, String type, String key) {
        this.assistantId = assistantId;
        this.assistantName = assistantName;
        this.clientId = clientId;
        this.clientName = clientName;
        this.details = details;
        this.status = status;
        this.type = type;
        this.key = key;
    }

    public String getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
