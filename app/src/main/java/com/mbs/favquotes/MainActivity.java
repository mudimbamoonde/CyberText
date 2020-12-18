package com.mbs.favquotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    TextView textView,message;
    ArrayList<Quotes> modelList,models;
    @Override
    protected void onCreate(Bundle save) {
        super.onCreate(save);
        setContentView(R.layout.activity_main);
//        receiver = new ContentReceiver();
        recyclerView = findViewById(R.id.recycler_view);
//        textView = findViewById(R.id.textView);
        message = findViewById(R.id.message);
        models = new ArrayList<>();
//        GetRequest();
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE},1);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new RecyclerAdapter(MainActivity.this,getModel());
        recyclerView.setAdapter(adapter);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(
                            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Enable FavQuote Notification Access to allow You receive Quotes! ", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to Send you Quotes", Toast.LENGTH_SHORT).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


        @SuppressLint("NonConstantResourceId")
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(
                        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<Quotes> getModel(){
        JsonParser j = new JsonParser();
        try {
            FileReader file = new FileReader("assets/data.json");
            Object obj = j.parse(file);

            JSONArray quotes = (JSONArray) obj;
            for (int i=0; i< quotes.length(); i ++){
                Quotes quo = new Quotes();
                quo.setDescription((String) quotes.get(i));
                models.add(quo);
            }

        }catch (FileNotFoundException | JSONException e){
            e.printStackTrace();
        }
        return models;
//        Quotes quotes1 = new Quotes();
//        Quotes quotes2 = new Quotes();
//        Quotes quotes3 = new Quotes();
//        Quotes quotes4 = new Quotes();
//        Quotes quotes5 = new Quotes();
//        quotes.setDescription("Genius is one percent inspiration and ninety-nine percent perspiration.");
//        quotes1.setDescription("You can observe a lot just by watching.");
//        quotes2.setDescription("A house divided against itself cannot stand.");
//        quotes3.setDescription("Difficulties increase the nearer we get to the goal.");
//        quotes4.setDescription("Fate is in your hands and no one elses");
//        quotes5.setDescription("Be the chief but never the lord.");
//        models.add(quotes);
//        models.add(quotes1);
//        models.add(quotes2);
//        models.add(quotes3);
//        models.add(quotes4);
//        models.add(quotes5);
//        return quo;
    }

//    private void GetRequest() {
//        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
//        String url="https://favquoteshack.herokuapp.com/api/quotes";
////        String url="https://favquoteshack.herokuapp.com/mobile/text";
//
//        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray jsonArray = response.getJSONArray("content");
//                    Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_LONG).show();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject content = jsonArray.getJSONObject(i);
//                        String contentString = content.getString("author");
//                        String date_created = content.getString("text");
//
//                        Quotes quotes = new Quotes();
//                        quotes.setDescription(contentString);
//                        models.add(quotes);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                adapter = new RecyclerAdapter(MainActivity.this,models);
//                recyclerView.setAdapter(adapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(MainActivity.this,"Fetch Data : Response Failed"+
////                        error.toString(),Toast.LENGTH_LONG).show();
//            }
//        }){
////            @Override
////            protected Map<String,String> getParams(){
////                Map<String,String> params=new HashMap<>();
////                params.put("content","");
////                return params;
////            }
//
////
////            @Override
////            public Map<String,String> getHeaders() throws AuthFailureError {
////                Map<String,String> params=new HashMap<String, String>();
////                params.put("Content-Type","application/x-www-form-urlencoded");
////                return params;
////            }
//        };
//
//        requestQueue.add(request);
//
//    }

    private void postRequest(final String content) {
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="https://favquoteshack.herokuapp.com/mobile";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
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


        private final  BroadcastReceiver onNotice= new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // String pack = intent.getStringExtra("package");
                String title = intent.getStringExtra("title");
                String text = intent.getStringExtra("text");
                //int id = intent.getIntExtra("icon",0);
                postRequest(title+":->"+text);
                Context remotePackageContext = null;
                try {
//                remotePackageContext = getApplicationContext().createPackageContext(pack, 0);
//                Drawable icon = remotePackageContext.getResources().getDrawable(id);
//                if(icon !=null) {
//                    ((ImageView) findViewById(R.id.imageView)).setBackground(icon);
//                }
                    byte[] byteArray =intent.getByteArrayExtra("icon");
                    Bitmap bmp = null;
                    if(byteArray !=null) {
                        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    }
//                    Model model = new Model();
//                    model.setName(title +" " +text);
//                    model.setImage(bmp);
//
//                    if(modelList !=null) {
//                        modelList.add(model);
//                        adapter.notifyDataSetChanged();
//                    }else {
//                        modelList = new ArrayList<Model>();
//                        modelList.add(model);
//                        adapter = new CustomListAdapter(getApplicationContext(), modelList);
//                        list=(ListView)findViewById(R.id.list);
//                        list.setAdapter(adapter);
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };




}