package Helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlo.grabequipment.AdminRequest;
import com.example.carlo.grabequipment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.RequestDetail;


/**
 * Created by Matthew on 4/3/2017.
 */
public class RequestAdapter extends BaseAdapter {

    private DatabaseReference mDatabase;
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
    public View getView(int i, final View convertView, ViewGroup parent) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        View view = View.inflate(mContext, R.layout.request_list, null);

        Button okButton = (Button) view.findViewById(R.id.button_Returned);
        Button lostButton = (Button) view.findViewById(R.id.button_Lost);

        final TextView studentNumberView = (TextView) view.findViewById(R.id.textView_studentNumber);
        TextView firstNameView = (TextView) view.findViewById(R.id.textView_firstName);
        TextView lastNameView = (TextView) view.findViewById(R.id.textView_lastName);
        TextView statusView = (TextView) view.findViewById(R.id.textView_status);
        TextView dateBorrowedView = (TextView) view.findViewById(R.id.textView_dateBorrowed);
        final TextView equipmentView= (TextView) view.findViewById(R.id.textView_equipment);

        studentNumberView.setText(mRequestDetail.get(i).getStudentNumber());
        statusView.setText(String.valueOf(mRequestDetail.get(i).getStatus()));
        dateBorrowedView.setText(mRequestDetail.get(i).getDateBorrowed());
        equipmentView.setText(String.valueOf(mRequestDetail.get(i).getEquipment()));
        firstNameView.setText(String.valueOf(mRequestDetail.get(i).getFirstName()));
        lastNameView.setText(String.valueOf(mRequestDetail.get(i).getLastName()));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot requestSnapShot : dataSnapshot.child("Requests").getChildren()) {
                            if((requestSnapShot.child("studentNumber").getValue().toString()).equals(studentNumberView.getText().toString() + "@gmail.com") &&
                                    requestSnapShot.child("deviceBorrowed").getValue().toString().equals(equipmentView.getText().toString()) &&
                                    requestSnapShot.child("status").getValue().toString().equals("Borrowed")) {

                                Log.d("DEBUG", "TRUE");

                                Date date = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                SimpleDateFormat stf = new SimpleDateFormat("h:mm:ss a");
                                String formattedDate = sdf.format(date);
                                String formattedTime = stf.format(date);

                                requestSnapShot.child("dateReturned").getRef().setValue(formattedDate);
                                requestSnapShot.child("timeReturned").getRef().setValue(formattedTime);
                                requestSnapShot.child("status").getRef().setValue("Returned");

                                for(DataSnapshot equipmentSnapshot : dataSnapshot.child("Equipment").getChildren()) {
                                    if(equipmentSnapshot.child("name").getValue().toString().equals(equipmentView.getText().toString())) {
                                        int amount = Integer.parseInt(equipmentSnapshot.child("amount").getValue().toString());
                                        amount++;
                                        equipmentSnapshot.child("amount").getRef().setValue(String.valueOf(amount));

                                        break;
                                    }
                                }

                                Toast.makeText(convertView.getContext(), "Successfully changed status." ,  Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(convertView.getContext(), AdminRequest.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                convertView.getContext().startActivity(intent);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        lostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot requestSnapShot : dataSnapshot.child("Requests").getChildren()) {

                            Log.d("DEBUG", studentNumberView.getText().toString());

                            if((requestSnapShot.child("studentNumber").getValue().toString()).equals(studentNumberView.getText().toString() + "@gmail.com") &&
                                    requestSnapShot.child("deviceBorrowed").getValue().toString().equals(equipmentView.getText().toString()) &&
                                    requestSnapShot.child("status").getValue().toString().equals("Borrowed")) {

                                Date date = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                SimpleDateFormat stf = new SimpleDateFormat("h:mm:ss a");
                                String formattedDate = sdf.format(date);
                                String formattedTime = stf.format(date);

                                requestSnapShot.child("dateReturned").getRef().setValue(formattedDate);
                                requestSnapShot.child("timeReturned").getRef().setValue(formattedTime);
                                requestSnapShot.child("status").getRef().setValue("Lost");

                                Toast.makeText(convertView.getContext(), "Successfully changed status." ,  Toast.LENGTH_SHORT).show();

                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        //view.setTag(mRequestDetail.get(i).getId());
        return view;
    }
}
