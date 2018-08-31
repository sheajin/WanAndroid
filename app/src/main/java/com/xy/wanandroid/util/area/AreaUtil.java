package com.xy.wanandroid.util.area;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangmu on 2017/7/12.
 */

public class AreaUtil {
    private static List<String> province = new ArrayList<>();
    private static JSONArray provinces;

    private static List<AreaBean.DataBean> allArea = new ArrayList<>();
    private static ArrayList<String> options1Items = new ArrayList<>();
    private static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    public static List<AreaBean.DataBean> getArea(@NonNull Context context) throws IOException {
        if (allArea.size() > 0) {
            return allArea;
        } else {
            //解析json数据
            InputStream is = null;
            is = context.getResources().getAssets().open("area.json");
            int available = is.available();
            byte[] b = new byte[available];
            int read = is.read(b);
            String json = new String(b);
            is.close();

            AreaBean areaBean = new Gson().fromJson(json, AreaBean.class);
            ArrayList<AreaBean.DataBean> dataBeen = (ArrayList<AreaBean.DataBean>) areaBean.getData();
            allArea.clear();
            allArea.addAll(dataBeen);
            return allArea;
        }
    }

    public static List<String> getCity(@NonNull Context context, @NonNull String province) throws IOException, JSONException {
        initData(context);
        List<String> cities = new ArrayList<>();
        if (provinces == null) {
        } else {
            for (int i = 0; i < provinces.length(); i++) {
                JSONObject obj = provinces.getJSONObject(i);
                if (!TextUtils.isEmpty(obj.getString("name")) && obj.getString("name").equals(province)){
                    JSONArray arry = obj.getJSONArray("childs");
                    for (int j = 0; j < arry.length(); j++) {
                        cities.add(arry.getJSONObject(j).getString("name"));
                    }
                }
            }
        }
        return cities;
    }

    public static List<String> getProvince(@NonNull Context context) throws IOException, JSONException {
        initData(context);
        if (provinces == null || province.size() > 0) {
        } else {
            for (int i = 0; i < provinces.length(); i++) {
                province.add(provinces.getJSONObject(i).getString("name"));
            }
        }
        return province;
    }


    /**
     * 从asset路径下读取对应文件转String输出
     *
     * @param mContext
     * @return
     */
    private static String getJson(Context mContext, String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }

    /**
     * 解析Json获取城市信息
     */
    private static void initData(Context context) {
        if (provinces == null){
            //解析json数据
            InputStream is = null;
            try {
                is = context.getResources().getAssets().open("area.json");
                int available = is.available();
                byte[] b = new byte[available];
                int read = is.read(b);
                String json = new String(b);
                JSONObject jsonObject = new JSONObject(json);
//            Log.e("ym", "initPickData: "+jsonObject.getString("msg") );
                JSONArray provinces = jsonObject.getJSONArray("data");
                AreaUtil.provinces = provinces;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
