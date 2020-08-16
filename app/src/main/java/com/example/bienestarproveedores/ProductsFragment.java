package com.example.bienestarproveedores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;


public class ProductsFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.products_fragment, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ProductLayout mealsProductLayout;
    ProductLayout assistanceProductLayout;
    ProductLayout consultancyProductLayout;
    ProductLayout pharmacyProductLayout;

    /* Consultorio médico. */
    consultancyProductLayout = view.findViewById(R.id.product_consultancy);
    consultancyProductLayout.setDescription(getString(R.string.consultancy));
    consultancyProductLayout.setImageResource(R.drawable.ic_main_medic);
    consultancyProductLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorConsultancy));
    NavDirections consultancyAction = ProductsFragmentDirections.actionFirstFragmentToVideoCallFragment();
    consultancyProductLayout.setOnClickListener(Navigation.createNavigateOnClickListener(consultancyAction));

    /* Viandas. */
    mealsProductLayout = view.findViewById(R.id.product_meals);
    mealsProductLayout.setDescription(getString(R.string.meals));
    mealsProductLayout.setImageResource(R.drawable.ic_main_viands_apple);
    mealsProductLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorMeals));
    //NavDirections mealsAction = ProductsFragmentDirections.actionProductsFragmentToMealsFragment();
    //mealsProductLayout.setOnClickListener(Navigation.createNavigateOnClickListener(mealsAction));

    /* Asistencia. */
    assistanceProductLayout = view.findViewById(R.id.product_assistance);
    assistanceProductLayout.setDescription(getString(R.string.assistance));
    assistanceProductLayout.setImageResource(R.drawable.ic_main_assist_hand);
    assistanceProductLayout.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorAssistance));
    //NavDirections assistanceAction = ProductsFragmentDirections.actionProductsFragmentToAssistanceFragment();
    //assistanceProductLayout.setOnClickListener(Navigation.createNavigateOnClickListener(assistanceAction));


  }
}
