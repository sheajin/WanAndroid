package com.xy.wanandroid.util.widget;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xy.wanandroid.R;

/**
 * Created by jxy on 2018/6/17.
 */
public class CommonDialog extends Dialog {
    private Context context;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView btnPositive;
    private TextView btnNegative;
    private FrameLayout flCustom;
    private View.OnClickListener onDefaultClickListener = view -> cancel();
    private View.OnClickListener onPositiveListener = onDefaultClickListener;
    private View.OnClickListener onNegativeListener = onDefaultClickListener;
    private String mTitle;
    private String mMessage;
    private String positiveText;
    private String negativeText;
    private boolean isProgressBarShow = false;
    private boolean isNegativeBtnShow = true;
    private View mView;

    public CommonDialog(@NonNull Context context) {
//        super(context);
        super(context, R.style.MyDialog);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logout);
        tvTitle = findViewById(R.id.dialog_title);
        tvContent = findViewById(R.id.dialog_content);
        btnPositive = findViewById(R.id.dialog_sure);
        btnNegative = findViewById(R.id.dialog_cancel);
    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(CommonDialog mDialog) {
        if (!TextUtils.isEmpty(mDialog.mTitle)) {
            mDialog.tvTitle.setText(mDialog.mTitle);
        }
        if (mDialog.mView != null) {
            mDialog.flCustom.addView(mDialog.mView);
            mDialog.tvContent.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(mDialog.mMessage)) {
                mDialog.tvContent.setText(mDialog.mMessage);
                mDialog.tvContent.setVisibility(View.VISIBLE);
            }
            if (isProgressBarShow) {
                mDialog.btnPositive.setVisibility(View.GONE);
                mDialog.btnNegative.setVisibility(View.GONE);
            }
        }
        if (!mDialog.isNegativeBtnShow) {
            mDialog.btnNegative.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mDialog.btnPositive
                    .getLayoutParams();
            layoutParams.setMargins(150, layoutParams.topMargin, 150, layoutParams.bottomMargin);
            mDialog.btnPositive.setLayoutParams(layoutParams);
        } else {
            mDialog.btnNegative.setOnClickListener(mDialog.onNegativeListener);
            if (!TextUtils.isEmpty(mDialog.negativeText)) {
                mDialog.btnNegative.setText(mDialog.negativeText);
            }
        }
        mDialog.btnPositive.setOnClickListener(mDialog.onPositiveListener);
        if (!TextUtils.isEmpty(mDialog.positiveText)) {
            mDialog.btnPositive.setText(mDialog.positiveText);
        }
    }

    public static class Builder {

        private CommonDialog mDialog;

        public Builder(Context context) {
            mDialog = new CommonDialog(context);
        }

        /**
         * 设置对话框标题
         *
         * @param title
         */
        public Builder setTitle(String title) {
            mDialog.mTitle = title;
            return this;
        }

        /**
         * 设置对话框文本内容,如果调用了setView()方法，该项失效
         *
         * @param msg
         */
        public Builder setMessage(String msg) {
            mDialog.mMessage = msg;
            return this;
        }

        /**
         * 设置确认按钮的回调
         *
         * @param onClickListener
         */
        public Builder setPositiveButton(View.OnClickListener onClickListener) {
            mDialog.onPositiveListener = onClickListener;
            return this;
        }

        /**
         * 设置确认按钮的回调
         *
         * @param btnText,onClickListener
         */
        public Builder setPositiveButton(String btnText, View.OnClickListener onClickListener) {
            mDialog.positiveText = btnText;
            mDialog.onPositiveListener = onClickListener;
            return this;
        }

        /**
         * 设置取消按钮的回调
         *
         * @param btnText,onClickListener
         */
        public Builder setNegativeButton(String btnText, View.OnClickListener onClickListener) {
            mDialog.negativeText = btnText;
            mDialog.onNegativeListener = onClickListener;
            return this;
        }

        /**
         * 设置该对话框能否被Cancel掉，默认可以
         *
         * @param cancelable
         */
        public Builder setCancelable(boolean cancelable) {
            mDialog.setCancelable(cancelable);
            return this;
        }

        /**
         * 设置对话框被cancel对应的回调接口，cancel()方法被调用时才会回调该接口
         *
         * @param onCancelListener
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mDialog.setOnCancelListener(onCancelListener);
            return this;
        }

        /**
         * 设置对话框消失对应的回调接口，一切对话框消失都会回调该接口
         *
         * @param onDismissListener
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mDialog.setOnDismissListener(onDismissListener);
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public CommonDialog create() {
            return mDialog;
        }
    }



}
