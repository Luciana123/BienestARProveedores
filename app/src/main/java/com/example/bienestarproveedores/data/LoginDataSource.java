package com.example.bienestarproveedores.data;

import com.example.bienestarproveedores.data.model.LoggedInUser;
import com.example.bienestarproveedores.ui.login.Provider;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    static HashMap<String, Provider> providers;

    public Result<LoggedInUser> login(String username, String password) {
            LoggedInUser fakeUser = new LoggedInUser(username, password);
            if (validateUser(fakeUser)) {
                return new Result.Success<LoggedInUser>(fakeUser);
            }
            return new Result.Error(new IOException());
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private boolean validateUser(@NotNull LoggedInUser user) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String loginPass = user.getPassword();
        String loginUsername = user.getUsername();
        Provider provider = providers.get(loginUsername);

        if (provider == null) return false;
        return provider.getPass().equals(loginPass);
    }

    public static void setProviders(HashMap<String, Provider> _providers) {
        providers = _providers;
    }

    public static Provider getProvider(String key) {
        return providers.get(key);
    }
}