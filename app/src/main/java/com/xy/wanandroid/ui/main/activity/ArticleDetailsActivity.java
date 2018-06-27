package com.xy.wanandroid.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.ArticleDetailContact;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.presenter.main.ArticleDetailPresenter;
import com.xy.wanandroid.ui.login.LoginActivity;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

import butterknife.BindView;

@SuppressLint("SetJavaScriptEnabled")
public class ArticleDetailsActivity extends BaseRootActivity implements ArticleDetailContact.View {
    @BindView(R.id.article_detail_web_view)
    FrameLayout mWebContent;
    @BindView(R.id.article_toolbar)
    Toolbar mArticleToolbar;

    private String title;
    private String articleLink;
    private int articleId;
    private boolean isCollect;
    private AgentWeb mAgentWeb;
    private ArticleDetailPresenter presenter;
    private int collectCode = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_details;
    }

    @Override
    protected void initUI() {
        getBundleData();
        setSupportActionBar(mArticleToolbar);
        getSupportActionBar().setTitle(title);
        mArticleToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void getBundleData() {
        presenter = new ArticleDetailPresenter(this);
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString(Constant.ARTICLE_TITLE);
        articleLink = bundle.getString(Constant.ARTICLE_LINK);
        articleId = bundle.getInt(Constant.ARTICLE_ID, Constant.REQUEST_ERROR);
        isCollect = bundle.getBoolean(Constant.ARTICLE_IS_COLLECT);
    }

    @Override
    protected void initData() {
        if (title != null && articleLink != null) {
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go(articleLink);
            WebView mWebView = mAgentWeb.getWebCreator().getWebView();
            WebSettings mSettings = mWebView.getSettings();
            mSettings.setJavaScriptEnabled(true);
            mSettings.setSupportZoom(true);
            mSettings.setBuiltInZoomControls(true);
            //不显示缩放按钮
            mSettings.setDisplayZoomControls(false);
            //设置自适应屏幕，两者合用
            //将图片调整到适合WebView的大小
            mSettings.setUseWideViewPort(true);
            //缩放至屏幕的大小
            mSettings.setLoadWithOverviewMode(true);
            //自适应屏幕
            mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_article_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "来自WanAndroid 【" + title + "】" + articleLink);
                startActivity(Intent.createChooser(shareIntent, "分享"));
                break;
            case R.id.menu_article_collect:
                LogUtil.e("id = " + articleId + "is = " + isCollect);
                if ((Boolean) SharedPreferenceUtil.get(context, Constant.ISLOGIN, Constant.FALSE)) {
                    if (isCollect) {
                        presenter.cancelCollectArticle(articleId);
                    } else {
                        presenter.collectArticle(articleId);
                    }
                } else {
                    ToastUtil.show(activity, getString(R.string.please_login));
                    JumpUtil.overlay(activity, LoginActivity.class);
                }
                break;
            case R.id.menu_article_browser:
                Uri uri = Uri.parse(articleLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

}

    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId Feature id
     * @param menu      Menu
     * @return menu if opened
     */
    @SuppressLint("PrivateApi")
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            supportFinishAfterTransition();
        }
    }

    /**
     * menu菜单栏发生变化时调用
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LogUtil.e("collectCode = " + collectCode);
        menu.findItem(R.id.menu_article_collect).setIcon(isCollect ? R.drawable.icon_collect : R.drawable.icon_no_collect);
        switch (collectCode) {
            case 1:
            case 4:
                isCollect = true;
                menu.findItem(R.id.menu_article_collect).setIcon(R.drawable.icon_collect);
                break;
            case 2:
            case 3:
                isCollect = false;
                menu.findItem(R.id.menu_article_collect).setIcon(R.drawable.icon_no_collect);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void collectArticleOK(String info) {
        collectCode = 1;
        ToastUtil.show(activity, getString(R.string.collect_success));
        EventBus.getDefault().post(new MessageEvent(EventConstant.REFRESHHOMEPAGE, ""));
    }

    @Override
    public void collectArticleErr(String info) {
        collectCode = 2;
        ToastUtil.show(activity, getString(R.string.collect_fail));
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        collectCode = 3;
        ToastUtil.show(activity, getString(R.string.cancel_collect_success));
        EventBus.getDefault().post(new MessageEvent(EventConstant.REFRESHHOMEPAGE, ""));
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        collectCode = 4;
        ToastUtil.show(activity, getString(R.string.cancel_collect_fail));
    }
}
