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


public class SearchViewdapter extends RecyclerView.Adapter<SearchViewdapter.PatientViewHolder> {

    private List<RegistrationModel> patientList;


    public SearchViewdapter(List<RegistrationModel> patientList) {
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


        String fod = model.getActualFollowupDate();

        try {
            if (fod == null || fod == "0000-00-00" || fod.equals("30-11-0002")) {
                holder.follow_up_date.setText("--");
            } else {
                holder.follow_up_date.setText(fod);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

           /* if (fod.equals("30-11-0002")) {

            holder.follow_up_date.setText("--");

        } else {
            holder.follow_up_date.setText(fod);
        }
*/


        int posi=position+1;
        String id=""+posi;
        holder.id.setText(id); // this will add i value in desc order ie fifo order as per client req. 25-8-16 ,this id is not stored in db.



        holder.age.setText(model.getAge());
        holder.phone_no.setText(model.getMobileNumber());




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
            id=(TextView)view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            gender=(TextView)view.findViewById(R.id.gender);

            age = (TextView) view.findViewById(R.id.age);
            phone_no=(TextView)view.findViewById(R.id.phno);
            follow_up_date=(TextView)view.findViewById(R.id.follow_up_date);

        }
    }

    public void setFilter(List<RegistrationModel> hotelModels) {
        patientList = new ArrayList<>();
        patientList.addAll(hotelModels);
        notifyDataSetChanged();
    }



}
