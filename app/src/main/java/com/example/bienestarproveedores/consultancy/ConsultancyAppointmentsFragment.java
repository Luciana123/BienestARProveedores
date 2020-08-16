package com.example.bienestarproveedores.consultancy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bienestarproveedores.HeaderLayout;
import com.example.bienestarproveedores.ProductsFragmentDirections;
import com.example.bienestarproveedores.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ConsultancyAppointmentsFragment extends Fragment {

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
    }

    /**
     * Adapter for the medical specialists / professionals recycler view.
     */
    private static class ConsultancyAppointmentsAdapter extends RecyclerView.Adapter<ConsultancyAppointmentsItemsViewHolder> {

        List<String> appointments = new ArrayList<>();
        private List<ConsultancyAppointmentsItemsViewHolder> viewValues = new ArrayList<>();
        AppointmentsProvider appointmentsProvider = new AppointmentsProvider();

        ConsultancyAppointmentsAdapter() {
            //TODO hardcodeado por ahora.. un solo doctor.
            //appointmentsProvider.getClientsForDoctor(1);
            appointments.add("Juan Perez");
            appointments.add("Fabio Albertario");
            appointments.add("Juan Ignacio Rotondo");
        }

        @NonNull
        @Override
        public ConsultancyAppointmentsItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consultancy_appointment_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    v.setBackgroundColor(v.getResources().getColor(android.R.color.darker_gray));

                    for(ConsultancyAppointmentsItemsViewHolder item: viewValues) {
                        if(item.itemView != v){
                            item.setSelected(false);
                            item.itemView.setBackgroundColor(v.getResources().getColor(android.R.color.holo_red_dark));

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
            holder.setName(appointments.get(position));
            holder.setDescription("Sesión de cardiología.");
            viewValues.add(holder);
        }

        @Override
        public int getItemCount() {
            return appointments.size();
        }
    }

    /**
     * ViewHolder to display the details of an item of the meals order
     */
    private static class ConsultancyAppointmentsItemsViewHolder extends RecyclerView.ViewHolder {

        private ImageView pic;
        private TextView name;
        private TextView desc;

        private boolean selected;

        ConsultancyAppointmentsItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.doctor_pic);
            name = itemView.findViewById(R.id.doctor_name);
            desc = itemView.findViewById(R.id.appointment_desc);

            //NavDirections specialistAction = ConsultancyAppointmentsFragmentDirections.actionConsultancyAppointmentsFragmentToVideoCallFragment();
            //itemView.setOnClickListener(Navigation.createNavigateOnClickListener(specialistAction));

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

    }
}
