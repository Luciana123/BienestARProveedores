package com.example.bienestarproveedores.meals;

import android.app.AlertDialog;
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
import com.example.bienestarproveedores.consultancy.Appointment;
import com.example.bienestarproveedores.consultancy.AppointmentsProvider;
import com.example.bienestarproveedores.consultancy.ConsultancyAppointmentsFragmentDirections;
import com.example.bienestarproveedores.firebase.FirebaseViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MealsFragment extends Fragment {

    private static int NO_SELECTION = 100000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.meals_pending_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());

        super.onViewCreated(view, savedInstanceState);
        HeaderLayout header = view.findViewById(R.id.fragment_header);
        header.setDescription(getString(R.string.meals_to_retrieve));

        RecyclerView orderItemsRecyclerView = view.findViewById(R.id.meals_recyclerview);
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MealsAdapter adapter = new MealsAdapter(alertDialogBuilder.create());
        orderItemsRecyclerView.setAdapter(adapter);

        FirebaseViewModel firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
        LiveData<DataSnapshot> appointmentsDataSnapshot = firebaseViewModel
                .getMealsLiveDataSnapshot();

        appointmentsDataSnapshot.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                adapter.showAppointment(dataSnapshot.getChildren());
            }
        });

        Button buttonConfirm = view.findViewById(R.id.confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(adapter.getSelected() != NO_SELECTION) {

                    //TODO acá debería borrarlo con firebase!!!
                    int pos = adapter.getSelected();
                    adapter.meals.remove(pos);
                    adapter.viewValues.remove(pos);
                    adapter.notifyItemRemoved(pos);



                }

            }
        });

    }

    /**
     * Adapter for the medical specialists / professionals recycler view.
     */
    private static class MealsAdapter extends RecyclerView.Adapter<MealItemsViewHolder> {

        List<Meal> meals = new ArrayList<>();
        private List<MealItemsViewHolder> viewValues = new ArrayList<>();
        MealsProvider appointmentsProvider = new MealsProvider();

        private AlertDialog dialog;

        MealsAdapter(AlertDialog dialog) {
            this.dialog = dialog;



        }

        public void showAppointment(Iterable< DataSnapshot > mealsData) {
            this.meals = new ArrayList<>();
            for (DataSnapshot appointmentData : mealsData) {
                HashMap appointmentsDataMap = (HashMap) appointmentData.getValue();
                String title = (String) appointmentsDataMap.get("title");
                String patientName = (String) appointmentsDataMap.get("patient_name");
                String desc = (String) appointmentsDataMap.get("desc");
                String status = (String) appointmentsDataMap.get("status");

                if(status.equals("pending")){
                    Meal a = new Meal(title, desc);
                    this.meals.add(a);
                }
            }

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MealItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_pending_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    v.setBackgroundColor(v.getResources().getColor(android.R.color.holo_green_light));

                    for(MealItemsViewHolder item: viewValues) {
                        if(item.itemView != v){
                            item.setSelected(false);
                            item.itemView.setBackgroundColor(v.getResources().getColor(android.R.color.transparent));

                        } else {
                            item.setSelected(true);
                        }
                    }
                }
            });

            return new MealItemsViewHolder(view, this.dialog);
        }

        @Override
        public void onBindViewHolder(@NonNull MealItemsViewHolder holder, int position) {
            holder.setPic(R.drawable.ic_main_viands_apple);
            holder.setName(meals.get(position).getNombre());
            holder.setDetalles(meals.get(position).getDescripcion());
            viewValues.add(holder);
        }

        @Override
        public int getItemCount() {
            return meals.size();
        }

        public int getSelected() {
            int selectedPos = MealsFragment.NO_SELECTION;

            for (MealItemsViewHolder item : viewValues) {
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
    private static class MealItemsViewHolder extends RecyclerView.ViewHolder {

        private ImageView pic;
        private TextView name;
        private String detalles = "";

        private boolean selected;

        MealItemsViewHolder(@NonNull View itemView, AlertDialog dialog) {
            super(itemView);
            pic = itemView.findViewById(R.id.meal_pic);
            name = itemView.findViewById(R.id.meal_name);

            itemView.findViewById(R.id.meal_details).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.setMessage(detalles);
                    dialog.show();
                }
            });

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


        public String getDetalles() {
            return detalles;
        }

        public void setDetalles(String detalles) {
            this.detalles = detalles;
        }
    }
}
