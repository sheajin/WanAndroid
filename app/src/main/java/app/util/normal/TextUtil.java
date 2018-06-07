package app.util.normal;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


import java.util.Random;

/**
 * Created by JinXinyi on 2017/7/28.
 */

public class TextUtil {
    /**
     * 设置下划线
     * */
    public static void setUnderLine(TextView mTv){
        mTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTv.getPaint().setAntiAlias(true);//抗锯齿
    }

    /**
     * 设置中划线
     * */
    public static void setStrike(TextView mTv){
        mTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 设置字体加粗
     * */
    public static void setBold(TextView mTv){
        mTv.getPaint().setFakeBoldText(true);
    }

    /**
     * 修改TextView中部分文字颜色和添加下划线
     * */
//    SpannableString spanText=new SpannableString(getString(R.string.terms_service));
//        spanText.setSpan(new ClickableSpan() {
//
//        @Override
//        public void updateDrawState(TextPaint ds) {
//            super.updateDrawState(ds);
//            ds.setColor(Color.parseColor("#00AAFF"));       //设置文件颜色
//            ds.setUnderlineText(true);                      //设置下划线
//        }
//
//        @Override
//        public void onClick(View view) {
//            ToastUtil.showShort(getContext(),"点击了");
//        }
//    }, spanText.length() - 9, spanText.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        mTvTip.setHighlightColor(Color.TRANSPARENT);            //设置点击后的颜色为透明，否则会一直出现高亮
//        mTvTip.setText(spanText);
//        mTvTip.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
//

    /**
     * 修改TextView部分文字颜色
     * */
//    mTv = (TextView) findViewById(R.id.textview);
//
//    SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());
//
//    //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
//    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
//    builder.setSpan(redSpan,0,1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//    mTv.setText(builder);

    public enum Orientation {
        Width, Height
    }

//    public static int getPx(float px, Orientation orientation) {
//        if (orientation == Orientation.Width) {
//            return (int) (px * AutoLayoutConifg.getInstance().getScreenWidth() / AutoLayoutConifg.getInstance().getDesignWidth());
//        } else if (orientation == Orientation.Height) {
//            return (int) (px * AutoLayoutConifg.getInstance().getScreenHeight() / AutoLayoutConifg.getInstance().getDesignHeight());
//        } else {
//            return 0;
//        }
//    }

    /**
     * 设置字符串中数字颜色
     */
    public static SpannableStringBuilder setNumColor(String str) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (a >= '0' && a <= '9') {
                style.setSpan(new ForegroundColorSpan(Color.GREEN), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }

    /**
     * 设置五颜六色的弹幕
     */
    public static int randomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.rgb(r, g, b);
    }

}