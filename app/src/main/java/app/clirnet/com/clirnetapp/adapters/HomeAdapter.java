package app.clirnet.com.clirnetapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


@SuppressWarnings("ALL")
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.PatientViewHolder> {

    private List<RegistrationModel> patientList;

    public HomeAdapter(List<RegistrationModel> patientList) {
        this.patientList = patientList;

    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_seach_list, parent, false);


        return new PatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        RegistrationModel model = patientList.get(position);

        String first_name = patientList.get(position).getFirstName();
        String middle_name = patientList.get(position).getMiddleName();
        String last_name = patientList.get(position).getLastName();

        holder.name.setText(first_name + " " + middle_name + " " + last_name);
       // Log.d("title", "" + patientList.get(position).getMobileNumber());

        holder.address.setText(model.getDob());

        holder.id.setText(model.getId());


        holder.age.setText(model.getAge());
        holder.phone_no.setText(model.getMobileNumber());


     //   String stringdistance = String.valueOf(model.getLanguage());//Need to convert to string to set into text view Ashish U 21-04-2016




        holder.gender.setText(model.getGender());


    }

    @Override
    public int getItemCount() {
        //   return bookList.size();
        return patientList.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {
        private final TextView phone_no;
        private final TextView address;
        public final TextView name;
        public final TextView gender;
        private final TextView id;
        public final TextView age;
        private String mItem;

        public PatientViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            gender = (TextView) view.findViewById(R.id.gender);

            age = (TextView) view.findViewById(R.id.age);
            phone_no = (TextView) view.findViewById(R.id.phno);
            address = (TextView) view.findViewById(R.id.follow_up_date);

        }
    }

    public void setFilter(List<RegistrationModel> hotelModels) {
        patientList = new ArrayList<>();
        patientList.addAll(hotelModels);
        notifyDataSetChanged();
    }


}
