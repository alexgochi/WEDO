package alexgochi.wedo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import alexgochi.wedo.activity.ImportantActivity;
import alexgochi.wedo.activity.SocialActivity;
import alexgochi.wedo.activity.TodayActivity;
import alexgochi.wedo.activity.TomorrowActivity;
import alexgochi.wedo.activity.WorkActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TaskDBHelper mHelper;
    int mCount = 0;

    TextView today_count, tomorrow_count,important_count, work_count, social_count;
    int data_today, data_tomorrow, data_important, data_work, data_social;
    private static final int TODAY_REQUEST_CODE = 1;
    private static final int TOMORROW_REQUEST_CODE = 2;
    private static final int IMPORTANT_REQUEST_CODE = 3;
    private static final int WORK_REQUEST_CODE = 4;
    private static final int SOCIAL_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        today_count = (TextView) findViewById(R.id.main_today);
        today_count.setText("Total : "+ mCount);

        tomorrow_count = (TextView) findViewById(R.id.main_tomorrow);
//        tomorrow_count.setText("Total : "+ mCount);

        important_count = (TextView) findViewById(R.id.main_important);
//        important_count.setText("Total : "+ mCount);

        work_count = (TextView) findViewById(R.id.main_work);
//        important_count.setText("Total : "+ mCount);

        social_count = (TextView) findViewById(R.id.main_social);
//        social_count.setText("Total : "+ mCount);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getCount();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("TODAY", data_today);
//        outState.putInt("TOMORROW", data_tomorrow);
//        outState.putInt("IMPORTANT", data_important);
//        outState.putInt("WORK", data_work);
//        outState.putInt("SOCIAL", data_social);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        data_today = savedInstanceState.getInt("TODAY",0);
//        today_count.setText(data_today + " List");
//        data_tomorrow = savedInstanceState.getInt("TOMORROW",0);
//        tomorrow_count.setText(data_tomorrow + " List");
//        data_important = savedInstanceState.getInt("IMPORTANT",0);
//        important_count.setText(data_important + " List");
//        data_work = savedInstanceState.getInt("WORK",0);
//        work_count.setText(data_work + " List");
//        data_social = savedInstanceState.getInt("SOCIAL",0);
//        social_count.setText(data_social + " List");
//    }

    public void mPieChartLaunch(View view) {
        Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.today) {
           Intent in_today = new Intent(MainActivity.this, TodayActivity.class);
           startActivityForResult(in_today, TODAY_REQUEST_CODE);
           Toast.makeText(getApplicationContext(), "You're in Today Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.tomorrow) {
           Intent in_tomorrow = new Intent(MainActivity.this, TomorrowActivity.class);
           startActivityForResult(in_tomorrow, TOMORROW_REQUEST_CODE);
           Toast.makeText(getApplicationContext(), "You're in Tomorrow Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.important) {
           Intent in_important = new Intent(MainActivity.this, ImportantActivity.class);
           startActivityForResult(in_important, IMPORTANT_REQUEST_CODE);
           Toast.makeText(getApplicationContext(), "You're in Important Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.work) {
           Intent in_work = new Intent(MainActivity.this, WorkActivity.class);
           startActivityForResult(in_work, WORK_REQUEST_CODE);
           Toast.makeText(getApplicationContext(), "You're in Work Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.social) {
           Intent in_social = new Intent(MainActivity.this, SocialActivity.class);
           startActivityForResult(in_social, SOCIAL_REQUEST_CODE);
           Toast.makeText(getApplicationContext(), "You're in Social Activity", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TODAY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data_today = data.getIntExtra("TODAY", 0);
                today_count.setText(data_today + " List");
            }
        }
        if (requestCode == TOMORROW_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data_tomorrow = data.getIntExtra("TOMORROW", 0);
                tomorrow_count.setText(data_tomorrow + " List");
            }
        }
        if (requestCode == IMPORTANT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data_important = data.getIntExtra("IMPORTANT", 0);
                important_count.setText(data_important + " List");
            }
        }
        if (requestCode == WORK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data_work = data.getIntExtra("WORK", 0);
                work_count.setText(data_work + " List");
            }
        }
        if (requestCode == SOCIAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data_social = data.getIntExtra("SOCIAL", 0);
                social_count.setText(data_social + " List");
            }
        }
    }

    public void getCount() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE1;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCount = cursor.getInt(0);
//            Toast.makeText(getApplicationContext(), "Total : "+mCount, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}
