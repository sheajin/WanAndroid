package app.ui.knowledge.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;

import java.util.List;

import app.data.knowledge.KnowledgeClassifyListBean;

/**
 * Created by jxy on 2018/6/18.
 */
public class KnowledgeClassifyAdapter extends BaseQuickAdapter<KnowledgeClassifyListBean.DatasBean, BaseViewHolder> {

    public KnowledgeClassifyAdapter(int layoutResId, @Nullable List<KnowledgeClassifyListBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeClassifyListBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_content, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            String classifyName = item.getSuperChapterName() + " / " + item.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
    }
}
