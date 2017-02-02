package app.clirnet.com.clirnetapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.ShowPrescriptionImageActivity;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;



public class AddPatientUpdateAdapter  extends RecyclerView.Adapter<AddPatientUpdateAdapter.HistoryViewHolder> {

    private final List<RegistrationModel> patientList;
    private final AppController appController=new AppController();
    private final Context mContext;

    public AddPatientUpdateAdapter(Context context,List<RegistrationModel> patientList) {
        this.patientList = patientList;
        this.mContext=context;

    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_history_listenew2, parent, false);

        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        RegistrationModel model = patientList.get(position);


      //  String visit_date = patientList.get(position).getVisit_date();
       // String ailments = patientList.get(position).getAilments();
        String follow_up_date = patientList.get(position).getActualFollowupDate();


        try{
            if(follow_up_date == null || follow_up_date .equals("0000-00-00") || follow_up_date.equals("30-11-0002")|| follow_up_date.equals("") ){
                holder.tv_fod.setText("--");
            }
            else{
                holder.tv_fod.setText(follow_up_date);
            }

        }
        catch(Exception e){
            e.printStackTrace();
            appController.appendLog(appController.getDateTime()+" " +"/ "+"Addpatient update Adapter"+e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        holder.tv_visit_date.setText(model.getVisit_date());

        holder.tv_ailment.setText(model.getAilments());


        String clinicalNotes = model.getClinicalNotes().trim();
        if(clinicalNotes.length() > 0){
            holder.tv_clinical_notes.setText(clinicalNotes);
        }
        else{
            holder.tv_clinical_notes.setText("No Available Notes");
        }

        String mSymptoms = model.getSymptoms();
        if (mSymptoms != null && !mSymptoms.equals("") && mSymptoms.length()>0) {
            holder.tv_symptoms.setText(mSymptoms);
        }else{
            holder.linearlayoutSymptoms.setVisibility(View.GONE);
        }

        String mDiagnosis = model.getDignosis();
        if (mDiagnosis != null && !mDiagnosis.equals("") && mDiagnosis.length()>0) {
            holder.tv_diagnosis.setText(mDiagnosis);
        }else{
            holder.linearlayoutDiagnosis.setVisibility(View.GONE);
        }

        try {
            final String imgPath = patientList.get(position).getPres_img();
            if (!TextUtils.isEmpty(imgPath)) {

                holder.imgText.setText("View Prescription");
                holder.imgText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(mContext, "PATH IS: " + imgPath.trim(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(mContext, ShowPrescriptionImageActivity.class);
                        i.putExtra("PRESCRIPTIONIMAGE", imgPath);
                        mContext.startActivity(i);
                    }
                });

            }else{
                holder.imgText.setText("No Prescription Attached");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
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
        private final TextView imgText;
        private final LinearLayout linearlayoutSymptoms;
        private final LinearLayout linearlayoutDiagnosis;
        private final TextView  tv_diagnosis, tv_symptoms;

        public HistoryViewHolder(View view) {
            super(view);
            tv_visit_date = (TextView) view.findViewById(R.id.tv_visit_date);
            tv_ailment = (TextView) view.findViewById(R.id.tv_ailment);
            tv_fod = (TextView) view.findViewById(R.id.tv_fod);
            imgText=(TextView)view.findViewById(R.id.imgText);
            tv_clinical_notes = (TextView) view.findViewById(R.id.tv_clinical_notes);
            tv_diagnosis = (TextView) view.findViewById(R.id.tv_diagnosis);
            tv_symptoms = (TextView) view.findViewById(R.id.tv_symptoms);

            linearlayoutSymptoms = (LinearLayout) view.findViewById(R.id.linearlayoutSymptoms);
            linearlayoutDiagnosis = (LinearLayout) view.findViewById(R.id.linearlayoutDiagnosis);

        }
    }




}
