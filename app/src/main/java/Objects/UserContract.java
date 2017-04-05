package Objects;

import android.provider.BaseColumns;

public class UserContract
{
    private UserContract()
    {
        //To avoid instantiating anywhere
    }

    public static class UserDetails implements BaseColumns
    {
        public static final String TABLE_NAME = "users";
        public static final String FIRST_NAME_COLUMN = "firstName";
        public static final String LAST_NAME_COLUMN = "lastName";
        public static final String STUDENT_ID_COLUMN = "studentID";
        public static final String PASSWORD_COLUMN = "password";
        public static final String ADMIN_COLUMN = "admin";
    }
}
