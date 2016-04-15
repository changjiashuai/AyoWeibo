package com.ayo.sample.zhttp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.manager.LoadController;
import com.android.volley.manager.LoadListener;
import com.android.volley.manager.RequestManager;
import com.android.volley.manager.RequestMap;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cowthan.sample.App;
import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.cowthan.sample.Utils;
import com.cowthan.sample.menu.Leaf;
import com.cowthan.sample.menu.MenuItem;

import org.ayo.Ayo;
import org.ayo.jlog.JLog;
import org.ayo.lang.TheApp;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/29.
 */
public class HttpDemoActivity extends BaseActivity{
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_http_demo);

        menuItem = new MenuItem("Http请求", 0, 0);
        {
            menuItem.addLeaf(new Leaf("volly get", "", null));
            menuItem.addLeaf(new Leaf("volly post 普通", "", null));
            menuItem.addLeaf(new Leaf("volly post StringEntity", "", null));
            menuItem.addLeaf(new Leaf("volly 上传文件", "", null));
            menuItem.addLeaf(new Leaf("volly 下载文件", "", null));
            menuItem.addLeaf(new Leaf("volly 其他请求方式", "", null));

            menuItem.addLeaf(new Leaf("---------", "", null));
            menuItem.addLeaf(new Leaf("AyoHttp", "", null));
        }

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        for(Leaf leaf: menuItem.subMenus){
            addButton(leaf);
        }
    }

    private LinearLayout ll_root;


    private void addButton(final Leaf leaf){
        Button btn = new Button(getActivity());
        btn.setText(leaf.name);
        btn.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        btn.setBackgroundResource(R.drawable.sel_menu2);
        btn.setTextSize(15);
        btn.setPadding(20, 0, 20, 0);
        btn.setTextColor(Color.WHITE);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(getActivity(), 40));
        lp.gravity = Gravity.CENTER;
        lp.topMargin = Utils.dip2px(getActivity(), 5);
        lp.bottomMargin = Utils.dip2px(getActivity(), 5);
        lp.leftMargin = Utils.dip2px(getActivity(), 5);
        lp.rightMargin = Utils.dip2px(getActivity(), 5);
        ll_root.addView(btn, lp);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(leaf.name.equals("volly get")){
                    vollyGet();
                }else if(leaf.name.equals("volly post 普通")){
                    vollyPost1();
                }else if(leaf.name.equals("volly post StringEntity")){
                    vollyPost2();
                }else if(leaf.name.equals("volly 上传文件")){
                    vollyUpload();
                }else if(leaf.name.equals("volly 下载文件")){
                    vollyDownload();
                }else if(leaf.name.equals("volly 其他请求方式")){
                    volly();
                }else if(leaf.name.equals("")){

                }

            }
        });

    }

    public static final String HOST = "http://172.16.12.39/";
    public static final String URL_GET = HOST + "www/test-api/get.php";
    public static final String URL_POST_1 = HOST + "www/test-api/post1.php";
    public static final String URL_POST_2 = HOST + "www/test-api/post2.php";
    public static final String URL_UPLOAD = HOST + "www/php/web/story.php?functionCode=headerImgage";
    public static final String URL_DOWNLOAD = HOST + "www/php/web/test.jpg";
    //public static final String URL_GET = HOST + "www/get.php";


    private void vollyGet() {
        RequestQueue mQueue = Volley.newRequestQueue(Ayo.context);
        String url = HttpDemoActivity.URL_GET;
        int m = Request.Method.GET;
        url = url + "?username=ddd&pwd=123456";

        StringRequest stringRequest = new StringRequest(m, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JLog.i("请求结果\n" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JLog.i("HTTP reqeust failed: " + error.getMessage());
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> ps = new HashMap<String, String>();
                ps.put("version", TheApp.getAppVersionName());
                ps.put("os", "android");
                return ps;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    JLog.i("get cookie: ------" + rawCookies);
                    return super.parseNetworkResponse(response);
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }

        };
        stringRequest.setTag("hehe");
        //stringRequest.setShouldCache(request.useCache);
//		stringRequest.setCacheEntry(entry);
//		stringRequest.setRequestQueue(requestQueue);
//		stringRequest.setRetryPolicy(retryPolicy);
//		stringRequest.setSequence(sequence);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(stringRequest);
    }

    private void vollyPost1() {
        RequestQueue mQueue = Volley.newRequestQueue(Ayo.context);
        String url = HttpDemoActivity.URL_POST_1;
        int m = Request.Method.POST;

        StringRequest stringRequest = new StringRequest(m, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JLog.i("请求结果\n" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JLog.i("HTTP reqeust failed: " + error.getMessage());
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> ps = new HashMap<String, String>();
                ps.put("username", "hehehe");
                ps.put("pwd", "123456");
                return ps;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> ps = new HashMap<String, String>();
                ps.put("version", TheApp.getAppVersionName());
                ps.put("os", "android");
                return ps;
            }

            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    JLog.i("get cookie: ------" + rawCookies);
                    return super.parseNetworkResponse(response);
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }

        };
        stringRequest.setTag("hehe");
        //stringRequest.setShouldCache(request.useCache);
//		stringRequest.setCacheEntry(entry);
//		stringRequest.setRequestQueue(requestQueue);
//		stringRequest.setRetryPolicy(retryPolicy);
//		stringRequest.setSequence(sequence);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(stringRequest);
    }
    private void vollyPost2() {

        LoadListener callback = new LoadListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(byte[] data, Map<String, String> headers, String url, int actionId) {
                try {
                    String str = new String(data, "utf-8");
                    JLog.i("返回--" + str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMsg, String url, int actionId) {
                JLog.i("出错--" + errorMsg);
            }
        };

        RequestManager.getInstance().init(App.app);
        LoadController c = RequestManager.getInstance().sendRequest(Request.Method.POST,
                HttpDemoActivity.URL_POST_2,
                "hahahahahhahahah", //可以是Map, String, RequestMap
                null,
                callback,
                false,
                3000,
                3000,
                10
        );


    }
    private void vollyUpload() {

        RequestMap rm = new RequestMap();
        rm.put("name", "hehe"); //post参数
        rm.put("file1", App.app.getResources().openRawResource(R.raw.test), "test.jpg");


        LoadListener callback = new LoadListener() {
            @Override
            public void onStart() {
                JLog.i("开始上传");
            }

            @Override
            public void onSuccess(byte[] data, Map<String, String> headers, String url, int actionId) {
                try {
                    String str = new String(data, "utf-8");
                    JLog.i("返回--" + str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMsg, String url, int actionId) {
                JLog.i("出错--" + errorMsg);
            }
        };

        RequestManager.getInstance().init(App.app);
        LoadController c = RequestManager.getInstance().sendRequest(Request.Method.POST,
                HttpDemoActivity.URL_UPLOAD,
                rm, //可以是Map, String, RequestMap
                null,
                callback,
                false,
                3000,
                3000,
                10
        );
    }
    private void vollyDownload() {

    }
    private void volly() {

    }







}
