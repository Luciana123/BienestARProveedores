package com.example.bienestarproveedores.consultancy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bienestarproveedores.HeaderLayout;
import com.example.bienestarproveedores.R;
import com.example.bienestarproveedores.firebase.FirebaseViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ConsultancyAppointmentsFragment extends Fragment {

    private static int NO_SELECTION = 100000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.consultancy_appointments_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HeaderLayout header = view.findViewById(R.id.fragment_header);
        ImageView icon = view.findViewById(R.id.bienestarPLogo);
        header.setDescription(getString(R.string.consultancy_appointments));
        header.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorConsultancy));

        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorConsultancyB));

        RecyclerView orderItemsRecyclerView = view.findViewById(R.id.appointments_recyclerview);
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ConsultancyAppointmentsAdapter adapter = new ConsultancyAppointmentsAdapter();
        orderItemsRecyclerView.setAdapter(adapter);

        FirebaseViewModel firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
        LiveData<DataSnapshot> appointmentsDataSnapshot = firebaseViewModel
                .getAppointmentsDataSnapshot();

        overrideBackButton(view);


        appointmentsDataSnapshot.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                adapter.showAppointment(dataSnapshot.getChildren());
            }
        });

        Button buttonAssist = view.findViewById(R.id.check_in_button);
        NavDirections specialistAction = ConsultancyAppointmentsFragmentDirections.actionConsultancyAppointmentsFragmentToVideoCallFragment();
        buttonAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedPostion = adapter.getSelected();
                if(selectedPostion != NO_SELECTION) {

                    adapter.viewValues.remove(selectedPostion);
                    adapter.notifyItemRemoved(selectedPostion);

                    Appointment a = adapter.appointments.get(selectedPostion);

                    deleteFromFirebase(a);
                    putInSharedPreferences(a);

                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.videoCallFragment);

                }

            }
        });

    }

    private void deleteFromFirebase(Appointment a){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Appointments");
        //todo descomentar esto!!!! es para que no me borre los turnos cuando pruebo y los tenga que ir a agregar de vuelta
        //ref.child(a.getAppointmentId()).removeValue();
    }

    private void overrideBackButton(View view){

        //Tiro esto por acá para overraidee el botón de atrás y no vuelva a la videollamada
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
                                  @Override
                                  public boolean onKey(View v, int keyCode, KeyEvent event) {
                                      if (keyCode == KeyEvent.KEYCODE_BACK) {
                                          NavController navController = Navigation.findNavController(view);
                                          navController.navigate(R.id.ProductsFragment);
                                          return true;
                                      }
                                      return false;
                                  }
                              }
        );
    }

    private void putInSharedPreferences(Appointment a){
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.sharedPreferences), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("patient_id", a.getPatientId().toString());
        editor.commit();

    }

    /**
     * Adapter for the medical specialists / professionals recycler view.
     */
    private static class ConsultancyAppointmentsAdapter extends RecyclerView.Adapter<ConsultancyAppointmentsItemsViewHolder> {

        List<Appointment> appointments = new ArrayList<>();
        private List<ConsultancyAppointmentsItemsViewHolder> viewValues = new ArrayList<>();

        public void showAppointment(Iterable<DataSnapshot> appointmentsData) {
            this.appointments = new ArrayList<>();
            for (DataSnapshot appointmentData : appointmentsData) {
                HashMap appointmentsDataMap = (HashMap) appointmentData.getValue();
                String date = (String) appointmentsDataMap.get("date");
                String time = (String) appointmentsDataMap.get("time");
                String consultancyType = (String) appointmentsDataMap.get("consultancy_type");
                String doctorId = (String) appointmentsDataMap.get("doctor_id");
                String patientName = (String) appointmentsDataMap.get("patient_name");
                String patientId = (String) appointmentsDataMap.get("patient_id");

                assert doctorId != null;
                Appointment a = new Appointment(doctorId, patientId, consultancyType, patientName, time, appointmentData.getKey());
                this.appointments.add(a);
            }

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ConsultancyAppointmentsItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consultancy_appointment_item, parent, false);

            view.setOnClickListener(v -> {
                v.setBackgroundColor(v.getResources().getColor(R.color.colorConsultancySelected));
                for(ConsultancyAppointmentsItemsViewHolder item: viewValues) {
                    if(item.itemView != v){
                        item.setSelected(false);
                        item.itemView.setBackgroundColor(item.itemView.getResources().getColor(R.color.colorWhite));
                    } else {
                        item.setSelected(true);
                    }
                }
            });

            return new ConsultancyAppointmentsItemsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ConsultancyAppointmentsItemsViewHolder holder, int position) {
            //TODO: set profile picture and description.
            holder.setName(appointments.get(position).getPatientName());
            holder.setDescription(appointments.get(position).getConsultancyType());
            holder.setAppointmentTime(appointments.get(position).getAppointmentTime());
            viewValues.add(holder);
        }

        @Override
        public int getItemCount() {
            return appointments.size();
        }

        public int getSelected() {
            int selectedPos = ConsultancyAppointmentsFragment.NO_SELECTION;

            for (ConsultancyAppointmentsItemsViewHolder item : viewValues) {
                if (item.isSelected()) {
                    selectedPos = item.getAdapterPosition();
                }
            }
            return selectedPos;
        }
    }

    /**
     * ViewHolder to display the details of an item of the meals order
     */
    private static class ConsultancyAppointmentsItemsViewHolder extends RecyclerView.ViewHolder {

        private ImageView pic;
        private TextView name;
        private TextView desc;
        private TextView appointmentTime;

        private boolean selected;

        ConsultancyAppointmentsItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.doctor_pic);
            name = itemView.findViewById(R.id.doctor_name);
            desc = itemView.findViewById(R.id.appointment_desc);
            appointmentTime = itemView.findViewById(R.id.appointment_time);

        }

        /**
         * Sets the profile picture of the view holder.
         * @param resId the resource identifier of the drawable.
         */
        void setPic(@DrawableRes int resId) {
            pic.setImageResource(resId);
        }

        void setSelected(boolean selected) {
            this.selected = selected;
        }

        boolean isSelected(){
            return selected;
        }

        void setName(String name) {
            this.name.setText(name);
        }

        void setDescription(String description) {
            desc.setText(description);
        }

        public TextView getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime.setText(appointmentTime);
        }
    }
}
