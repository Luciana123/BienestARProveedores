package com.example.bienestarproveedores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bienestarproveedores.data.LoginDataSource;
import com.example.bienestarproveedores.ui.login.LoginActivity;
import com.example.bienestarproveedores.ui.login.Provider;
import com.example.bienestarproveedores.ui.login.ProviderType;
import com.example.bienestarproveedores.utils.Utils;


public class ProductsFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.products_fragment, container, false);
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ProductLayout mealsProductLayout;
    ProductLayout assistanceProductLayout;
    ProductLayout consultancyProductLayout;
    ProductLayout pharmacyProductLayout;
    ImageButton logoutButton;
    TextView welcomeMsj;
    String username = getContext()
            .getSharedPreferences(getString(R.string.shared_preferences_file), getContext().MODE_PRIVATE)
            .getString("username", "");

    String name = getContext()
            .getSharedPreferences(getString(R.string.shared_preferences_file), getContext().MODE_PRIVATE)
            .getString("name", "");

    getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
    getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(), R.color.common_google_signin_btn_text_light_focused));
    getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.common_google_signin_btn_text_light_focused));

    welcomeMsj = view.findViewById(R.id.bienvenida);

    /* Consultorio médico. */
    consultancyProductLayout = view.findViewById(R.id.product_consultancy);
    consultancyProductLayout.setDescription(getString(R.string.consultancy));
    consultancyProductLayout.setImageResource(R.drawable.ic_main_medic);
    consultancyProductLayout.setBackgroundResource(R.drawable.rounded);
    consultancyProductLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorConsultancy));
    NavDirections consultancyAction = ProductsFragmentDirections.actionProductsFragmentToConsultancyAppointmentsFragment2();
    consultancyProductLayout.setOnClickListener(Navigation.createNavigateOnClickListener(consultancyAction));

    /* Viandas. */
    mealsProductLayout = view.findViewById(R.id.product_meals);
    mealsProductLayout.setDescription(getString(R.string.meals));
    mealsProductLayout.setImageResource(R.drawable.ic_main_viands_apple);
    mealsProductLayout.setBackgroundResource(R.drawable.rounded);
    mealsProductLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorMeals));
    NavDirections mealsAction = ProductsFragmentDirections.actionProductsFragmentToMealsFragment();
    mealsProductLayout.setOnClickListener(Navigation.createNavigateOnClickListener(mealsAction));

    /* Asistencia. */
    assistanceProductLayout = view.findViewById(R.id.product_assistance);
    assistanceProductLayout.setDescription(getString(R.string.assistance));
    assistanceProductLayout.setBackgroundResource(R.drawable.rounded);
    assistanceProductLayout.setImageResource(R.drawable.ic_main_assist_hand);
    assistanceProductLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorAssistance));
    NavDirections assistanceAction = ProductsFragmentDirections.actionProductsFragmentToAssistanceProviderFragment();
    assistanceProductLayout.setOnClickListener(Navigation.createNavigateOnClickListener(assistanceAction));

    logoutButton = view.findViewById(R.id.logout);

    logoutButton.setOnClickListener(v -> {
      getContext().getSharedPreferences(
                      getString(R.string.shared_preferences_file), Context.MODE_PRIVATE
              ).edit().remove("username").apply();

      startActivity(new Intent(getContext(), LoginActivity.class));
      getActivity().finish();
    });

    assert username != null;
    if (username.isEmpty()) return;

    welcomeMsj.setText("Welcome " + name);
    Provider provider = LoginDataSource.getProvider(username);

    if (username.equals(Utils.ROOT_USER)) return;

    if (provider.getType() == ProviderType.Medic) {
      assistanceProductLayout.setVisibility(View.GONE);
      mealsProductLayout.setVisibility(View.GONE);
    }
    if (provider.getType() == ProviderType.Assistance) {
      consultancyProductLayout.setVisibility(View.GONE);
      mealsProductLayout.setVisibility(View.GONE);
    }
    if (provider.getType() == ProviderType.Meals) {
      assistanceProductLayout.setVisibility(View.GONE);
      consultancyProductLayout.setVisibility(View.GONE);
    }
  }
}
