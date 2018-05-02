package alexgochi.wedo;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import alexgochi.wedo.activity.ImportantActivity;
import alexgochi.wedo.activity.SocialActivity;
import alexgochi.wedo.activity.TodayActivity;
import alexgochi.wedo.activity.TomorrowActivity;
import alexgochi.wedo.activity.WorkActivity;

public class OverviewActivity extends AppCompatActivity {
    private TaskDBHelper mHelper;
    int mCountToday = 0;
    int mCountTomorrow = 0;
    int mCountImportant = 0;
    int mCountWork = 0;
    int mCountSocial = 0;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        mHelper = new TaskDBHelper(this);

        pieChart = (PieChart) findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.25f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        getCountToday();
        getCountTomorrow();
        getCountImportant();
        getCountWork();
        getCountSocial();

        final ArrayList<PieEntry> mValue = new ArrayList<>();

        if (mCountToday > 0) {
            mValue.add(new PieEntry(mCountToday, "Today"));
        }
        if (mCountTomorrow > 0) {
            mValue.add(new PieEntry(mCountTomorrow, "Tomorrow"));
        }
        if (mCountImportant > 0) {
            mValue.add(new PieEntry(mCountImportant, "Important"));
        }
        if (mCountWork > 0) {
            mValue.add(new PieEntry(mCountWork, "Work"));
        }
        if (mCountSocial > 0) {
            mValue.add(new PieEntry(mCountSocial, "Social"));
        }

        Description description = new Description();
        description.setText("TODO LIST");
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EasingOption.EaseInCubic);

        final PieDataSet mDataSet = new PieDataSet(mValue, "");
        mDataSet.setSliceSpace(3f);
        mDataSet.setSelectionShift(10f);
        mDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                String[] splits = e.toString().split(" ");
                String fix = splits[4].split("\\.")[0];

//                String fix = splits[4].substring(0,1);

//                int subs = e.toString().indexOf("x: 0.0 y: ");
//                String data = e.toString().substring(subs + 10);
//
                Toast.makeText(getApplicationContext(), "Value : " + fix + " List", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        PieData mData = new PieData(mDataSet);
        mData.setValueTextSize(15f);
        mData.setValueTextColor(Color.BLACK);

        pieChart.setData(mData);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

    public void getCountToday() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE1;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountToday = cursor.getInt(0);
        }
        cursor.close();
    }

    public void getCountTomorrow() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE2;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountTomorrow = cursor.getInt(0);
        }
        cursor.close();
    }

    public void getCountImportant() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE3;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountImportant = cursor.getInt(0);
        }
        cursor.close();
    }

    public void getCountWork() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE4;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountWork = cursor.getInt(0);
        }
        cursor.close();
    }

    public void getCountSocial() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE5;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountSocial = cursor.getInt(0);
        }
        cursor.close();
    }
}
