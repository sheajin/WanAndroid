package com.xy.wanandroid.ui.gank.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.base.adapter.SimpleFragmentStateAdapter;
import com.xy.wanandroid.contract.gank.ArticleContract;
import com.xy.wanandroid.data.gank.ArticleTitleBean;
import com.xy.wanandroid.presenter.gank.ArticlePresenter;
import com.xy.wanandroid.ui.gank.fragment.WxArticleFragment;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ExtraActivity extends BaseRootActivity<ArticlePresenter> implements ArticleContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pager)
    ViewPager mPager;

    private List<ArticleTitleBean> titleList;
    private List<String> articleTitleList;
    private List<Fragment> fragments;
    private SimpleFragmentStateAdapter adapter;
    private ViewHolder holder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_extra;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.hot_wx_article));
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        titleList = new ArrayList<>();
        fragments = new ArrayList<>();
        articleTitleList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        mPresenter.getArticleTitle();
    }

    @Override
    public void getArticleTitleOk(List<ArticleTitleBean> data) {
        showNormal();
        titleList.addAll(data);
        for (int i = 0; i < titleList.size(); i++) {
            articleTitleList.add(titleList.get(i).getName());
            fragments.add(WxArticleFragment.getInstance(titleList.get(i).getId()));
        }
        adapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), fragments, articleTitleList);
        mPager.setAdapter(adapter);
        mTab.setupWithViewPager(mPager);
        /**
         * TabLayout第一个子view是LinearLayout,可以通过LinearLayout设置间隔线
         */
        LinearLayout linearLayout = (LinearLayout) mTab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(50);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.drawable_divider_tab));
        /**
         * 设置TabLayout样式,可在选中时放大
         */
//        setTabStyle();
    }

    @Override
    public void getArticleTitleErr(String info) {
        ToastUtil.show(activity, info);
        showError();
    }

    /**
     * 设置TabLayout样式,选中字体变大
     */
    private void setTabStyle() {
        holder = null;
        for (int i = 0; i < titleList.size(); i++) {
            //依次获取标签
            TabLayout.Tab tab = mTab.getTabAt(i);
            //为每个标签设置布局
            tab.setCustomView(R.layout.item_custom_tab);
            holder = new ViewHolder(tab.getCustomView());
            //为标签填充数据
            holder.tvTabName.setText(articleTitleList.get(i));
            //默认选择第一项
            if (i == 0) {
                holder.tvTabName.setSelected(true);
                holder.tvTabName.setTextSize(18);
            }
        }

        //tab选中的监听事件
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(true);
                //选中后字体变大
                holder.tvTabName.setTextSize(18);
                //让Viewpager跟随TabLayout的标签切换
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(false);
                //恢复为默认字体大小
                holder.tvTabName.setTextSize(16);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ViewHolder {
        TextView tvTabName;

        public ViewHolder(View tabView) {
            tvTabName = tabView.findViewById(R.id.tv_tab);
        }
    }

}















