package app.clirnet.com.clirnetapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


public class SearchViewdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int mQueryCount;
    private List<RegistrationModel> patientList;

    private AppController appController;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private boolean loading = true;


    public SearchViewdapter(List<RegistrationModel> patientList) {
        this.patientList = patientList;

    }

    public SearchViewdapter(List<RegistrationModel> patientList, int queryCount) {
        this.patientList = patientList;
        this.mQueryCount = queryCount;

    }

    private void add(RegistrationModel item) {
        patientList.add(item);
        notifyItemInserted(patientList.size());
    }

    public void addAll(List<RegistrationModel> memberList) {

        patientList.addAll(memberList);

        notifyDataSetChanged();

    }

    public void remove(RegistrationModel item) {
        int position = patientList.indexOf(item);
        if (position > -1) {
            patientList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }
    public RegistrationModel getItem(int position) {
        return patientList.get(position);
    }
    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }
    private boolean isPositionFooter(int position) {
        return position == patientList.size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_seach_list, parent, false);
            return new MemberViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MemberViewHolder) {

            MemberViewHolder memberViewHolder = (MemberViewHolder) holder;

            final RegistrationModel model = patientList.get(position);
            String first_name = patientList.get(position).getFirstName();
            String middle_name = patientList.get(position).getMiddleName();
            String last_name = patientList.get(position).getLastName();

            String name=appController.toCamelCase(first_name + " " + middle_name + " " + last_name);
            memberViewHolder.name.setText(name);

            appController=new AppController();

            String fod = model.getActualFollowupDate();



            try {
                if (fod == null || fod.equals("") || fod.equals("0000-00-00")  || fod.equals("30-11-0002")) {
                    memberViewHolder.follow_up_date.setText("--");
                } else {
                    memberViewHolder.follow_up_date.setText(fod);
                }
            }catch(Exception e){
                e.printStackTrace();
                appController.appendLog(appController.getDateTime()+" " +"/ "+"Srach View Adapter"+e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

           /* if (fod.equals("30-11-0002")) {

            holder.follow_up_date.setText("--");

        } else {
            holder.follow_up_date.setText(fod);
        }
*/


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

            if (patientList.size() >= mQueryCount-1) {
                loadingViewHolder.progressBar.setVisibility(View.GONE);
            }
        }

    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    @Override
    public int getItemCount() {
        return patientList == null ? 0 : patientList.size(); //if we remove +1 will hide proress bar which is shows before loading data
    }


    static class MemberViewHolder extends RecyclerView.ViewHolder {

        private final TextView phone_no;
        private final TextView follow_up_date;
        public final TextView name;
        public final TextView gender;
        private final TextView id;
        public final TextView age;

        public MemberViewHolder(View view) {
            super(view);

            id=(TextView)view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            gender=(TextView)view.findViewById(R.id.gender);

            age = (TextView) view.findViewById(R.id.age);
            phone_no=(TextView)view.findViewById(R.id.phno);
            follow_up_date=(TextView)view.findViewById(R.id.follow_up_date);

        }

    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.loading);
        }
    }


    public void setFilter(List<RegistrationModel> hotelModels) {
        patientList = new ArrayList<>();
        patientList.addAll(hotelModels);
        notifyDataSetChanged();
    }
}
