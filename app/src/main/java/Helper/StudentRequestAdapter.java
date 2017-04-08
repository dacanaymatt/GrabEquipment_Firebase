package Helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.carlo.grabequipment.R;

import java.util.List;

import Model.RequestDetail;
import Model.StudentRequestDetail;

/**
 * Created by Matthew on 4/3/2017.
 */
public class StudentRequestAdapter extends BaseAdapter {

    private Context mContext;
    private List<StudentRequestDetail> mStudentRequestDetail;

    public StudentRequestAdapter(Context mContext, List<StudentRequestDetail> mStudentRequestDetail) {
        this.mContext = mContext;
        this.mStudentRequestDetail = mStudentRequestDetail;
    }

    @Override
    public int getCount() {
        return mStudentRequestDetail.size();
    }

    @Override
    public Object getItem(int i) {
        return mStudentRequestDetail.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.student_request_list, null);

        TextView statusView = (TextView) view.findViewById(R.id.textView_status);
        TextView dateBorrowedView = (TextView) view.findViewById(R.id.textView_dateBorrowed);
        TextView dateReturnedView = (TextView) view.findViewById(R.id.textView_dateReturned);
        TextView equipmentView= (TextView) view.findViewById(R.id.textView_equipment);
        TextView timeBorrowedView= (TextView) view.findViewById(R.id.textView_timeBorrowed);

        statusView.setText(String.valueOf(mStudentRequestDetail.get(i).getStatus()));
        dateBorrowedView.setText(mStudentRequestDetail.get(i).getDateBorrowed());
        dateReturnedView.setText(mStudentRequestDetail.get(i).getDateReturned());
        equipmentView.setText(String.valueOf(mStudentRequestDetail.get(i).getEquipment()));
        timeBorrowedView.setText(mStudentRequestDetail.get(i).getTimeBorrowed());

        //view.setTag(mStudentRequestDetail.get(i).getId());
        return view;
    }
}
