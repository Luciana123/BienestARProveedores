package com.example.bienestarproveedores.consultancy;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AppointmentsProvider {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<Appointment> getClientsForDoctor(int id){

        //TODO ACA ES DONDE TENDRÍA QUE IR A LA DB A TRAERSE UNA LISTA DE TURNOS
        // LA DB DEBERÍA CONTENER UNA LISTA DE OBJETOS APPOINTMENT{DOCTOR, PACIENTE, CONSULTA}

        db.collection("consultancy_appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<Appointment> apps = document.toObject(AppointmentDocument.class).appointments;
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return null;

    }

}
