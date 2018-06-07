package app.model.contract;

/**
 * Created by jxy on 2018/2/2.
 */

public class MainContract {

    public interface View {

        void getEntranceInfoOk();

        void getEntranceInfoErr(String info);

    }

    public interface Presenter {

        void getEntranceInfo();
    }
}
