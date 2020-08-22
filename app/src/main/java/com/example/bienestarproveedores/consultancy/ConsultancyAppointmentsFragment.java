package com.example.bienestarproveedores.consultancy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


                if(adapter.getSelected() != NO_SELECTION) {

                    adapter.viewValues.remove(adapter.getSelected());
                    adapter.notifyItemRemoved(adapter.getSelected());

                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.videoCallFragment);

                }

            }
        });

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
                Long doctorId = (Long) appointmentsDataMap.get("doctor_id");
                String patientName = (String) appointmentsDataMap.get("patient_name");

                Appointment a = new Appointment(doctorId.toString(), patientName, consultancyType, patientName, time);
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
            holder.setName(appointments.get(position).getPatient_name());
            holder.setDescription(appointments.get(position).getConsultancy_type());
            holder.setAppointmentTime(appointments.get(position).getAppointment_time());
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
