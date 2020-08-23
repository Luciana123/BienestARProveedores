package com.example.bienestarproveedores.ui.login;

enum ProviderType {Medic, Assistance, Meals}

public class Provider {
    private static int idNumber = 0;
    private String username;
    private ProviderType type;
    private String pass;
    private int id;

    public Provider(String username, String pass, ProviderType type) {
        this.id = idNumber++;
        this.username = username;
        this.pass = pass;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public ProviderType getType() {
        return type;
    }
}
