package com.example.myapplication;

import android.content.Context;
import android.os.StrictMode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMSend {
    private static String BaseUrl =  "https://fcm.googleapis.com/fcm/send";
    private static String SERVER_KEY = "AAAAqqrKb_Y:APA91bHgaHDNETtEjQVxHeBk6F3d9vq9f3lP-zUEY7jf7AY0vjS-6jRahg1qvY8dmLYjfDk1mtaWtLfA19US1Ma6aLqlKeeSemsNcuXWUtT-VlkSyBoDAqcPzAU14o_jbui5rJZwS9eQ";
    public static void send(Context context,String token, String title, String mes){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONObject jsonObject = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(context);
        try {
            jsonObject.put("to",token);
            JSONObject noti = new JSONObject();
            noti.put("title",title);
            noti.put("body",mes);
            jsonObject.put("notification",noti);
            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, BaseUrl, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("FCM" + response);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String,String> param = new HashMap<>();
                    param.put("Content-Type","application/json");
                    param.put("Authorization","key="+SERVER_KEY);
                    return param;
                }
            };
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
