package app.ui.main.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
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

import app.base.activity.BaseRootActivity;
import app.model.constant.Constant;
import app.util.app.ToastUtil;
import butterknife.BindView;

@SuppressLint("SetJavaScriptEnabled")
public class ArticleDetailsActivity extends BaseRootActivity {
    @BindView(R.id.article_detail_web_view)
    FrameLayout mWebContent;
    @BindView(R.id.article_toolbar)
    Toolbar mArticleToolbar;

    private String title;
    private String articleLink;
    private AgentWeb mAgentWeb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_details;
    }

    @Override
    protected void initUI() {
        getBundleData();
        setSupportActionBar(mArticleToolbar);
        getSupportActionBar().setTitle(title);
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

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString(Constant.ARTICLE_TITLE);
        articleLink = bundle.getString(Constant.ARTICLE_LINK);
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
                ToastUtil.show(context, "share");
                break;
            case R.id.menu_article_collect:
                ToastUtil.show(context, "collect");
                break;
            case R.id.menu_article_browser:
                ToastUtil.show(context, "browser");
                break;
        }
        return super.onOptionsItemSelected(item);
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


}
