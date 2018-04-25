package alexgochi.wedo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity {
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        pieChart = (PieChart) findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.25f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> mValue = new ArrayList<>();

        mValue.add(new PieEntry(34f, "Today"));
        mValue.add(new PieEntry(14f, "Tomorrow"));
        mValue.add(new PieEntry(23f, "Important"));
        mValue.add(new PieEntry(8f, "Work"));
        mValue.add(new PieEntry(21f, "Social"));

        Description description = new Description();
        description.setText("TODO List");
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EasingOption.EaseInCubic);

        PieDataSet mDataSet = new PieDataSet(mValue, "List");
        mDataSet.setSliceSpace(3f);
        mDataSet.setSelectionShift(6f);
        mDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData mData = new PieData(mDataSet);
        mData.setValueTextSize(10f);
        mData.setValueTextColor(Color.BLACK);

        pieChart.setData(mData);
    }
}
