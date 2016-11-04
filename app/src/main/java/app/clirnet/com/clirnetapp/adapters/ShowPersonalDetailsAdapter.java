package app.clirnet.com.clirnetapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


public class ShowPersonalDetailsAdapter extends RecyclerView.Adapter<ShowPersonalDetailsAdapter.HistoryViewHolder> {

    final List<RegistrationModel> patientList;

    public ShowPersonalDetailsAdapter(List<RegistrationModel> patientList) {
        this.patientList = patientList;

    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      /*  View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_seach_list, parent, false);*/

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_history_list, parent, false);


        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        RegistrationModel model = patientList.get(position);



        String follow_up_date = patientList.get(position).getFollowUpDate();


        try {
            if (follow_up_date == null) {
                holder.tv_fod.setText("--");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.tv_visit_date.setText(model.getVisit_date());

        holder.tv_ailment.setText(model.getAilments());

        String actFod = model.getActualFollowupDate();
        if (actFod.equals("30-11-0002")) {


            actFod = "--";

        }
        holder.tv_fod.setText(actFod);

        String clinicalNotes = model.getClinicalNotes().trim();
        if (clinicalNotes.length() > 0) {
            holder.tv_clinical_notes.setText(clinicalNotes);
        } else {
            holder.tv_clinical_notes.setText("No Aailable Notes");
        }


        clinicalNotes = model.getClinicalNotes().trim();
        if (clinicalNotes.length() > 0) {
            holder.tv_clinical_notes.setText(clinicalNotes);
        } else {
            holder.tv_clinical_notes.setText("No Aailable Notes");
        }

    }

    @Override
    public int getItemCount() {

        return patientList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_ailment;
        public final TextView tv_fod;
        private final TextView tv_visit_date;
        public final TextView tv_clinical_notes;


        public HistoryViewHolder(View view) {
            super(view);
            tv_visit_date = (TextView) view.findViewById(R.id.tv_visit_date);
            tv_ailment = (TextView) view.findViewById(R.id.tv_ailment);
            tv_fod = (TextView) view.findViewById(R.id.tv_fod);

            tv_clinical_notes = (TextView) view.findViewById(R.id.tv_clinical_notes);


        }
    }


}
