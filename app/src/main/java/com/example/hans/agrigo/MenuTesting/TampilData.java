package com.example.hans.agrigo.MenuTesting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hans.agrigo.Fragment.AccountFragment;
import com.example.hans.agrigo.MenuTesting.Support.Model;
import com.example.hans.agrigo.MenuTesting.Support.Server;
import com.example.hans.agrigo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.AdapterData;

public class TampilData extends AppCompatActivity {
    ProgressDialog pDialog;

    AdapterData adapter;
    ListView list;

    ArrayList<Model> newsList = new ArrayList<Model>();

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterData(TampilData.this, newsList, mRequestQueue, pDialog);
        list.setAdapter(adapter);
        getAllData();
    }

    private void getAllData() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest( Request.Method.GET, Server.viewData, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
//                            boolean status = response.getBoolean("error");
//                            if (status == false) {
                                String data = response.getString("result");
                                JSONArray jsonArray = new JSONArray(data);
                                Model map = null;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject( i );
                                    map = new Model();
                                    map.set_id( jsonObject.getString( "_id" ) );
                                    map.setLongitude( jsonObject.getString( "longitude" ) );
                                    map.setLatitude( jsonObject.getString( "latitude" ) );
                                }
                                newsList.add( map );
                           // }
                            } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public void onBackPressed(){
        Intent i = new Intent(TampilData.this, AccountFragment.class);
        startActivity(i);
        finish();
    }
}
