package com.example.bienestarproveedores.consultancy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bienestarproveedores.HeaderLayout;
import com.example.bienestarproveedores.R;

public class PrescriptionFragment  extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.consultancy_prescription_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HeaderLayout header = view.findViewById(R.id.fragment_header);
        header.setDescription(getString(R.string.consultancy_prescriptions));

        Button buttonAssist = view.findViewById(R.id.send_prescription);
        NavDirections goToConsultancyMain = PrescriptionFragmentDirections.actionPrescriptionFragmentToConsultancyAppointmentsFragment();

        //TODO armar una función que mande la receta a firebase !!
        buttonAssist.setOnClickListener(Navigation.createNavigateOnClickListener(goToConsultancyMain));

    }

}
