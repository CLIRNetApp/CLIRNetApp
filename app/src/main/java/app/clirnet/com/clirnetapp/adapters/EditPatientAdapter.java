package app.clirnet.com.clirnetapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.ShowPrescriptionImageActivity;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


/**
 * Created by Ashish on 9/12/2016.
 */
public class EditPatientAdapter extends RecyclerView.Adapter<EditPatientAdapter.HistoryViewHolder> {

    private final List<RegistrationModel> patientList;
    private Context mContext;

    public EditPatientAdapter(Context context, List<RegistrationModel> patientList) {
        this.patientList = patientList;
        this.mContext = context;

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
        //

        AppController appController = new AppController();
        try {
            if (follow_up_date == null || follow_up_date.equals("0000-00-00") || follow_up_date.equals("30-11-0002") || follow_up_date.equals("")) {
                holder.tv_fod.setText("--");
            } else {
                holder.tv_fod.setText(follow_up_date);
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient Adapter" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        holder.tv_visit_date.setText(model.getVisit_date());

        holder.tv_ailment.setText(model.getAilments());
        String mAilment=model.getAilments();
        if (mAilment != null && !mAilment.equals("") && mAilment.length()>0) {
            holder.tv_ailment.setText(mAilment);
        }else{
            holder.linearlayoutAilment.setVisibility(View.GONE);
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

        try {
            final String imgPath = patientList.get(position).getPres_img();
            if (!TextUtils.isEmpty(imgPath)) {

                holder.imgText.setText("View Prescription");
             //   holder.llayout.setBackgroundColor(R.color.colorPrimary2);
                holder.imgText.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.imgText.setTextColor(mContext.getResources().getColor(R.color.white));
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

    private void showDialog(String imgPath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "Yes button Clicked", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.custom_image_view_dialog, null);
        ImageView precImg = (ImageView) dialoglayout.findViewById(R.id.imageView);

        Glide.with(mContext)
                .load(imgPath)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(precImg);


        builder.setView(dialoglayout);
        builder.show();


    }

    @Override
    public int getItemCount() {
        //   return bookList.size();
        return patientList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_ailment;
        final TextView tv_fod;
        private final TextView tv_visit_date;
        final TextView tv_clinical_notes;
        private final TextView imgText, tv_diagnosis, tv_symptoms;
        private final LinearLayout linearlayoutSymptoms;
        private final LinearLayout linearlayoutDiagnosis;
        private final LinearLayout linearlayoutAilment,llayout;


        HistoryViewHolder(View view) {
            super(view);
            tv_visit_date = (TextView) view.findViewById(R.id.tv_visit_date);
            tv_ailment = (TextView) view.findViewById(R.id.tv_ailment);
            tv_fod = (TextView) view.findViewById(R.id.tv_fod);
            // prescriptionImg = (ImageView) view.findViewById(R.id.prescriptionImg);
            imgText = (TextView) view.findViewById(R.id.imgText);
            tv_diagnosis = (TextView) view.findViewById(R.id.tv_diagnosis);
            tv_symptoms = (TextView) view.findViewById(R.id.tv_symptoms);

            tv_clinical_notes = (TextView) view.findViewById(R.id.tv_clinical_notes);
            linearlayoutSymptoms = (LinearLayout) view.findViewById(R.id.linearlayoutSymptoms);
            linearlayoutDiagnosis = (LinearLayout) view.findViewById(R.id.linearlayoutDiagnosis);
            llayout=(LinearLayout)view.findViewById(R.id.llayout);
            linearlayoutAilment = (LinearLayout) view.findViewById(R.id.linearlayoutAilment);

        }
    }


}
