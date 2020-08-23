package com.example.bienestarproveedores.ui.login;

public class Provider {
    private static int idNumber = 0;
    private String username;
    private ProviderType type;
    private String pass;
    private String name;
    private int id;

    public Provider(String name, String username, String pass, ProviderType type) {
        this.id = idNumber++;
        this.username = username;
        this.pass = pass;
        this.type = type;
        this.name = name;
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

    public String getName() { return name; }
}
