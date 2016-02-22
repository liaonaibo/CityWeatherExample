package com.lnb.simpleframework.ui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.lnb.simpleframework.model.WeatherDetail;


/**
 * 天气温度趋势图
 */
public class WeatherChartView extends RelativeLayout {
    private LineChart mChart;

    public WeatherChartView(Context context) {
        super(context);
        onCreate();
    }

    public WeatherChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate();
    }

    public WeatherChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate();
    }

    protected void onCreate() {
        //创建图表
        createChart();
    }

    /**
     * 构建趋势图
     */
    private void createChart() {
        mChart = new LineChart(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mChart.setLayoutParams(params);

        mChart.setNoDataTextDescription("如果传递的数值是空,那么你将看到这段文字!");
        mChart.setHighlightEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(true);
        mChart.setHighlightPerDragEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.animateX(3000);

        Legend l = mChart.getLegend();
        l.setForm(LegendForm.LINE);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setPosition(LegendPosition.BELOW_CHART_CENTER);

        XAxis xAxis = mChart.getXAxis();

        // 将X坐标轴的标尺刻度移动底部。
        xAxis.setPosition(XAxisPosition.BOTTOM);

        // X轴之间数值的间隔
        xAxis.setSpaceBetweenLabels(1);

        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);

        YAxis leftAxis = mChart.getAxisLeft();
        setYAxisLeft(leftAxis);
        //设置右边Y轴
        YAxis rightAxis = mChart.getAxisRight();
        setYAxisRight(rightAxis);
        addView(mChart);
    }

    /**
     * 设置左边Y轴参数
     *
     * @param leftAxis
     */
    private void setYAxisLeft(YAxis leftAxis) {
        // 在左侧的Y轴上标出4个刻度值
        leftAxis.setLabelCount(0, true);

        // Y坐标轴轴线的颜色
        leftAxis.setGridColor(Color.TRANSPARENT);

        // Y轴坐标轴上坐标刻度值的颜色
        leftAxis.setTextColor(Color.BLACK);

        // Y坐标轴最大值
//        leftAxis.setAxisMaxValue(50);

        // Y坐标轴最小值
//        leftAxis.setAxisMinValue(-50);

        leftAxis.setStartAtZero(false);

        leftAxis.setDrawLabels(false);
    }

    /**
     * 设置右边Y轴参数
     *
     * @param rightAxis
     */
    private void setYAxisRight(YAxis rightAxis) {
        // Y坐标轴上标出8个刻度值
        rightAxis.setLabelCount(0, true);

        // Y坐标轴上刻度值的颜色
        rightAxis.setTextColor(Color.BLUE);

        // Y坐标轴上轴线的颜色
        rightAxis.setGridColor(Color.BLUE);

        // Y坐标轴最大值
        //rightAxis.setAxisMaxValue(30);

        // Y坐标轴最小值
        //rightAxis.setAxisMinValue(-5);

        rightAxis.setStartAtZero(false);
        rightAxis.setDrawLabels(false);
    }

    /**
     * 设置数据
     */
    public void setData(WeatherDetail weatherDetail) {
        if (null == weatherDetail) {
            return;
        }
        //获取未来几天的天气情况
        List<WeatherDetail.Future> futures = weatherDetail.future;
        if (null == futures || futures.size() == 0) {
            return;
        }

        //最高温度
        ArrayList<Entry> yHigh = new ArrayList<>();
        //最低温度
        ArrayList<Entry> yLow = new ArrayList<>();
        //X轴日期数据
        ArrayList<String> xVals = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        for (int i = 0; i < futures.size(); i++) {
            WeatherDetail.Future future = futures.get(i);
            //8℃~20℃
            String[] temperatures = future.temperature.split("℃");

            float lowTemperature = Float.parseFloat(temperatures[0]);
            yLow.add(new Entry(lowTemperature, i));

            float highTemperature = Float.parseFloat(temperatures[1].substring(1));
            yHigh.add(new Entry(highTemperature, i));

            //20140804 转 月日
            try {
                Date date = sdf.parse(future.date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                //int month = cal.get(Calendar.MONTH)+1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                xVals.add(day + "日");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        mChart.setDescription("未来" + xVals.size() + "日最低温最高温趋势图 单位:℃");

        // 设置数据点。
        setData(xVals, yHigh, yLow);
    }

    /**
     * 设置图表数据
     *
     * @param xVals X轴文本
     * @param yHigh 最高温度集合
     * @param yLow  最低温度集合
     */
    private void setData(ArrayList<String> xVals, ArrayList<Entry> yHigh, ArrayList<Entry> yLow) {

        //设置最高温度
        LineDataSet high = new LineDataSet(yHigh, "最高温");
        setHighTemperature(high);

        //设置最低温度
        LineDataSet low = new LineDataSet(yLow, "最低温");
        setLowTemperature(low);

        //显示数据到图表上
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(high);
        dataSets.add(low);

        //设置X轴文本、Y轴数据集
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.DKGRAY);
        data.setValueTextSize(10f);
        mChart.setData(data);
    }

    /**
     * 设置最高温度
     *
     * @param high
     */
    private void setHighTemperature(LineDataSet high) {

        // 以左边的Y坐标轴为准
        high.setAxisDependency(AxisDependency.LEFT);

        high.setLineWidth(2f);
        high.setColor(Color.RED);
        high.setCircleSize(2f);
        high.setCircleColor(Color.RED);
        high.setCircleColorHole(Color.LTGRAY);
        high.setDrawCircleHole(true);

        //改变折线样式，用曲线。
        high.setDrawCubic(true);
        //默认是直线曲线的平滑度，值越大越平滑。
        high.setCubicIntensity(0.2f);

        //填充曲线下方的区域，红色，半透明。
        high.setDrawFilled(true);
        high.setFillAlpha(128);
        high.setFillColor(Color.RED);

        // 设置折线上显示数据的格式。如果不设置，将默认显示float数据格式。
        high.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                DecimalFormat decimalFormat = new DecimalFormat(".0");
                String s = "" + decimalFormat.format(value);
                return s;
            }
        });
    }

    /**
     * 设置最低温度
     *
     * @param low
     */
    private void setLowTemperature(LineDataSet low) {

        // 以右边Y坐标轴为准
        low.setAxisDependency(AxisDependency.LEFT);
        // 折现的颜色
        low.setColor(Color.BLUE);
        // 线宽度
        low.setLineWidth(2f);
        // 折现上点的圆球颜色
        low.setCircleColor(Color.BLUE);
        // 填充圆球中心部位洞的颜色
        low.setCircleColorHole(Color.LTGRAY);
        // 圆球的尺寸
        low.setCircleSize(2f);
        low.setDrawCircleHole(true);
        //改变折线样式，用曲线。
        low.setDrawCubic(true);
        //默认是直线曲线的平滑度，值越大越平滑。
        low.setCubicIntensity(0.2f);
        //填充曲线下方的区域，蓝色，半透明。
        low.setDrawFilled(true);
        low.setFillAlpha(128);
        low.setFillColor(Color.BLUE);


        low.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                DecimalFormat decimalFormat = new DecimalFormat(".0");
                String s = "" + decimalFormat.format(value);
                return s;
            }
        });
    }
}
