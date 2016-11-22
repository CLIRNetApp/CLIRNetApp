package app.clirnet.com.clirnetapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


/**
 * Created by ${Ashish} on 9/27/2016.
 */
public class FollowUpDateSearchAdapter extends RecyclerView.Adapter<FollowUpDateSearchAdapter.PatientViewHolder> {

    private List<RegistrationModel> patientList;
    private  AppController appController=new AppController();

    public FollowUpDateSearchAdapter(List<RegistrationModel> patientList) {
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

        String name=appController.toCamelCase(first_name + " " + middle_name + " " + last_name);

        holder.name.setText(name);



        String fod = model.getVisit_date();



        if (fod.equals("30-11-0002")) {

            holder.follow_up_date.setText("--");

        } else {
            holder.follow_up_date.setText(fod);
        }





        int posi=position+1;
        String id=""+posi;
        holder.id.setText(id); // this will add i value in desc order ie fifo order as per client req. 25-8-16 ,this id is not stored in db.
// this will add i value in desc order ie fifo order as per client req. 25-8-16 ,this id is not stored in db.



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
        private final TextView follow_up_date;
        public final TextView name;
        public final TextView gender;
        private final TextView id;
        public final TextView age;


        public PatientViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            gender = (TextView) view.findViewById(R.id.gender);

            age = (TextView) view.findViewById(R.id.age);
            phone_no = (TextView) view.findViewById(R.id.phno);
            follow_up_date = (TextView) view.findViewById(R.id.follow_up_date);

        }
    }

    public void setFilter(List<RegistrationModel> hotelModels) {
        patientList = new ArrayList<>();
        patientList.addAll(hotelModels);
        notifyDataSetChanged();
    }
}

