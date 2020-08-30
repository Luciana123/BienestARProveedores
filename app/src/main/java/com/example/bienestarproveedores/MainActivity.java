package com.example.bienestarproveedores;

import android.content.Intent;
import android.os.Bundle;

import com.example.bienestarproveedores.data.LoginDataSource;
import com.example.bienestarproveedores.ui.login.LoginActivity;
import com.example.bienestarproveedores.ui.login.Provider;
import com.example.bienestarproveedores.ui.login.ProviderType;
import com.example.bienestarproveedores.utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providersSetUp();

        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));

        String username = getApplicationContext()
                .getSharedPreferences(getString(R.string.shared_preferences_file), this.MODE_PRIVATE)
                .getString("username", "");

        // go straight to main if a username is stored
        if (username.isEmpty()) {
            Intent activityIntent = new Intent(this, LoginActivity.class);
            startActivity(activityIntent);
            finish();
        }

    }

    private void providersSetUp() {
        HashMap<String, Provider> providers = new HashMap<>();

        providers.put(Utils.ROOT_USER, new Provider("Juan Carlos Rodriguez", Utils.ROOT_USER,
                "bienestar", ProviderType.Medic));
        providers.put("felix", new Provider("Joao Felix", "felix", "bienestar",
                ProviderType.Medic));
        providers.put("bautista", new Provider("Bautista Cristophe", "bautista",
                "bienestar", ProviderType.Meals));
        providers.put("santiago", new Provider("Santiago Gonzales", "santiago",
                "bienestar", ProviderType.Assistance));
        providers.put("luciana", new Provider("Luciana Fernandez", "luciana",
                "bienestar", ProviderType.Meals));

        LoginDataSource.setProviders(providers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}