package com.example.bienestarproveedores.firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseViewModel extends ViewModel {

  private static final FirebaseDatabase FIREBASE_DB = FirebaseDatabase.getInstance();
  private static final DatabaseReference APPOINTMENTS_DB = FIREBASE_DB.getReference("Appointments");
  private static final DatabaseReference PRESCRIPTIONS_DB = FIREBASE_DB.getReference("Prescriptions");
  private static final DatabaseReference MEALS_DB = FIREBASE_DB.getReference("Meals");

  private final FirebaseQueryLiveData appointmentsLiveData = new FirebaseQueryLiveData(APPOINTMENTS_DB);
  private final FirebaseQueryLiveData prescriptionsLiveData = new FirebaseQueryLiveData(PRESCRIPTIONS_DB);
  private final FirebaseQueryLiveData mealsLiveData = new FirebaseQueryLiveData(MEALS_DB);

  public LiveData<DataSnapshot> getAppointmentsDataSnapshot() {
    return appointmentsLiveData;
  }

  public LiveData<DataSnapshot> getPrescriptionsDataSnapshot() {
    return prescriptionsLiveData;
  }

  public LiveData<DataSnapshot> getMealsLiveDataSnapshot() {
    return mealsLiveData;
  }

}