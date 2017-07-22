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


public class ShowPersonalDetailsAdapter extends RecyclerView.Adapter<ShowPersonalDetailsAdapter.HistoryViewHolder> {

    private final List<RegistrationModel> patientList;
    private final Context mContext;


    public ShowPersonalDetailsAdapter(Context context,List<RegistrationModel> patientList) {
        this.patientList = patientList;
        this.mContext=context;

    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      /*  View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_seach_list, parent, false);*/

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_history_listenew2, parent, false);


        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        RegistrationModel model = patientList.get(position);


        AppController appController = new AppController();
        String follow_up_date = patientList.get(position).getFollowUpDate();

        try {
            if (follow_up_date == null) {
                holder.tv_fod.setText("--");
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "ShowPersonalDetailsAdapter" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        holder.tv_visit_date.setText(model.getVisit_date());



        String actFod = model.getActualFollowupDate();

        if (actFod == null || actFod.equals("30-11-0002") || actFod.equals("0000-00-00")|| actFod.equals("")) {

            holder.tv_fod.setText("--");

        }else {
            holder.tv_fod.setText(actFod);
        }

        String clinicalNotes = model.getClinicalNotes().trim();
        if (clinicalNotes.length() > 0) {
            holder.tv_clinical_notes.setText(clinicalNotes);
        } else {
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
        String strWeight=model.getWeight();
        String strHeight=model.getHeight();
        String strBmi=model.getBmi();
        String strTemp=model.getTemprature();
        String strSystole=model.getBp();
        String strDistole=model.getlowBp();
        String strSugarpp=model.getSugar();
        String strSugarFasting=model.getSugarFasting();
        String strPulse=model.getPulse();
        String strSpo2=model.getSpo2();
        String strRespiration=model.getRespirataion();

        String strHbA1c=model.getHba1c();
        String strAcer=model.getAcer();
        String strSeremUrea=model.getSeremUrea();
        String strHdl=model.getLipidProfileHdl();
        String strTc=model.getLipidProfileTc();
        String strTg=model.getLipidProfileTg();
        String strLdl=model.getLipidProfileLdl();
        String strVhdl=model.getLipidProfileVhdl();
        String strEcg=model.getEcg();
        String strPft=model.getPft();
        String strPallor=model.getPallor();
        String strCyanosis=model.getCyanosis();
        String strTremors=model.getTremors();
        String strIcterus=model.getIcterus();
        String strClubbing=model.getClubbing();
        String strOedema=model.getOedema();
        String strCalfTenderness=model.getCalfTenderness();
        String strLymphadenopathy=model.getLymphadenopathy();
        String strObesity=model.getObesity();


        try {

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
            if (strSpo2 != null && !strSpo2.equals("") && strSpo2.length()>0) {
                holder.tv_spo2.setText(strSpo2);
            }else{
                holder.tv_spo2.setVisibility(View.GONE);
                holder.txtSpo2.setVisibility(View.GONE);
            }
            if (strRespiration != null && !strRespiration.equals("") && strRespiration.length()>0) {
                holder.tv_respiration.setText(strRespiration);
            }else{
                holder.tv_respiration.setVisibility(View.GONE);
                holder.txtRespiration.setVisibility(View.GONE);
            }



            if (strObesity!= null && !strObesity.equals("") && strObesity.length()>0) {
                holder.tv_obesity.setText(strObesity);
            }else{
                holder.tv_obesity.setVisibility(View.GONE);
                holder.txtObesity.setVisibility(View.GONE);
            }
            if (strHbA1c != null && !strHbA1c.equals("") && strHbA1c.length()>0) {
                holder.tv_hba1c.setText(strHbA1c);
            }else{
                holder.tv_hba1c.setVisibility(View.GONE);
                holder.txtHbA1c.setVisibility(View.GONE);
            }
            if (strAcer != null && !strAcer.equals("") && strAcer.length()>0) {
                holder.tv_acer.setText(strAcer);
            }else{
                holder.tv_acer.setVisibility(View.GONE);
                holder.txtAcer.setVisibility(View.GONE);
            }
            if (strSeremUrea != null && !strSeremUrea.equals("") && strSeremUrea.length()>0) {
                holder.tv_seremurea.setText(strSeremUrea);
            }else{
                holder.tv_seremurea.setVisibility(View.GONE);
                holder.txtSerumUrea.setVisibility(View.GONE);
            }
            if (strHdl != null && !strHdl.equals("") && strHdl.length()>0) {
                holder.tv_hdl.setText(strHdl);
            }else{
                holder.tv_hdl.setVisibility(View.GONE);
                holder.txtHdl.setVisibility(View.GONE);
            }
            if (strTc != null && !strTc.equals("") && strTc.length()>0) {
                holder.tv_tc.setText(strTc);
            }else{
                holder.tv_tc.setVisibility(View.GONE);
                holder.txtTc.setVisibility(View.GONE);
            }
            if (strTg != null && !strTg.equals("") && strTg.length()>0) {
                holder.tv_tg.setText(strTg);
            }else{
                holder.tv_tg.setVisibility(View.GONE);
                holder.txtTg.setVisibility(View.GONE);
            }

            if (strVhdl != null && !strVhdl.equals("") && strVhdl.length()>0) {
                holder.tv_vhdl.setText(strVhdl);
            }else{
                holder.tv_vhdl.setVisibility(View.GONE);
                holder.txtVhdl.setVisibility(View.GONE);
            }
            if (strLdl != null && !strLdl.equals("") && strLdl.length()>0) {
                holder.tv_ldl.setText(strLdl);
            }else{
                holder.tv_ldl.setVisibility(View.GONE);
                holder.txtLdl.setVisibility(View.GONE);
            }
            if (strEcg != null && !strEcg.equals("") && strEcg.length()>0) {
                holder.tv_ecg.setText(strEcg);
            }else{
                holder.tv_ecg.setVisibility(View.GONE);
                holder.txtEcg.setVisibility(View.GONE);
            }
            if (strPft != null && !strPft.equals("") && strPft.length()>0) {
                holder.tv_pft.setText(strPft);
            }else{
                holder.tv_pft.setVisibility(View.GONE);
                holder.txtPft.setVisibility(View.GONE);
            }
            if (strPallor != null && !strPallor.equals("") && strPallor.length()>0) {
                holder.tv_pallor.setText(strPallor);
            }else{
                holder.tv_pallor.setVisibility(View.GONE);
                holder.txtPallor.setVisibility(View.GONE);
            }
            if (strCyanosis != null && !strCyanosis.equals("") && strCyanosis.length()>0) {
                holder.tv_cyanosis.setText(strCyanosis);
            }else{
                holder.tv_cyanosis.setVisibility(View.GONE);
                holder.txtCyanosis.setVisibility(View.GONE);
            }
            if (strIcterus != null && !strIcterus.equals("") && strIcterus.length()>0) {
                holder.tv_icterus.setText(strIcterus);
            }else{
                holder.tv_icterus.setVisibility(View.GONE);
                holder.txtIcterus.setVisibility(View.GONE);
            }
            if (strTremors != null && !strTremors.equals("") && strTremors.length()>0) {
                holder.tv_tremors.setText(strTremors);
            }else{
                holder.tv_tremors.setVisibility(View.GONE);
                holder.txtTremors.setVisibility(View.GONE);
            }
            if (strClubbing != null && !strClubbing.equals("") && strClubbing.length()>0) {
                holder.tv_clubbing.setText(strClubbing);
            }else{
                holder.tv_clubbing.setVisibility(View.GONE);
                holder.txtClubbing.setVisibility(View.GONE);
            }
            if (strOedema != null && !strOedema.equals("") && strOedema.length()>0) {
                holder.tv_oedema.setText(strOedema);
            }else{
                holder.tv_oedema.setVisibility(View.GONE);
                holder.txtOedema.setVisibility(View.GONE);
            }
            if (strCalfTenderness != null && !strCalfTenderness.equals("") && strCalfTenderness.length()>0) {
                holder.tv_calfTenderness.setText(strCalfTenderness);
            }else{
                holder.tv_calfTenderness.setVisibility(View.GONE);
                holder.txtCalfTenderness.setVisibility(View.GONE);
            }
            if (strLymphadenopathy != null && !strLymphadenopathy.equals("") && strLymphadenopathy.length()>0) {
                holder.tv_lymphadenopathy.setText(strLymphadenopathy);
            }else{
                holder.tv_lymphadenopathy.setVisibility(View.GONE);
                holder.txtLymphadenopathy.setVisibility(View.GONE);
            }

            if(strWeight!=null && !strWeight.equals("") || strHeight!=null && !strHeight.equals("")  ||strBmi!=null && !strBmi.equals("")  || strObesity!=null && !strObesity.equals("")  || strPulse!=null && !strPulse.equals("")  || strTemp!=null && !strTemp.equals("")  || strSystole!=null && !strSystole.equals("") || strDistole!=null && !strDistole.equals("")  || strSpo2!=null && !strSpo2.equals("") || strRespiration!=null && !strRespiration.equals("") ){
                holder.txtVitals.setVisibility(View.VISIBLE);
            }
            if(strClubbing!=null && !strClubbing.equals("") || strOedema!=null && !strOedema.equals("") ||strCalfTenderness!=null && !strCalfTenderness.equals("") || strLymphadenopathy!=null && !strLymphadenopathy.equals("")){
                holder.txtObservations.setVisibility(View.VISIBLE);
            }

            if(strSugarFasting!=null && !strSugarFasting.equals("") || strSugarpp!=null && !strSugarpp.equals("") ||strObesity!=null && !strObesity.equals("") || strHbA1c!=null && !strHbA1c.equals("")|| strAcer!=null && !strAcer.equals("") || strSeremUrea!=null && !strSeremUrea.equals("") || strHdl!=null && !strHdl.equals("") || strTc!=null && !strTc.equals("") || strTg!=null && !strTg.equals("")
                    || strLdl!=null && !strLdl.equals("")|| strVhdl!=null && !strVhdl.equals("")){
                holder.txtInvestigation.setVisibility(View.VISIBLE);
            }

            final String imgPath = patientList.get(position).getPres_img();
            if (imgPath != null && !TextUtils.isEmpty(imgPath)) {

                holder.imgText.setText("View Prescription");
               // holder.llayout.setBackgroundColor(R.color.btn_back_sbmt);
                holder.imgText.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.imgText.setTextColor(mContext.getResources().getColor(R.color.white));
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


        private final TextView tv_fod;
        private final TextView tv_visit_date;
        private final TextView tv_clinical_notes;
        private final TextView imgText;
        private final LinearLayout linearlayoutSymptoms;
        private final LinearLayout linearlayoutDiagnosis;

        private final TextView  tv_diagnosis, tv_symptoms;
        private final TextView  tv_weight, tv_height,tv_bmi,tv_temp,tv_systole,tv_diastole,tv_pulse,tv_sugar_pp,tv_sugar_fasting;
        private final TextView  txtWeight, txtHeight,txtBmi,txtTemp,txtSystole,txtDiastole,txtPulse,txtSugarPp,txtSugarFast;

        private TextView txtSpo2,tv_spo2;
        private  TextView  txtRespiration, tv_respiration;
        private TextView txtHbA1c,txtAcer,txtSerumUrea,txtHdl,txtTc,txtTg,txtLdl,txtVhdl;
        private  TextView  tv_hba1c, tv_acer,tv_seremurea,tv_hdl,tv_tc,tv_tg,tv_ldl,tv_vhdl;

        private TextView txtEcg,txtPft,txtPallor,txtCyanosis,txtTremors,txtIcterus,txtClubbing,txtOedema,txtCalfTenderness,txtLymphadenopathy;
        private  TextView  tv_ecg,tv_pft,tv_pallor,tv_cyanosis,tv_tremors,tv_icterus,tv_clubbing,tv_oedema,tv_calfTenderness,tv_lymphadenopathy;
         private  TextView txtObesity,tv_obesity;
        private TextView txtVitals,txtInvestigation,txtObservations;
        HistoryViewHolder(View view) {
            super(view);
            tv_visit_date = (TextView) view.findViewById(R.id.tv_visit_date);

            tv_fod = (TextView) view.findViewById(R.id.tv_fod);
            imgText=(TextView)view.findViewById(R.id.imgText);
            tv_clinical_notes = (TextView) view.findViewById(R.id.tv_clinical_notes);

            tv_diagnosis = (TextView) view.findViewById(R.id.tv_diagnosis);
            tv_symptoms = (TextView) view.findViewById(R.id.tv_symptoms);

            linearlayoutSymptoms = (LinearLayout) view.findViewById(R.id.linearlayoutSymptoms);
            linearlayoutDiagnosis = (LinearLayout) view.findViewById(R.id.linearlayoutDiagnosis);


            tv_weight = (TextView) view.findViewById(R.id.tv_weight);
            tv_height = (TextView) view.findViewById(R.id.tv_height);

            tv_bmi = (TextView) view.findViewById(R.id.tv_bmi);
            tv_temp = (TextView) view.findViewById(R.id.tv_temp);
            tv_spo2 = (TextView) view.findViewById(R.id.tv_spo2);
            tv_respiration = (TextView) view.findViewById(R.id.tv_respiration);
            tv_obesity = (TextView) view.findViewById(R.id.tv_obesity);

            tv_systole = (TextView) view.findViewById(R.id.tv_systole);
            tv_diastole = (TextView) view.findViewById(R.id.tv_diastole);
            tv_pulse=(TextView)view.findViewById(R.id.tv_pulse);
            tv_sugar_pp=(TextView)view.findViewById(R.id.tv_sugar_pp);
            tv_sugar_fasting=(TextView)view.findViewById(R.id.tv_sugar_fasting);

            txtWeight = (TextView) view.findViewById(R.id.txtWeight);
            txtHeight = (TextView) view.findViewById(R.id.txtHeight);

            txtBmi = (TextView) view.findViewById(R.id.txtBmi);
            txtTemp = (TextView) view.findViewById(R.id.txtTemp);

            txtEcg = (TextView) view.findViewById(R.id.txtEcg);
            tv_ecg = (TextView) view.findViewById(R.id.tv_ecg);
            txtPft = (TextView) view.findViewById(R.id.txtPft);
            tv_pft = (TextView) view.findViewById(R.id.tv_pft);

            txtSystole = (TextView) view.findViewById(R.id.txtSystole);
            txtDiastole = (TextView) view.findViewById(R.id.txtDiastole);
            txtPulse=(TextView)view.findViewById(R.id.txtPulse);
            txtSugarPp=(TextView)view.findViewById(R.id.txtSugarPp);
            txtSugarFast=(TextView)view.findViewById(R.id.txtSugarFast);
            txtSpo2 = (TextView) view.findViewById(R.id.txtSpo2);
            txtRespiration = (TextView) view.findViewById(R.id.txtRespiration);
            txtObesity = (TextView) view.findViewById(R.id.txtObesity);

            txtHbA1c = (TextView) view.findViewById(R.id.txtHbA1c);
            tv_hba1c = (TextView) view.findViewById(R.id.tv_hba1c);
            txtAcer=(TextView)view.findViewById(R.id.txtAcer);
            tv_acer=(TextView)view.findViewById(R.id.tv_acer);
            txtSerumUrea=(TextView)view.findViewById(R.id.txtSerumUrea);
            tv_seremurea = (TextView) view.findViewById(R.id.tv_seremurea);
            txtHdl = (TextView) view.findViewById(R.id.txtHdl);
            tv_hdl=(TextView)view.findViewById(R.id.tv_hdl);
            txtTc = (TextView) view.findViewById(R.id.txtTc);
            tv_tc = (TextView) view.findViewById(R.id.tv_tc);
            txtTg = (TextView) view.findViewById(R.id.txtTg);
            tv_tg = (TextView) view.findViewById(R.id.tv_tg);
            txtLdl = (TextView) view.findViewById(R.id.txtLdl);
            tv_ldl = (TextView) view.findViewById(R.id.tv_ldl);
            txtVhdl = (TextView) view.findViewById(R.id.txtVhdl);
            tv_vhdl = (TextView) view.findViewById(R.id.tv_vhdl);


            txtPallor = (TextView) view.findViewById(R.id.txtPallor);
            tv_pallor = (TextView) view.findViewById(R.id.tv_pallor);
            txtCyanosis = (TextView) view.findViewById(R.id.txtCyanosis);
            tv_cyanosis = (TextView) view.findViewById(R.id.tv_cyanosis);
            txtTremors = (TextView) view.findViewById(R.id.txtTremors);
            tv_tremors = (TextView) view.findViewById(R.id.tv_tremors);
            txtIcterus = (TextView) view.findViewById(R.id.txtIcterus);
            tv_icterus = (TextView) view.findViewById(R.id.tv_icterus);
            txtClubbing = (TextView) view.findViewById(R.id.txtClubbing);
            tv_clubbing = (TextView) view.findViewById(R.id.tv_clubbing);
            txtOedema = (TextView) view.findViewById(R.id.txtOedema);
            tv_oedema = (TextView) view.findViewById(R.id.tv_oedema);
            tv_icterus = (TextView) view.findViewById(R.id.tv_icterus);
            txtCalfTenderness = (TextView) view.findViewById(R.id.txtCalfTenderness);
            tv_calfTenderness = (TextView) view.findViewById(R.id.tv_calfTenderness);
            txtLymphadenopathy = (TextView) view.findViewById(R.id.txtLymphadenopathy);
            tv_lymphadenopathy = (TextView) view.findViewById(R.id.tv_lymphadenopathy);
            txtVitals=(TextView)view.findViewById(R.id.txtVitals);
            txtInvestigation=(TextView)view.findViewById(R.id.txtInvestigation);
            txtObservations=(TextView)view.findViewById(R.id.txtObservations);

        }
    }


}
