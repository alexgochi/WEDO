package alexgochi.wedo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

import alexgochi.wedo.activity.ImportantActivity;
import alexgochi.wedo.activity.SocialActivity;
import alexgochi.wedo.activity.TodayActivity;
import alexgochi.wedo.activity.TomorrowActivity;
import alexgochi.wedo.activity.WorkActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView today_count, tomorrow_count,important_count, work_count, social_count;
    int data_today, data_tomorrow, data_important, data_work, data_social;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        today_count = (TextView) findViewById(R.id.main_today);
        tomorrow_count = (TextView) findViewById(R.id.main_tomorrow);
        important_count = (TextView) findViewById(R.id.main_important);
        work_count = (TextView) findViewById(R.id.main_work);
        social_count = (TextView) findViewById(R.id.main_social);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        savedInstanceState = getIntent().getExtras();
//        if (savedInstanceState != null && getIntent().getIntExtra("TODAY",0) != 0) {
//            data_today = savedInstanceState.getInt("TODAY");
//            today_count.setText(data_today + " List");
//        }
//        if (savedInstanceState!= null && getIntent().getIntExtra("TOMORROW",0) != 0) {
//            data_tomorrow = savedInstanceState.getInt("TOMORROW");
//            tomorrow_count.setText(data_tomorrow +" List");
//        }
//        if (savedInstanceState != null && getIntent().getIntExtra("IMPORTANT",0) != 0) {
//            data_important = savedInstanceState.getInt("IMPORTANT");
//            important_count.setText(data_important +" List");
//        }
//        if (savedInstanceState != null && getIntent().getIntExtra("WORK",0) != 0) {
//            data_work = savedInstanceState.getInt("WORK");
//            work_count.setText(data_work +" List");
//        }
//        if (savedInstanceState!= null && getIntent().getIntExtra("SOCIAL",0) != 0) {
//            data_social = savedInstanceState.getInt("SOCIAL");
//            social_count.setText(data_social +" List");
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putInt("TODAY", data_today);
//        savedInstanceState.putInt("TOMORROW", data_tomorrow);
//        savedInstanceState.putInt("IMPORTANT", data_important);
//        savedInstanceState.putInt("WORK", data_work);
//        savedInstanceState.putInt("SOCIAL", data_social);
        super.onSaveInstanceState(savedInstanceState);
    }

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
           startActivity(in_today);
           Toast.makeText(getApplicationContext(), "You're in Today Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.tomorrow) {
           Intent in_tomorrow = new Intent(MainActivity.this, TomorrowActivity.class);
           startActivity(in_tomorrow);
           Toast.makeText(getApplicationContext(), "You're in Tomorrow Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.important) {
           Intent in_important = new Intent(MainActivity.this, ImportantActivity.class);
           startActivity(in_important);
           Toast.makeText(getApplicationContext(), "You're in Important Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.work) {
           Intent in_work = new Intent(MainActivity.this, WorkActivity.class);
           startActivity(in_work);
           Toast.makeText(getApplicationContext(), "You're in Work Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.social) {
           Intent in_social = new Intent(MainActivity.this, SocialActivity.class);
           startActivity(in_social);
           Toast.makeText(getApplicationContext(), "You're in Social Activity", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data_today = data.getIntExtra("TODAY", 0);
                today_count.setText(data_today);
            }
        }
    }
}
