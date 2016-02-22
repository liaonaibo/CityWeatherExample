package com.lnb.simpleframework.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lnb.simpleframework.R;
import com.lnb.simpleframework.adapter.binder.BaseResource;
import com.lnb.simpleframework.presenter.CityListPresenter;
import com.lnb.simpleframework.view.ICityListView;

import java.util.List;

/**
 * 列表界面
 */
public class MainActivity extends FragmentActivity implements ICityListView, View.OnClickListener {

    private CityAdapter mAdapter;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private EditText et_search;
    private CityListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        initLayout();
        loadData();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        mPresenter = new CityListPresenter(this, this);
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mListView = (ListView) findViewById(R.id.activity_main_lv);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_main_pb);
        et_search = (EditText) findViewById(R.id.activity_main_et_search);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    searchCity();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.activity_main_iv_search).setOnClickListener(this);
    }

    /**
     * 加载数据
     */
    private void loadData() {
        //调用主持者获取数据
        mPresenter.getData();
    }

    @Override
    public void showCityList(List<BaseResource> list) {
        mProgressBar.setVisibility(View.GONE);
        if (null == mAdapter) {
            mAdapter = new CityAdapter(this, mPresenter, list);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDataLoading() {
        //TODO:数据加载中
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestDataFailByNoData() {
        //TODO:没有数据了
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, "没有数据了,稍候重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestDataFailByNetswork() {
        //网络异常
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, "网络异常,稍候重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.activity_main_iv_search == id) {
            searchCity();
        }
    }

    private void searchCity() {
        String city = et_search.getText().toString();
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "请输入城市名称", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.searchCity(city);
    }
}
