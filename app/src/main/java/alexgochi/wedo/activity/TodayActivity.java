package alexgochi.wedo.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import alexgochi.wedo.MainActivity;
import alexgochi.wedo.R;
import alexgochi.wedo.TaskContract;
import alexgochi.wedo.TaskDBHelper;

public class TodayActivity extends AppCompatActivity {
    private TaskDBHelper mHelper;
    private SwipeMenuListView Ltoday;
    private ArrayAdapter<String> mAdapter;
    ImageView imageToday, pass_button;
    TextView today;
    int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        mHelper = new TaskDBHelper(this);

        imageToday = (ImageView) findViewById(R.id.today);
        imageToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_list();
            }
        });

        Ltoday = (SwipeMenuListView) findViewById(R.id.list_today);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xCC, 0x00)));
                // set item width
                deleteItem.setWidth(70);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_done);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        Ltoday.setMenuCreator(creator);

        Ltoday.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deleteTask();
                        Toast.makeText(getApplicationContext(), "List Completed", Toast.LENGTH_SHORT).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        updateUI();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE1,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE1},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE1);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.add_todo, R.id.task_title, taskList);
            Ltoday.setAdapter(mAdapter);

        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        getCount();
        today = (TextView) findViewById(R.id.total_today);
        today.setText("Total : "+ mCount);

        cursor.close();
        db.close();
    }

    public void add_list() {
        LayoutInflater inflater = TodayActivity.this.getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.add_activity, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogLayout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText mNote = dialogLayout.findViewById(R.id.note);
                        if (mNote.getText().toString().equals("")) {
                            showToast();
                        } else {
                            String mNoteTodo = mNote.getText().toString();
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(TaskContract.TaskEntry.COL_TASK_TITLE1, mNoteTodo);
                            db.insertWithOnConflict(TaskContract.TaskEntry.TABLE1,
                                    null, values, SQLiteDatabase.CONFLICT_REPLACE);
                            db.close();
                            updateUI();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void showToast() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.input_todo, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void deleteTask() {
        TextView taskTextView = (TextView) findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE1,
                TaskContract.TaskEntry.COL_TASK_TITLE1+ " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    public void deleteAllTask() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + TaskContract.TaskEntry.TABLE1);
        db.close();
        updateUI();
    }

    public void getCount() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE1;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCount = cursor.getInt(0);
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            deleteAllTask();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void passData(View view) {
        int in_today = mCount;
        Intent intent_today = new Intent();
        intent_today.putExtra("TODAY", in_today);
        setResult(RESULT_OK, intent_today);
        finish();
    }
}
