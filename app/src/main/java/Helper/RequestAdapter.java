package Helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.carlo.grabequipment.R;

import org.w3c.dom.Text;

import java.util.List;

import Model.BorrowDetail;
import Model.RequestDetail;

/**
 * Created by Matthew on 4/3/2017.
 */
public class RequestAdapter extends BaseAdapter {

    private Context mContext;
    private List<RequestDetail> mRequestDetail;

    public RequestAdapter(Context mContext, List<RequestDetail> mRequestDetail) {
        this.mContext = mContext;
        this.mRequestDetail = mRequestDetail;
    }

    @Override
    public int getCount() {
        return mRequestDetail.size();
    }

    @Override
    public Object getItem(int i) {
        return mRequestDetail.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.request_list, null);

        TextView studentNumberView = (TextView) view.findViewById(R.id.textView_studentNumber);
        TextView firstNameView = (TextView) view.findViewById(R.id.textView_firstName);
        TextView lastNameView = (TextView) view.findViewById(R.id.textView_lastName);
        TextView statusView = (TextView) view.findViewById(R.id.textView_status);
        TextView dateBorrowedView = (TextView) view.findViewById(R.id.textView_dateBorrowed);
        TextView equipmentView= (TextView) view.findViewById(R.id.textView_equipment);

        studentNumberView.setText(mRequestDetail.get(i).getStudentNumber());
        statusView.setText(String.valueOf(mRequestDetail.get(i).getStatus()));
        dateBorrowedView.setText(mRequestDetail.get(i).getDateBorrowed());
        equipmentView.setText(String.valueOf(mRequestDetail.get(i).getEquipment()));
        firstNameView.setText(String.valueOf(mRequestDetail.get(i).getFirstName()));
        lastNameView.setText(String.valueOf(mRequestDetail.get(i).getLastName()));

        //view.setTag(mRequestDetail.get(i).getId());
        return view;
    }
}
