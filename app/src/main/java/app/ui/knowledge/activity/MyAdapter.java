package app.ui.knowledge.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xy.wanandroid.R;

import java.util.List;

/**
 * Created by jxy on 2018/6/11.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myHoilder> {
    private List<String> list;
    private Activity activity;

    public MyAdapter(List<String> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public myHoilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myHoilder(View.inflate(activity, R.layout.item_homepage, null));
    }

    @Override
    public void onBindViewHolder(@NonNull myHoilder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myHoilder extends RecyclerView.ViewHolder {


        public myHoilder(View itemView) {
            super(itemView);
        }
    }
}
