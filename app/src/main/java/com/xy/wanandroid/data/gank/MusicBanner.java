package com.xy.wanandroid.data.gank;

import java.util.List;

/**
 * Created by jxy on 2018/8/8.
 */

public class MusicBanner {

    public FocusBean focus;

    public class FocusBean {

        public List<ResultBean> result;

        public class ResultBean {

            public String randpic;
        }
    }
}
