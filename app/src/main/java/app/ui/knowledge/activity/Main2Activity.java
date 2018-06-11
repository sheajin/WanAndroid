package app.ui.knowledge.activity;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.xy.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import app.base.activity.BaseActivity;
import app.ui.knowledge.fragment.KnowledgeFragment;
import app.ui.main.fragment.HomePageFragment;
import butterknife.BindView;

public class Main2Activity extends BaseActivity {
    private RecyclerView mRv;
    private List<String> list;
    private MyAdapter adapter;
    private Activity activity;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initUI() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        getSupportFragmentManager().beginTransaction().add(R.id.frame,new HomePageFragment()).commit();

    }

    @Override
    protected void initData() {

    }
}
