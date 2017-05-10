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
            appController.appendLog(appController.getDateTime()+" " +"/ "+"Addpatient update Adapter " + e + " Line Number "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        holder.tv_visit_date.setText(model.getVisit_date());

        String mAilment=model.getAilments();
        if (mAilment != null && !mAilment.equals("") && mAilment.length()>0) {
            holder.tv_ailment.setText(mAilment);
        }else{
            holder.linearlayoutAilment.setVisibility(View.GONE);
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

        String clinicalNotes = model.getClinicalNotes().trim();
        if(clinicalNotes.length() > 0){
            holder.tv_clinical_notes.setText(clinicalNotes);
        }
        else{
            holder.tv_clinical_notes.setText("No Available Notes");
        }


        String strWeight=model.getWeight();
        String strHeight=model.getHeight();
        String strBmi=model.getBmi();
        String strTemp=model.getTemprature();
        String strSystole=model.getBp();
        String strDistole=model.getlowBp();
        String strSugarpp=model.getSugar();
        String strSugarFasting=model.getSugarFasting();
        String strPulse=model.getPulse();

        /*if(strWeight!=null || strHeight!=null || strBmi!=null && !strBmi.equals("0.0") || strTemp!=null || strSystole !=null
                || strDistole !=null || strSugarpp !=null || strSugarFasting !=null || strPulse !=null){
            holder.vitalsLayout.setVisibility(View.VISIBLE);
        }*/
        if (strWeight != null && !strWeight.equals("") && strWeight.length()>0) {
            holder.tv_weight.setText(strWeight);
        }else{
            holder.tv_weight.setVisibility(View.GONE);
            holder.txtWeight.setVisibility(View.GONE);
        }
        if (strHeight != null && !strHeight.equals("") && strHeight.length()>0) {
            holder.tv_height.setText(strHeight);
        }else{
            holder.tv_height.setVisibility(View.GONE);
            holder.txtHeight.setVisibility(View.GONE);
        }
        if (strBmi != null && !strBmi.equals("") && !strBmi.trim().equals("0.0") && strBmi.length()>0) {
            holder.tv_bmi.setText(strBmi);
        }else{
            holder.tv_bmi.setVisibility(View.GONE);
            holder.txtBmi.setVisibility(View.GONE);
        }
        if (strTemp != null && !strTemp.equals("") && strTemp.length()>0) {
            holder.tv_temp.setText(strTemp);
        }else{
            holder.tv_temp.setVisibility(View.GONE);
            holder.txtTemp.setVisibility(View.GONE);
        }
        if (strSystole != null && !strSystole.equals("") && strSystole.length()>0) {
            holder.tv_systole.setText(strSystole);
        }else{
            holder.tv_systole.setVisibility(View.GONE);
            holder.txtSystole.setVisibility(View.GONE);

        }
        if (strDistole != null && !strDistole.equals("") && strDistole.length()>0) {
            holder.tv_diastole.setText(strDistole);
        }else{
            holder.tv_diastole.setVisibility(View.GONE);
            holder.txtDiastole.setVisibility(View.GONE);
        }
        if (strPulse != null && !strPulse.equals("") && strPulse.length()>0) {
            holder.tv_pulse.setText(strPulse);
        }else{
            holder.tv_pulse.setVisibility(View.GONE);
            holder.txtPulse.setVisibility(View.GONE);
        }
        if (strSugarpp != null && !strSugarpp.equals("") && strSugarpp.length()>0) {
            holder.tv_sugar_pp.setText(strSugarpp);
        }else{
            holder.tv_sugar_pp.setVisibility(View.GONE);
            holder.txtSugarPp.setVisibility(View.GONE);
        }
        if (strSugarFasting != null && !strSugarFasting.equals("") && strSugarFasting.length()>0) {
            holder.tv_sugar_fasting.setText(strSugarFasting);
        }else{
            holder.tv_sugar_fasting.setVisibility(View.GONE);
            holder.txtSugarFast.setVisibility(View.GONE);
        }
        try {
            final String imgPath = patientList.get(position).getPres_img();
            if (!TextUtils.isEmpty(imgPath)) {
                holder.imgText.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.imgText.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.imgText.setText("View Prescription");
                holder.imgText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(mContext, ShowPrescriptionImageActivity.class);
                        i.putExtra("PRESCRIPTIONIMAGE", imgPath);
                        mContext.startActivity(i);
                    }
                });

            }else{
                    holder.imgText.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                    holder.imgText.setTextColor(mContext.getResources().getColor(R.color.white));
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

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_ailment;
        private final TextView tv_fod;
        private final TextView tv_visit_date;
        private final TextView tv_clinical_notes;
        private final TextView imgText;
        private final LinearLayout linearlayoutSymptoms;
        private final LinearLayout linearlayoutDiagnosis,linearlayoutAilment;
        private final TextView  tv_diagnosis, tv_symptoms;
        private final TextView  tv_weight, tv_height,tv_bmi,tv_temp,tv_systole,tv_diastole,tv_pulse,tv_sugar_pp,tv_sugar_fasting;
        private final TextView  txtWeight, txtHeight,txtBmi,txtTemp,txtSystole,txtDiastole,txtPulse,txtSugarPp,txtSugarFast;
        private final LinearLayout vitalsLayout;
        HistoryViewHolder(View view) {
            super(view);
            tv_visit_date = (TextView) view.findViewById(R.id.tv_visit_date);
            tv_ailment = (TextView) view.findViewById(R.id.tv_ailment);
            tv_fod = (TextView) view.findViewById(R.id.tv_fod);
            imgText=(TextView)view.findViewById(R.id.imgText);
            tv_clinical_notes = (TextView) view.findViewById(R.id.tv_clinical_notes);
            tv_diagnosis = (TextView) view.findViewById(R.id.tv_diagnosis);
            tv_symptoms = (TextView) view.findViewById(R.id.tv_symptoms);

            tv_weight = (TextView) view.findViewById(R.id.tv_weight);
            tv_height = (TextView) view.findViewById(R.id.tv_height);

            tv_bmi = (TextView) view.findViewById(R.id.tv_bmi);
            tv_temp = (TextView) view.findViewById(R.id.tv_temp);

            tv_systole = (TextView) view.findViewById(R.id.tv_systole);
            tv_diastole = (TextView) view.findViewById(R.id.tv_diastole);
            tv_pulse=(TextView)view.findViewById(R.id.tv_pulse);
            tv_sugar_pp=(TextView)view.findViewById(R.id.tv_sugar_pp);
            tv_sugar_fasting=(TextView)view.findViewById(R.id.tv_sugar_fasting);

            txtWeight = (TextView) view.findViewById(R.id.txtWeight);
            txtHeight = (TextView) view.findViewById(R.id.txtHeight);

            txtBmi = (TextView) view.findViewById(R.id.txtBmi);
            txtTemp = (TextView) view.findViewById(R.id.txtTemp);

            txtSystole = (TextView) view.findViewById(R.id.txtSystole);
            txtDiastole = (TextView) view.findViewById(R.id.txtDiastole);
            txtPulse=(TextView)view.findViewById(R.id.txtPulse);
            txtSugarPp=(TextView)view.findViewById(R.id.txtSugarPp);
            txtSugarFast=(TextView)view.findViewById(R.id.txtSugarFast);

            linearlayoutSymptoms = (LinearLayout) view.findViewById(R.id.linearlayoutSymptoms);
            linearlayoutDiagnosis = (LinearLayout) view.findViewById(R.id.linearlayoutDiagnosis);
            linearlayoutAilment = (LinearLayout) view.findViewById(R.id.linearlayoutAilment);
            vitalsLayout=(LinearLayout)view.findViewById(R.id.vitalsLayout);
        }
    }
}
