package app.util.normal;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by jxy on 2018/3/6.
 */

public class AnimateUtil {

    public void setAlpha(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.8f);
        //设置动画持续时长
        alphaAnimation.setDuration(500);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        alphaAnimation.setFillAfter(true);
        //设置动画结束之后的状态是否是动画开始时的状态，true，表示是保持动画开始时的状态
        alphaAnimation.setFillBefore(true);
        //设置动画的重复模式：反转REVERSE和重新开始RESTART
        alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);
        //设置动画播放次数
//        alphaAnimation.setRepeatCount(AlphaAnimation.INFINITE);
        //开始动画
        view.startAnimation(alphaAnimation);
        //清除动画
        view.clearAnimation();
        //同样cancel()也能取消掉动画
        alphaAnimation.cancel();
    }
}
