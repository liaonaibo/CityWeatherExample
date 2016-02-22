package com.lnb.simpleframework.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lnb.simpleframework.R;
import com.lnb.simpleframework.model.City;
import com.lnb.simpleframework.presenter.WeatherDetailPresenter;

import com.lnb.simpleframework.model.WeatherDetail;
import com.lnb.simpleframework.view.IWeatherDetailView;


public class WeatherDetailActivity extends FragmentActivity implements IWeatherDetailView ,View.OnClickListener{
    private WeatherDetailPresenter mWeatherDetailPresenter;
    private WeatherChartView mWeatherChartView;
    private ProgressBar mProgressBar;
    private TextView tv_title,tv_temperature,tv_weather,tv_dressing_advice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather_detail);
        initVariable();
        initLayout();
        loadData();
    }

    /**
     * 初始化变量
     */
    private void initVariable(){
        mWeatherDetailPresenter = new WeatherDetailPresenter(this,this);
    }

    /**
     * 初始化布局
     */
    private void initLayout(){
        mProgressBar =(ProgressBar)findViewById(R.id.layout_weather_detail_pb);
        tv_title = (TextView)findViewById(R.id.layout_weather_detail_tv_title);
        tv_temperature = (TextView)findViewById(R.id.layout_weather_detail_tv_temperature);
        tv_weather = (TextView)findViewById(R.id.layout_weather_detail_tv_weather);
        tv_dressing_advice = (TextView)findViewById(R.id.layout_weather_detail_tv_dressing_advice);
        mWeatherChartView =(WeatherChartView)findViewById(R.id.layout_weather_detail_weatherchart);
        findViewById(R.id.layout_weather_detail_tv_back).setOnClickListener(this);
    }

    /**
     * 加载数据
     */
    private void loadData(){
        mWeatherDetailPresenter.loadData();
    }

    @Override
    public void showDetail(WeatherDetail weatherDetail) {
        mProgressBar.setVisibility(View.GONE);
        mWeatherChartView.setData(weatherDetail);
        tv_dressing_advice.setText(weatherDetail.today.dressing_advice);
        tv_temperature.setText(weatherDetail.today.temperature);
        tv_weather.setText(weatherDetail.today.weather);
    }

    @Override
    public void onDataLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestDataFailByNoData() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void requestDataFailByNetswork() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public Intent getDataIntent() {
        return getIntent();
    }

    @Override
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(R.id.layout_weather_detail_tv_back == id){
            finish();
        }
    }

    public static void start(Context context,City city){
        Intent intent = new Intent(context,WeatherDetailActivity.class);
        if(null != city){
            Bundle bundle = new Bundle();
            bundle.putSerializable("data",city);
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
