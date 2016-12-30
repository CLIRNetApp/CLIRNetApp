package app.clirnet.com.clirnetapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Member;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


/**
 * Created by ${Ashish} on 9/3/2016.
 */
public class MultipleFilterPatientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RegistrationModel> patientList;
    private  AppController appController=new AppController();

    private boolean loading = true;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private OnItemClickListener onItemClickListener;

    public MultipleFilterPatientAdapter(List<RegistrationModel> patientList) {
        this.patientList = patientList;

    }

    private void add(RegistrationModel item) {
        patientList.add(item);
        notifyItemInserted(patientList.size());
    }

    public void addAll(List<RegistrationModel> memberList) {

            patientList.addAll(memberList);

            notifyDataSetChanged();

    }

    public void remove(Member item) {
        int position = patientList.indexOf(item);
        if (position > -1) {
            patientList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public RegistrationModel getItem(int position){
        return patientList.get(position);
    }

    @Override
    public int getItemViewType (int position) {

        if(isPositionFooter (position)) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    private boolean isPositionFooter (int position) {
        return position == patientList.size();
    }

    public void setLoading(boolean loading){
        this.loading = loading;
        notify();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_patient_search_list, parent, false);
            return new PatientViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiple_patient_search_list, parent, false);


        return new PatientViewHolder(itemView);*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PatientViewHolder) {
            PatientViewHolder memberViewHolder = (PatientViewHolder) holder;

            RegistrationModel model = patientList.get(position);

            String first_name = patientList.get(position).getFirstName();
            String middle_name = patientList.get(position).getMiddleName();
            String last_name = patientList.get(position).getLastName();


            String name=appController.toCamelCase(first_name + " " + middle_name + " " + last_name);


            memberViewHolder.name.setText(name);

            String fod = model.getActualFollowupDate();

            if(fod != null) {
                if (fod.equals("30-11-0002")) {

                    memberViewHolder.follow_up_date.setText("--");

                } else {
                    memberViewHolder.follow_up_date.setText(fod);
                }
            }


            int posi=position+1;
            String id=""+posi;
            memberViewHolder.id.setText(id); // this will add i value in desc order ie fifo order as per client req. 25-8-16 ,this id is not stored in db.



            memberViewHolder.age.setText(model.getAge());
            memberViewHolder.phone_no.setText(model.getMobileNumber());

            memberViewHolder.gender.setText(model.getGender());

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
            /*if(loading = false){
                loadingViewHolder.progressBar.setVisibility(View.GONE);
            }*/
        }

    }

    @Override
    public int getItemCount() {
        //   return bookList.size();
        return patientList == null ? 0 : patientList.size()+1;
        // return patientList.size();
    }
    /*@Override
    public int getItemCount() {
        return patientList == null ? 0 : patientList.size() + 1;
    }*/


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

    // --Commented out by Inspection START (07-11-2016 16:44):
//    public void setFilter(List<RegistrationModel> hotelModels) {
//        patientList = new ArrayList<>();
//        patientList.addAll(hotelModels);
//        notifyDataSetChanged();
//    }
// --Commented out by Inspection STOP (07-11-2016 16:44)
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.loading);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}