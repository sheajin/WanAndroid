package com.xy.wanandroid.util.widget;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xy.wanandroid.R;

/**
 * Created by jxy on 2018/6/17.
 */
public class CommonDialog extends Dialog {
    private Context context;
    private boolean outsideCancelable;
    private String title;
    private String content;
    private View view;

    public CommonDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected CommonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        init();
    }

    private void init() {
        setCanceledOnTouchOutside(outsideCancelable);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        win.setAttributes(lp);
        view.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }




}
