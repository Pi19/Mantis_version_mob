package com.mantis.project.mantisbt.controller;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton extends Application {

   private static  VolleySingleton instance ;
   private RequestQueue requestQueue ;
   public static  final String TAG = VolleySingleton.class.getSimpleName();


    @Override
    public void onCreate() {
         super.onCreate();
         instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
    public  static synchronized VolleySingleton getInstance(){

            return  instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request <T>req , String tag ){

        req.setTag(tag);
        getRequestQueue().add(req);

    }

    public <T> void addToRequestQueue(Request <T>req ){

        req.setTag(TAG);
        getRequestQueue().add(req);

    }
    public void cancelPendingRequest(Object tag){

        if(requestQueue != null ){

            requestQueue.cancelAll(tag);
        }

    }

}
