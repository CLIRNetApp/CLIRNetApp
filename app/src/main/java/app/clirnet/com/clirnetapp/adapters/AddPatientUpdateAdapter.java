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
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;

public class AddPatientUpdateAdapter extends RecyclerView.Adapter<AddPatientUpdateAdapter.HistoryViewHolder> {

    private final List<RegistrationModel> patientList;
    private final AppController appController = new AppController();
    private final Context mContext;
    private StringBuilder sbVitals;
    private StringBuilder sbObservations;
    private StringBuilder sbInvestigations;
    private StringBuilder sbReferrals;
    private SQLController sqlController;

    public AddPatientUpdateAdapter(Context context, List<RegistrationModel> patientList) {
        this.patientList = patientList;
        this.mContext = context;
        sbVitals = new StringBuilder();
        sbObservations = new StringBuilder();
        sbInvestigations = new StringBuilder();
        sbReferrals = new StringBuilder();
        sqlController = new SQLController(context);
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

        try {
            if (follow_up_date == null || follow_up_date.equals("0000-00-00") || follow_up_date.equals("30-11-0002") || follow_up_date.equals("")) {
                holder.tv_fod.setText(": --");
            } else {
                holder.tv_fod.setText(": " + follow_up_date);
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "AddPatient update Adapter " + e + " Line Number " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        holder.tv_visit_date.setText(": " + model.getVisit_date());


        String mSymptoms = model.getSymptoms();
        if (mSymptoms != null && !mSymptoms.equals("") && mSymptoms.length() > 0) {
            holder.tv_symptoms.setText(": " + mSymptoms);
        } else {
            holder.linearlayoutSymptoms.setVisibility(View.GONE);
        }

        String mDiagnosis = model.getDignosis();
        if (mDiagnosis != null && !mDiagnosis.equals("") && mDiagnosis.length() > 0) {
            holder.tv_diagnosis.setText(": " + mDiagnosis);
        } else {
            holder.linearlayoutDiagnosis.setVisibility(View.GONE);
        }

        String clinicalNotes = model.getClinicalNotes().trim();
        if (clinicalNotes.length() > 0) {
            holder.tv_clinical_notes.setText(": " + clinicalNotes);
        } else {
            holder.tv_clinical_notes.setText(": No Available Notes");
        }


        String strWeight = model.getWeight();
        String strHeight = model.getHeight();
        String strBmi = model.getBmi();
        String strTemp = model.getTemprature();
        String strSystole = model.getBp();
        String strDistole = model.getlowBp();

        String strPulse = model.getPulse();
        String strSpo2 = model.getSpo2();
        String strRespiration = model.getRespirataion();
        String strObesity = model.getObesity();

        String strReferedById = model.getReferedBy();
        String strReferedToId = model.getReferedTo();


        if (strReferedToId != null && !strReferedToId.equals("")) {

            boolean isFound = strReferedToId.contains(",");

            if (isFound) {
                strReferedToId = strReferedToId.substring(0, strReferedToId.indexOf(","));
            }
        }

        try {
            sqlController.open();

            String referedBy = null, strSpecialityReferrel = null;
            if (strReferedById != null && !strReferedById.equals("")) {
                referedBy = sqlController.getNameByIdAssociateMaster(strReferedById);
                strSpecialityReferrel = sqlController.getSpciality(strReferedById);
            }


            if (referedBy != null && !referedBy.equals("")) {
                sbReferrals.append("Referred By - ").append(referedBy);//.append(" - ").append(strSpecialityReferrel).append(" ;   ");
                if (strSpecialityReferrel != null && !strSpecialityReferrel.equals("")) {
                    sbReferrals.append(" ( ").append(strSpecialityReferrel).append(" ) ;   ");
                } else {
                    sbReferrals.append(" ;   ");
                }
            }

            String referedTo = null, strSpecialityReferrelTo = null;

            if (strReferedToId != null && !strReferedToId.equals("")) {
                referedTo = sqlController.getNameByIdAssociateMaster(strReferedToId);
                strSpecialityReferrelTo = sqlController.getSpciality(strReferedToId);
            }

            if (referedTo != null && !referedTo.equals("")) {
                sbReferrals.append("Referred To - ").append(referedTo);

                if (strSpecialityReferrelTo != null && !strSpecialityReferrelTo.equals("")) {
                    sbReferrals.append(" ( ").append(strSpecialityReferrelTo).append(" )");
                }
                holder.showReferralsLayout.setVisibility(View.VISIBLE);
                holder.showReferralsData.setText(" Referred To - " + referedTo);
            }
            if (referedBy != null && !referedBy.equals("") || referedTo != null && !referedTo.equals("")) {
                holder.showReferralsLayout.setVisibility(View.VISIBLE);
                holder.showReferralsData.setText(sbReferrals);
            } else {
                holder.showReferralsLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (strWeight != null && !strWeight.equals("") && strWeight.length() > 0) {
            sbVitals.append("Weight - ").append(strWeight).append(" ; ");
        }

        if (strHeight != null && !strHeight.equals("") && strHeight.length() > 0) {

            sbVitals.append("Height - ").append(strHeight).append(" ; ");
        }

        if (strBmi != null && !strBmi.equals("") && strBmi.length() > 0) {

            sbVitals.append("BMI - ").append(strBmi).append(" ; ");
        }
        if (strObesity != null && !strObesity.equals("") && strObesity.length() > 0) {

            sbVitals.append("Obesity - ").append(strObesity).append(" ; ");
        }
        if (strPulse != null && !strPulse.equals("") && strPulse.length() > 0) {

            sbVitals.append("Pulse - ").append(strPulse).append(" ; ");
        }
        if (strSystole != null && !strSystole.equals("") && strSystole.length() > 0) {

            sbVitals.append("Systole - ").append(strSystole).append(" ; ");
        }
        if (strDistole != null && !strDistole.equals("") && strDistole.length() > 0) {

            sbVitals.append("Diastole - ").append(strDistole).append(" ;  ");
        }
        if (strTemp != null && !strTemp.equals("") && strTemp.length() > 0) {

            sbVitals.append("Temperature - ").append(strTemp).append(" ; ");
        }
        if (strSpo2 != null && !strSpo2.equals("") && strSpo2.length() > 0) {

            sbVitals.append("SpO2 - ").append(strSpo2).append(" ; ");
        }
        if (strRespiration != null && !strRespiration.equals("") && strRespiration.length() > 0) {

            sbVitals.append("Respiration Rate - ").append(strRespiration).append(" ; ");
        }

        if (strWeight != null && !strWeight.equals("") || strHeight != null && !strHeight.equals("") || strBmi != null && !strBmi.equals("") || strObesity != null && !strObesity.equals("") || strPulse != null && !strPulse.equals("") || strTemp != null && !strTemp.equals("") || strSystole != null && !strSystole.equals("") || strDistole != null && !strDistole.equals("") ||
                strSpo2 != null && !strSpo2.equals("") || strRespiration != null && !strRespiration.equals("")) {
            holder.showVitalsLayout.setVisibility(View.VISIBLE);
            holder.showVitalsData.setText(sbVitals);
        }

        String strSugarpp = model.getSugar();
        String strSugarFasting = model.getSugarFasting();
        String strHbA1c = model.getHba1c();
        String strAcer = model.getAcer();
        String strSeremUrea = model.getSeremUrea();
        String strHdl = model.getLipidProfileHdl();
        String strTc = model.getLipidProfileTc();
        String strTg = model.getLipidProfileTg();
        String strLdl = model.getLipidProfileLdl();
        String strVhdl = model.getLipidProfileVhdl();
        String strEcg = model.getEcg();
        String strPft = model.getPft();

        if (strSugarpp != null && !strSugarpp.equals("") && strSugarpp.length() > 0) {
            sbInvestigations.append("Sugar(PPG) - ").append(strSugarpp).append(" ; ");

        }
        if (strSugarFasting != null && !strSugarFasting.equals("") && strSugarFasting.length() > 0) {
            sbInvestigations.append("Sugar(FPG) - ").append(strSugarFasting).append(" ; ");
        }
        if (strEcg != null && !strEcg.equals("") && strEcg.length() > 0) {

            sbInvestigations.append("ECG - ").append(strEcg).append(" ; ");
        }
        if (strPft != null && !strPft.equals("") && strPft.length() > 0) {

            sbInvestigations.append("PFT - ").append(strPft).append(" ; ");
        }
        if (strHbA1c != null && !strHbA1c.equals("") && strHbA1c.length() > 0) {

            sbInvestigations.append("HbA1c - ").append(strHbA1c).append(" ; ");
        }

        if (strAcer != null && !strAcer.equals("") && strAcer.length() > 0) {

            sbInvestigations.append("ACR - ").append(strAcer).append(" ; ");
        }
        if (strSeremUrea != null && !strSeremUrea.equals("") && strSeremUrea.length() > 0) {

            sbInvestigations.append("SerumUrea - ").append(strSeremUrea).append(" ; ");
        }
        if (strHdl != null && !strHdl.equals("") && strHdl.length() > 0) {

            sbInvestigations.append("HDL - ").append(strHdl).append(" ; ");
        }
        if (strTc != null && !strTc.equals("") && strTc.length() > 0) {

            sbInvestigations.append(" TC: ").append(strTc).append(" ;");
        }
        if (strTg != null && !strTg.equals("") && strTg.length() > 0) {

            sbInvestigations.append("TG - ").append(strTg).append(" ; ");
        }
        if (strLdl != null && !strLdl.equals("") && strLdl.length() > 0) {

            sbInvestigations.append("LDL - ").append(strLdl).append(" ; ");
        }
        if (strVhdl != null && !strVhdl.equals("") && strVhdl.length() > 0) {

            sbInvestigations.append("VLDL - ").append(strVhdl).append(" ; ");
        }

        if (strSugarpp != null && !strSugarpp.equals("") || strSugarFasting != null && !strSugarFasting.equals("") || strHbA1c != null && !strHbA1c.equals("") || strAcer != null && !strAcer.equals("") || strSeremUrea != null && !strSeremUrea.equals("") || strHdl != null && !strHdl.equals("") || strTc != null && !strTc.equals("") || strTg != null && !strTg.equals("") ||
                strLdl != null && !strLdl.equals("") || strVhdl != null && !strVhdl.equals("") || strEcg != null && !strEcg.equals("") || strPft != null && !strPft.equals("")) {

            holder.showInvestigationLayout.setVisibility(View.VISIBLE);
            holder.showInvestigationData.setText(sbInvestigations);
        }

        String strPallor = model.getPallor();
        String strCyanosis = model.getCyanosis();
        String strTremors = model.getTremors();
        String strIcterus = model.getIcterus();
        String strClubbing = model.getClubbing();
        String strOedema = model.getOedema();
        String strCalfTenderness = model.getCalfTenderness();
        String strLymphadenopathy = model.getLymphadenopathy();

        if (strPallor != null && strPallor.length() > 0) {
            sbObservations.append("Pallor - ").append(strPallor).append("  ;  ");
        }

        if (strCyanosis != null && strCyanosis.length() > 0) {
            sbObservations.append("Cyanosis - ").append(strCyanosis).append("  ;  ");
        }

        if (strTremors != null && strTremors.length() > 0) {
            sbObservations.append("Tremors - ").append(strTremors).append("  ;  ");
        }

        if (strIcterus != null && strIcterus.length() > 0) {
            sbObservations.append("Icterus - ").append(strIcterus).append("  ;  ");
        }
        if (strClubbing != null && strClubbing.length() > 0) {
            sbObservations.append("Clubbing - ").append(strClubbing).append("  ;  ");
        }
        if (strOedema != null && strOedema.length() > 0) {
            sbObservations.append("Oedema - ").append(strOedema).append("  ;  ");
        }
        if (strCalfTenderness != null && strCalfTenderness.length() > 0) {
            sbObservations.append("Tenderness - ").append(strCalfTenderness).append("  ;  ");
        }
        if (strLymphadenopathy != null && strLymphadenopathy.length() > 0) {
            sbObservations.append("Lymphadenopathy - ").append(strLymphadenopathy).append("  ;  ");
        }
        if (strPallor != null && !strPallor.equals("") || strCyanosis != null && !strCyanosis.equals("") || strTremors != null && !strTremors.equals("") || strIcterus != null && !strIcterus.equals("") || strClubbing != null && !strClubbing.equals("") || strOedema != null && !strOedema.equals("") || strCalfTenderness != null && !strCalfTenderness.equals("") ||
                strLymphadenopathy != null && !strLymphadenopathy.equals("")) {

            holder.showObservationsLayout.setVisibility(View.VISIBLE);
            holder.showObservationsData.setText(sbObservations);
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

            } else {
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

        private final TextView tv_fod, tv_visit_date;
        private final TextView tv_clinical_notes, imgText;
        private final TextView tv_diagnosis, tv_symptoms;
        private TextView showInvestigationData, showVitalsData, showObservationsData, showReferralsData;

        private final LinearLayout linearlayoutSymptoms, linearlayoutDiagnosis;
        private LinearLayout showVitalsLayout, showObservationsLayout, showInvestigationLayout, showReferralsLayout;

        HistoryViewHolder(View view) {
            super(view);
            tv_visit_date = (TextView) view.findViewById(R.id.tv_visit_date);

            tv_fod = (TextView) view.findViewById(R.id.tv_fod);
            imgText = (TextView) view.findViewById(R.id.imgText);
            tv_clinical_notes = (TextView) view.findViewById(R.id.tv_clinical_notes);
            tv_diagnosis = (TextView) view.findViewById(R.id.tv_diagnosis);
            tv_symptoms = (TextView) view.findViewById(R.id.tv_symptoms);

            showInvestigationData = (TextView) view.findViewById(R.id.showInvestigationData);
            showVitalsData = (TextView) view.findViewById(R.id.showVitalsData);
            showObservationsData = (TextView) view.findViewById(R.id.showObservationsData);
            showReferralsData = (TextView) view.findViewById(R.id.showReferralsData);

            linearlayoutSymptoms = (LinearLayout) view.findViewById(R.id.linearlayoutSymptoms);
            linearlayoutDiagnosis = (LinearLayout) view.findViewById(R.id.linearlayoutDiagnosis);

            showVitalsLayout = (LinearLayout) view.findViewById(R.id.showVitals1);
            showObservationsLayout = (LinearLayout) view.findViewById(R.id.showObservations);
            showInvestigationLayout = (LinearLayout) view.findViewById(R.id.showInvestigation);
            showReferralsLayout = (LinearLayout) view.findViewById(R.id.showReferrals);
        }
    }

}
