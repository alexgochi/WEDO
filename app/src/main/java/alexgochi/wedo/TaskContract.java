package alexgochi.wedo;

import android.provider.BaseColumns;

/**
 * Created by Alex Gochi on 07/03/2018.
 */

public class TaskContract {
    public static final String DB_NAME = "alexgochi.whatwedo.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE ="title";
    }
}
