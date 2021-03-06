package com.example.bienestarproveedores.consultancy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bienestarproveedores.HeaderLayout;
import com.example.bienestarproveedores.ProductsFragmentDirections;
import com.example.bienestarproveedores.R;
import com.example.bienestarproveedores.firebase.FirebaseViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrescriptionFragment extends Fragment {
    private SharedPreferences sharedPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.consultancy_prescription_fragment, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HeaderLayout header = view.findViewById(R.id.fragment_header);
        TextView patientText = view.findViewById(R.id.patientTExt);
        TextView medicText = view.findViewById(R.id.medicText);



        sharedPref = getContext().getSharedPreferences(
                getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);


        String username = sharedPref.getString("name", "");
        String patientName = sharedPref.getString("patient_name", "");

        patientText.setText("Describe prescription for: " + patientName);
        medicText.setText(username);

        header.setDescription(getString(R.string.consultancy_prescriptions));
        header.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorConsultancy));

        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorConsultancyB));

        Button buttonAssist = view.findViewById(R.id.send_prescription);
        NavDirections goToConsultancyMain = PrescriptionFragmentDirections.actionPrescriptionFragmentToConsultancyAppointmentsFragment();

        FirebaseViewModel firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
        LiveData<DataSnapshot> appointmentsDataSnapshot = firebaseViewModel
                .getAppointmentsDataSnapshot();

        //Tiro esto por acá para overraidee el botón de atrás y no vuelva a la videollamada
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
                                  @Override
                                  public boolean onKey(View v, int keyCode, KeyEvent event) {
                                      if (keyCode == KeyEvent.KEYCODE_BACK) {
                                          NavController navController = Navigation.findNavController(view);
                                          navController.navigate(R.id.consultancyAppointmentsFragment);
                                          return true;
                                      }
                                      return false;
                                  }
                              }
        );

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Prescriptions");

        buttonAssist.setOnClickListener(Navigation.createNavigateOnClickListener(goToConsultancyMain));

        buttonAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText prescription = view.findViewById(R.id.prescription);

                String prescriptionId = "receta-" + UUID.randomUUID().toString();

                ref.child(prescriptionId).child("date").setValue(LocalDate.now().toString());
                ref.child(prescriptionId).child("prescription").setValue(prescription.getText().toString());
                ref.child(prescriptionId).child("client_id").setValue(sharedPref.getString("patient_id", "1"));
                String doctorId = String.valueOf(sharedPref.getInt("id", 1));
                ref.child(prescriptionId).child("medic_id").setValue(doctorId);
                ref.child(prescriptionId).child("medic_name").setValue(sharedPref.getString("name", ""));

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.consultancyAppointmentsFragment);

            }
        });
    }
}
