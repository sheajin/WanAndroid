package app.model.api;

/**
 * Created by JinXinYi on 2018/1/7.
 */

public class BaseResp<T>{
    public T data;
    public int status;
    public String info;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
