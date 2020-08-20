package com.example.bienestarproveedores.consultancy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bienestarproveedores.HeaderLayout;
import com.example.bienestarproveedores.R;
import com.example.bienestarproveedores.firebase.FirebaseViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

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

        FirebaseViewModel firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
        LiveData<DataSnapshot> appointmentsDataSnapshot = firebaseViewModel
                .getAppointmentsDataSnapshot();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Prescriptions");


        //TODO armar una función que mande la receta a firebase !!
        buttonAssist.setOnClickListener(Navigation.createNavigateOnClickListener(goToConsultancyMain));

        buttonAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText prescription = view.findViewById(R.id.prescription);
                ref.child("receta-"+UUID.randomUUID().toString()).setValue(prescription.getText().toString());

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.consultancyAppointmentsFragment);

            }
        });

    }

}
