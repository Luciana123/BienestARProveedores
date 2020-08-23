package com.example.bienestarproveedores.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private Provider success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    public LoginResult(Provider provider) {
        this.success = provider;
    }

    @Nullable
    Provider getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}