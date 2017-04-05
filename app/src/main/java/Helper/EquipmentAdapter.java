package Helper;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.carlo.grabequipment.R;

import org.w3c.dom.Text;

import java.util.List;

import Model.Equipment;

/**
 * Created by Matthew on 4/3/2017.
 */
public class EquipmentAdapter extends BaseAdapter {

    private Context mContext;
    private List<Equipment> mEquipmentList;

    public EquipmentAdapter(Context mContext, List<Equipment> mEquipmentList) {
        this.mContext = mContext;
        this.mEquipmentList = mEquipmentList;
    }

    @Override
    public int getCount() {
        return mEquipmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return mEquipmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.equipment_list, null);

        TextView equipmentTypeView = (TextView) view.findViewById(R.id.textView_equipment_type);
        TextView equipmentAmountView = (TextView) view.findViewById(R.id.textView_equipment_amount);

        equipmentTypeView.setText(mEquipmentList.get(i).getName());
        equipmentAmountView.setText(String.valueOf(mEquipmentList.get(i).getAmount()));

        //view.setTag(mEquipmentList.get(i).getId());
        return view;
    }
}
