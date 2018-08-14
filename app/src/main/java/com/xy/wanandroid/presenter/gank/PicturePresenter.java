package com.xy.wanandroid.presenter.gank;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.MD5;
import com.xy.wanandroid.util.app.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by jxy on 2018/8/14.
 */

public class PicturePresenter extends BasePresenter {

    public void downloadFile(String url) {
        ApiStore.createApi(ApiService.class)
                .download(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String path = CommonUtil.getDownloadPath("/pic");   //设置下载路径
                        File file = new File(path, MD5.GetMD5Code(url) + ".jpg");   //设置文件名
                        if (!file.exists()) {
                            InputStream is = null;
                            FileOutputStream fos = null;
                            byte[] buffer = new byte[2048];
                            long total = responseBody.contentLength();
                            try {
                                int len = 0;
                                long current = 0;
                                int progress = 0;
                                is = responseBody.byteStream();
                                fos = new FileOutputStream(file);
                                while ((len = is.read(buffer)) != -1) {
                                    fos.write(buffer, 0, len);
                                    current += len;
                                    progress = (int) (current * 100 / total);
                                    LogUtil.e("progress = " + progress + " %");
                                }
                                fos.flush();
                                addToAlbum(file);
                                ToastUtil.show(MyApplication.getInstance(), "保存成功");
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    fos.close();
                                    is.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            ToastUtil.show(MyApplication.getInstance(), "文件已存在");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * 添加到相册
     */
    private void addToAlbum(File file) {
        try {
            MediaStore.Images.Media.insertImage(MyApplication.getInstance().getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        MyApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }
}
