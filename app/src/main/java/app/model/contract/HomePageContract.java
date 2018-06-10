package app.model.contract;

import app.model.data.main.HomePageArticleBean;

/**
 * Created by jxy on 2018/2/2.
 */

public class HomePageContract {

    public interface View {

        void getHomepageListOk(HomePageArticleBean datasBean);

        void getHomepageListErr(String info);

    }

    public interface Presenter {

        void getHomepageList(int page);
    }
}
