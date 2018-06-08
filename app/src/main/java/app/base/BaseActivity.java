package app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by jxy on 2018/1/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageView mIvBack;
    protected MyApplication context;
    private BaseActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = MyApplication.getInstance();
        activity = this;
        initBind();
        initUI();
        initData();
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    public void initBind() {
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
//    public void onEvent(ObjectEvent event) {
//    }

    /**
     * 界面初始化
     */
    protected abstract void initUI();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 点击事件
     */
    protected void setClick(int... resId) {
        for (int id : resId) {
            View view = this.findViewById(id);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void showMess(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    public void showLog(String mess) {
        Log.e(MyApplication.getInstance().getClass().getSimpleName(), "LogInfo: " + mess);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

}
