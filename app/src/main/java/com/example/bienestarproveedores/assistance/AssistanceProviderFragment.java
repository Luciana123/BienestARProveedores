package com.example.bienestarproveedores.assistance;

import android.app.AlertDialog;
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

public class AssistanceProviderFragment extends Fragment {

    private static int NO_SELECTION = 100000;

    private SharedPreferences sharedPref;
    private int userId;
    private String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.assistance_pending_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());

        super.onViewCreated(view, savedInstanceState);
        HeaderLayout header = view.findViewById(R.id.fragment_header);
        ImageView icon = view.findViewById(R.id.bienestarPLogo);
        header.setDescription(getString(R.string.assistance));
        header.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorAssistance));

        sharedPref = getContext().getSharedPreferences(
                getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);

        userId = sharedPref
                .getInt("id", new Integer(0));

        name = sharedPref.getString("name", "");

        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorAssistanceB));

        RecyclerView orderItemsRecyclerView = view.findViewById(R.id.assistants_recyclerview);
        orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        AssistancyAppointmentsAdapter adapter = new AssistancyAppointmentsAdapter(String.valueOf(this.userId), alertDialogBuilder.create());
        orderItemsRecyclerView.setAdapter(adapter);

        FirebaseViewModel firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
        LiveData<DataSnapshot> appointmentsDataSnapshot = firebaseViewModel
                .getAssistanceLiveSnapshot();

        overrideBackButton(view);


        appointmentsDataSnapshot.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                adapter.showAppointment(dataSnapshot.getChildren());
            }
        });

        Button buttonConfirm = view.findViewById(R.id.assistance_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedPostion = adapter.getSelected();

                if(selectedPostion != NO_SELECTION) {

                    AssistanceRequest m = adapter.assistanceRequests.get(selectedPostion);

                    adapter.assistanceRequests.remove(selectedPostion);
                    adapter.viewValues.remove(selectedPostion);
                    adapter.notifyItemRemoved(selectedPostion);



                    modifyStatusFirebase(m);

                }

            }
        });

    }

    private void modifyStatusFirebase(AssistanceRequest assistance){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Assistance");
        ref.child(assistance.getKey()).child("status").setValue("confirmed");
        ref.child(assistance.getKey()).child("assistant_name").setValue(this.name);
        ref.child(assistance.getKey()).child("assistant_id").setValue(String.valueOf(this.userId));
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

    /**
     * Adapter for the medical specialists / professionals recycler view.
     */
    private static class AssistancyAppointmentsAdapter extends RecyclerView.Adapter<AssistanceItemsViewHolder> {

        List<AssistanceRequest> assistanceRequests = new ArrayList<>();
        private List<AssistanceItemsViewHolder> viewValues = new ArrayList<>();
        private String userId;
        private AlertDialog dialog;

        public AssistancyAppointmentsAdapter(String userId, AlertDialog alertDialog){
            this.userId = userId;
            this.dialog = alertDialog;

        }

        public void showAppointment(Iterable<DataSnapshot> assistanceReqsData) {
            this.assistanceRequests = new ArrayList<>();
            for (DataSnapshot appointmentData : assistanceReqsData) {
                HashMap appointmentsDataMap = (HashMap) appointmentData.getValue();

                String assistantId = (String) appointmentsDataMap.get("assistant_id");
                String assistantName = (String) appointmentsDataMap.get("assistant_name");
                String clientId = (String) appointmentsDataMap.get("client_id");
                String clientName = (String) appointmentsDataMap.get("client_name");
                String details = (String) appointmentsDataMap.get("details");
                String type = (String) appointmentsDataMap.get("type");
                String status = (String) appointmentsDataMap.get("status");
                assert assistantId != null;


                if((status != null) && status.equals("pending")){
                    AssistanceRequest a = new AssistanceRequest(assistantId, assistantName, clientId, clientName, details, status, type, appointmentData.getKey());
                    this.assistanceRequests.add(a);
                }

            }

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public AssistanceItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assistance_pending_item, parent, false);

            view.setOnClickListener(v -> {
                v.setBackgroundColor(v.getResources().getColor(R.color.colorAssistanceSelected));
                for(AssistanceItemsViewHolder item: viewValues) {
                    if(item.itemView != v){
                        item.setSelected(false);
                        item.itemView.setBackgroundColor(item.itemView.getResources().getColor(R.color.colorWhite));
                    } else {
                        item.setSelected(true);
                    }
                }
            });

            return new AssistanceItemsViewHolder(view, this.dialog);
        }

        @Override
        public void onBindViewHolder(@NonNull AssistanceItemsViewHolder holder, int position) {
            //TODO: set profile picture and description.
            holder.setName(assistanceRequests.get(position).getClientName());
            holder.setDescription(assistanceRequests.get(position).getType());
            holder.setDetails(assistanceRequests.get(position).getDetails());
            viewValues.add(holder);
        }

        @Override
        public int getItemCount() {
            return assistanceRequests.size();
        }

        public int getSelected() {
            int selectedPos = AssistanceProviderFragment.NO_SELECTION;

            for (AssistanceItemsViewHolder item : viewValues) {
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
    private static class AssistanceItemsViewHolder extends RecyclerView.ViewHolder {

        private ImageView pic;
        private TextView name;
        private TextView desc;

        private String details = "";

        private boolean selected;

        AssistanceItemsViewHolder(@NonNull View itemView, AlertDialog dialog) {
            super(itemView);
            pic = itemView.findViewById(R.id.assistant_profile_pic);
            name = itemView.findViewById(R.id.assistant_name);
            desc = itemView.findViewById(R.id.assistance_description);

            itemView.findViewById(R.id.assistance_details).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.setMessage(details);
                    dialog.show();
                }
            });

        }

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

        void setDetails(String details) {
            this.details = details;
        }

    }
}
