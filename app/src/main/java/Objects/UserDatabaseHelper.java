package Objects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "GrabEquipment.db";
    public static final int DATABASE_VERSION = 1;

    public UserDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    UserContract.UserDetails.TABLE_NAME     + "(" +
                    UserContract.UserDetails._ID            + " INTEGER PRIMARY KEY, " +
                    UserContract.UserDetails.FIRST_NAME_COLUMN    + " VARCHAR, " +
                    UserContract.UserDetails.LAST_NAME_COLUMN    + " VARCHAR, " +
                    UserContract.UserDetails.STUDENT_ID_COLUMN    + " VARCHAR, " +
                    UserContract.UserDetails.PASSWORD_COLUMN    + " VARCHAR, " +
                    UserContract.UserDetails.ADMIN_COLUMN + " VARCHAR);");
        db.execSQL("INSERT INTO " +
                UserContract.UserDetails.TABLE_NAME + "(" +
                UserContract.UserDetails.FIRST_NAME_COLUMN    + " , " +
                UserContract.UserDetails.LAST_NAME_COLUMN    + " , " +
                UserContract.UserDetails.STUDENT_ID_COLUMN    + " , " +
                UserContract.UserDetails.PASSWORD_COLUMN    + " , " +
                UserContract.UserDetails.ADMIN_COLUMN + " ) VALUES  " +
                "('ADMIN','ADMIN', '1611' ,'admin', '1');");

//        db.execSQL("CREATE TABLE IF NOT EXISTS " +
//                SchedContract.SchedDetails.TABLE_NAME     + "(" +
//                SchedContract.SchedDetails._ID            + " INTEGER PRIMARY KEY, " +
//                SchedContract.SchedDetails.ROOM_COLUMN    + " VARCHAR, " +
//                SchedContract.SchedDetails.DAY_COLUMN    + " VARCHAR, " +
//                SchedContract.SchedDetails.START_TIME_COLUMN    + " VARCHAR, " +
//                SchedContract.SchedDetails.END_TIME_COLUMN + " VARCHAR);");
//        db.execSQL("CREATE TABLE IF NOT EXISTS " +
//                ReservationContract.ReservationDetails.TABLE_NAME     + "(" +
//                ReservationContract.ReservationDetails._ID            + " INTEGER PRIMARY KEY, " +
//                ReservationContract.ReservationDetails.STUDENT_ID_COLUMN    + " VARCHAR, " +
//                ReservationContract.ReservationDetails.ROOM_COLUMN    + " VARCHAR, " +
//                ReservationContract.ReservationDetails.DAY_COLUMN    + " VARCHAR, " +
//                ReservationContract.ReservationDetails.START_TIME_COLUMN    + " VARCHAR, " +
//                ReservationContract.ReservationDetails.END_TIME_COLUMN    + " VARCHAR, " +
//                ReservationContract.ReservationDetails.DATE_COLUMN    + " VARCHAR, " +
//                ReservationContract.ReservationDetails.PURPOSE_COLUMN    + " VARCHAR, " +
//                ReservationContract.ReservationDetails.PROFESSOR_COLUMN    + " VARCHAR, " +
//                ReservationContract.ReservationDetails.STATUS_COLUMN + " VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserDetails.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + SchedContract.SchedDetails.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + ReservationContract.ReservationDetails.TABLE_NAME);
        onCreate(db);
    }
}
