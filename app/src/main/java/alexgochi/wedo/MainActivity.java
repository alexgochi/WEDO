package alexgochi.wedo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import alexgochi.wedo.activity.ImportantActivity;
import alexgochi.wedo.activity.SocialActivity;
import alexgochi.wedo.activity.TodayActivity;
import alexgochi.wedo.activity.TomorrowActivity;
import alexgochi.wedo.activity.WorkActivity;
import alexgochi.wedo.superb.Counter;


public class MainActivity extends Counter implements NavigationView.OnNavigationItemSelectedListener {

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getCountToday();
        today_count = (TextView) findViewById(R.id.main_today);
        today_count.setText(mCountToday +" List");

        getCountTomorrow();
        tomorrow_count = (TextView) findViewById(R.id.main_tomorrow);
        tomorrow_count.setText(mCountTomorrow +" List");

        getCountImportant();
        important_count = (TextView) findViewById(R.id.main_important);
        important_count.setText(mCountImportant +" List");

        getCountWork();
        work_count = (TextView) findViewById(R.id.main_work);
        work_count.setText(mCountWork +" List");

        getCountSocial();
        social_count = (TextView) findViewById(R.id.main_social);
        social_count.setText(mCountSocial +" List");
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.today) {
           Intent in_today = new Intent(MainActivity.this, TodayActivity.class);
           Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_LONG).show();
           startActivityForResult(in_today, TODAY_REQUEST_CODE);
        } else if (id == R.id.tomorrow) {
           Intent in_tomorrow = new Intent(MainActivity.this, TomorrowActivity.class);
           Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_LONG).show();
           startActivityForResult(in_tomorrow, TOMORROW_REQUEST_CODE);
        } else if (id == R.id.important) {
           Intent in_important = new Intent(MainActivity.this, ImportantActivity.class);
           Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_LONG).show();
           startActivityForResult(in_important, IMPORTANT_REQUEST_CODE);
        } else if (id == R.id.work) {
           Intent in_work = new Intent(MainActivity.this, WorkActivity.class);
           Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_LONG).show();
           startActivityForResult(in_work, WORK_REQUEST_CODE);
        } else if (id == R.id.social) {
           Intent in_social = new Intent(MainActivity.this, SocialActivity.class);
           Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_LONG).show();
           startActivityForResult(in_social, SOCIAL_REQUEST_CODE);
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

}
