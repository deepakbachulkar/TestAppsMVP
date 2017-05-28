package com.deepak.testapp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.deepak.testapp.model.User;
import com.deepak.testapp.utils.AppPreferences;
import com.deepak.testapp.utils.Storage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Deepak on 5/28/2017.
 */
public class MainIndicatorImpl implements MainInteractor
{
    Context mContext;

    @Override
    public void serverCall(Context context, String url, final OnLoginFinishedListener listener)
    {
        mContext= context;
        StringRequest request = new StringRequest(Request.Method.GET, "http://bwellthy.getsandbox.com/users", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("","Server Data:"+response);
                try{
                    JSONArray array =new JSONArray(response);
                    List<User> list=new ArrayList<>();
                    for (int j=0; j<array.length(); j++)
                    {
                        JSONObject object = array.getJSONObject(j);
                        User user=new User(object.getString("name"), object.getString("mobileNumber"));
                        list.add(user);
                    }
                    Storage.writeObject(mContext, list);
                    listener.onSuccess(list);
                }catch (Exception e){
                    listener.error("Data Not Valid Format");
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("","Server error:"+error);
                error.printStackTrace();
                listener.error("Unable to connect server");
            }
        });
        RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
        mRequestQueue.add(request);
    }

    @Override
    public void requestPermission(Context context,  final OnLoginFinishedListener listener) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            listener.onRequest(true);
        }else
            listener.onRequest(false);
    }

    @Override
    public void appVersion(Context context, OnLoginFinishedListener listener) {
        int version=1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            version = info.versionCode;
        }catch (Exception e){ }

        // This code will updated in next version. After Updated  Sqlite or Realm below code is remove.
        if(AppPreferences.getVersion(context)>version)
        {
                listener.onVersionCheck(true);
        }else
            listener.onVersionCheck(false);
        AppPreferences.setAppVersion(context, version);
    }
}
