package com.xy.wanandroid.widget;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.xy.wanandroid.R;


/**
 * Common AlertDialog Package
 *
 * @author chao.qu
 * @date 2017/11/15
 */

public class CommonAlertDialog {

    private AlertDialog alertDialog;

    public static CommonAlertDialog newInstance() {
        return CommonAlertDialogHolder.COMMON_ALERT_DIALOG;
    }

    private static class CommonAlertDialogHolder {
        private static final CommonAlertDialog COMMON_ALERT_DIALOG = new CommonAlertDialog();
    }

    /**
     * Cancel alertDialog
     */
    public void cancelDialog(boolean isAdd) {
        if (isAdd && alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    public void dismissDialog(){

    }

    public void showDialog(Activity mActivity, String content, String btnContent, String neContent,
                           final View.OnClickListener onPoClickListener,
                           final View.OnClickListener onNeClickListener) {
        if (mActivity == null) {
            return;
        }
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(mActivity, R.style.myCorDialog).create();
        }
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_logout);
            TextView contentTv = window.findViewById(R.id.dialog_content);
            contentTv.setText(content);
            TextView mOkBtn = window.findViewById(R.id.dialog_sure);
            mOkBtn.setText(btnContent);
            mOkBtn.setOnClickListener(onPoClickListener);
            TextView mNeBtn = window.findViewById(R.id.dialog_cancel);
            mNeBtn.setText(neContent);
            mNeBtn.setVisibility(View.VISIBLE);
            mNeBtn.setOnClickListener(onNeClickListener);
        }
    }

}
