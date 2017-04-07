package Helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.carlo.grabequipment.R;

import java.util.List;

import Model.BorrowDetail;

/**
 * Created by Matthew on 4/3/2017.
 */
public class HistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<BorrowDetail> mBorrowDetail;

    public HistoryAdapter(Context mContext, List<BorrowDetail> mBorrowDetail) {
        this.mContext = mContext;
        this.mBorrowDetail = mBorrowDetail;
    }

    @Override
    public int getCount() {
        return mBorrowDetail.size();
    }

    @Override
    public Object getItem(int i) {
        return mBorrowDetail.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.history_list, null);

        TextView studentNumberView = (TextView) view.findViewById(R.id.textView_studentNumber);
        TextView statusView = (TextView) view.findViewById(R.id.textView_status);
        TextView dateBorrowedView = (TextView) view.findViewById(R.id.textView_dateBorrowed);
        TextView dateReturnedView= (TextView) view.findViewById(R.id.textView_dateReturned);
        TextView equipmentView= (TextView) view.findViewById(R.id.textView_equipment);

        studentNumberView.setText(mBorrowDetail.get(i).getStudentNumber());
        statusView.setText(String.valueOf(mBorrowDetail.get(i).getStatus()));
        dateBorrowedView.setText(mBorrowDetail.get(i).getDateBorrowed());
        dateReturnedView.setText(String.valueOf(mBorrowDetail.get(i).getDateReturned()));
        equipmentView.setText(String.valueOf(mBorrowDetail.get(i).getEquipment()));

        //view.setTag(mBorrowDetail.get(i).getId());
        return view;
    }
}
