package com.mbs.favquotes;

public class Sharp {

    /*
    package com.mbs.favquotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
//    Quotes quotes,q1,q2,q3,q4,q5,q6;
    TextView textView;
//    ContentReceiver receiver;
    ArrayList<Quotes> modelList,models;

    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;

    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        setContentView(R.layout.activity_main);
//        receiver = new ContentReceiver();
        recyclerView = findViewById(R.id.recycler_view);
         textView = findViewById(R.id.textView);
         models = new ArrayList<>();
         GetRequest();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(new ContentReceiver(), new IntentFilter("Msg"));

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE) != PackageManager.PERMISSION_GRANTED){
            //IF permission is not been granted then check if user has denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)){
                //Do nothing as user has denied
            }else{
                //a pop up will appear asking for required permission . Allow or deny
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE},MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       switch (requestCode){
           case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
           {
             //check whether the length of grantResults is greater than 0 and is equal to PERMISSION_GRANTED
             if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               Toast.makeText(this,"Access Granted",Toast.LENGTH_LONG).show();
             }else{
                 Toast.makeText(this,"When you have time Grant permission",Toast.LENGTH_LONG).show();
             }
           }


       }
    }

    public ArrayList<Quotes> getModel()  {

        ArrayList<Quotes> models = new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="https://favquoteshack.herokuapp.com/mobile/text";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject content = jsonArray.getJSONObject(i);
                        String contentString = content.getString("content");
                        String date_created = content.getString("date_created");

                        Quotes quotes = new Quotes();
                        quotes.setDescription(contentString);
                        models.add(quotes);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Fetch Data : Response Failed"+
                        error.toString(),Toast.LENGTH_LONG).show();
                textView.setText(error.toString());
            }
        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<>();
//                params.put("content","");
//                return params;
//            }


            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(request);

//        models.add(q2);
//        models.add(q3);
//        models.add(q4);
//        models.add(q5);
//        models.add(q6);
        return models;

    }

    private void GetRequest() {
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="https://favquoteshack.herokuapp.com/api/quotes";

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject content = jsonArray.getJSONObject(i);
//                        String contentString = content.getString("content");
//                        String date_created = content.getString("date_created");
                        String text = content.getString("text");
                        String author = content.getString("author");
//                        textView.append(firstName + ", " + String.valueOf(age) + ", " + mail +"\n\n");
//                        Toast.makeText(MainActivity.this,contentString+" "+date_created,Toast.LENGTH_LONG).show();
//                       textView.app(contentString + " " + date_created);
//                        getModel(contentString);

                        Quotes quotes = new Quotes();
                        quotes.setDescription(text);
                        models.add(quotes);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new RecyclerAdapter(MainActivity.this,models);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Fetch Data : Response Failed"+
                        error.toString(),Toast.LENGTH_LONG).show();
                textView.setText(error.toString());
            }
        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<>();
//                params.put("content","");
//                return params;
//            }


            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(request);

    }

    private void postRequest(final String content) {
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="https://favquoteshack.herokuapp.com/mobile";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Post Data : Response Failed"+
                        error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("content",content);
                return params;
            }


//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
        };

        requestQueue.add(stringRequest);

    }


    public class ContentReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            //int id = intent.getIntExtra("icon",0);
            Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG).show();
            Context remotePackageContext = null;
            try {
                byte[] byteArray =intent.getByteArrayExtra("icon");
                Bitmap bmp = null;
                if(byteArray !=null) {
                    bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
                Quotes model = new Quotes();
                model.setDescription(title +" " +text);
//                model.setImage(bmp);

                if(modelList !=null) {
                    modelList.add(model);
                    adapter.notifyDataSetChanged();
                }else {
                    modelList = new ArrayList<>();
                    modelList.add(model);
                    adapter = new RecyclerAdapter(getApplicationContext(), modelList);
                    recyclerView.setAdapter(adapter);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
    *
    *
    * */
}
