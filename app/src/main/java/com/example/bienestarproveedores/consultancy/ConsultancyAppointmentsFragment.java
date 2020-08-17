package com.example.bienestarproveedores.consultancy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bienestarproveedores.HeaderLayout;
import com.example.bienestarproveedores.R;

import java.util.ArrayList;
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
        header.setDescription(getString(R.string.consultancy_appointments));

        RecyclerView orderItemsRecyclerView = view.findViewById(R.id.appointments_recyclerview);
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ConsultancyAppointmentsAdapter adapter = new ConsultancyAppointmentsAdapter();
        orderItemsRecyclerView.setAdapter(adapter);

        Button buttonAssist = view.findViewById(R.id.check_in_button);
        NavDirections specialistAction = ConsultancyAppointmentsFragmentDirections.actionConsultancyAppointmentsFragmentToVideoCallFragment();
        buttonAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(adapter.getSelected() != NO_SELECTION) {

                    //TODO acá debería borrarlo con firebase!!!
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
        AppointmentsProvider appointmentsProvider = new AppointmentsProvider();

        ConsultancyAppointmentsAdapter() {
            //TODO hardcodeado por ahora.. un solo doctor.
            //appointmentsProvider.getClientsForDoctor(1);

            Appointment ap1 = new Appointment("doctor1", "3", "Sesión de masaje de pies", "Mark Zuckerberg", "08:00");
            Appointment ap2 = new Appointment("doctor1", "4", "Vacunación", "Bill Gates", "09:00");
            Appointment ap3 = new Appointment("doctor1", "5", "Sesión de terapia", "Elon Musk", "20:10");
            Appointment ap4 = new Appointment("doctor1", "3", "Sesión de masaje de pies", "Mark Zuckerberg", "20:10");
            appointments.add(ap1);
            appointments.add(ap2);
            appointments.add(ap3);
            appointments.add(ap4);
        }

        @NonNull
        @Override
        public ConsultancyAppointmentsItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consultancy_appointment_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    v.setBackgroundColor(v.getResources().getColor(android.R.color.holo_blue_light));

                    for(ConsultancyAppointmentsItemsViewHolder item: viewValues) {
                        if(item.itemView != v){
                            item.setSelected(false);
                            item.itemView.setBackgroundColor(v.getResources().getColor(android.R.color.transparent));

                        } else {
                            item.setSelected(true);
                        }
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
