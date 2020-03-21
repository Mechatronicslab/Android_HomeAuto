package com.example.hans.agrigo.MenuLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hans.agrigo.Menu.MenuUtama;
import com.example.hans.agrigo.MenuRegister.Registerr;
import com.example.hans.agrigo.Network.InitRetrofit;
import com.example.hans.agrigo.R;
import com.example.hans.agrigo.Storage.SharedPrefManager;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity{
    SharedPrefManager sharedPrefManager;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnSignin)
    Button btnLogin;
    @BindView(R.id.relativ)
    RelativeLayout coordinatorLayout;
    ProgressDialog loading;

    String name, email, name2, macSensor;
    Boolean session = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check_Inputan();
            }
        });
        if (sharedPrefManager.getSudahLogin()){
            startActivity(new Intent(Login.this, MenuUtama.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

    }

    private void Check_Inputan() {
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            username.setFocusable(false);
            username.setFocusable(false);
            showSnackbar();
        } else {
            loading = ProgressDialog.show(Login.this,"Loading.....",null,true,true);
            RequestLogin();
        }

    }

    private void RequestLogin() {
        String user = String.valueOf(username.getText());
        String pas = String.valueOf(password.getText());
        if (user.equals("")) {
            showSnackbar();
        } else if (pas.equals("")) {
            showSnackbar();
        } else {
//            mApiService.userLogin(Username.getText().toString(), Password.getText().toString())
            retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().userLogin(user,pas);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        loading.dismiss();
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            if (jsonRESULTS.getString("msg").equals("Berhasil Login")){
                                Log.d("response api", jsonRESULTS.toString());
                                String pesan=jsonRESULTS.getString("msg");
                                String json=jsonRESULTS.getString("user");
                                Toast.makeText(Login.this, ""+pesan, Toast.LENGTH_SHORT).show();
                                JSONObject obj = new JSONObject(json);
                                Log.v("Response:",json);
                                JSONObject jsonuser = new JSONObject(json);
                                String id_user=jsonuser .getString("_id");
                                String guid=jsonuser .getString("guid");
                                String no_ktp=jsonuser .getString("no_ktp");
                                String nama=jsonuser .getString("nama");
                                String no_hp=jsonuser .getString("no_hp");
                                String alamat=jsonuser .getString("alamat");
                                String password=jsonuser .getString("password");
                                String email=jsonuser .getString("email");
//                                String latitude=jsonuser .getString("latitude");
//                                String longitude=jsonuser .getString("longitude");
                                String devices=jsonuser .getString("devices");
//                                JSONArray array=new JSONArray(devices);
//                                JSONArray jsonarray = new JSONArray(devices);
//                                for (int i = 0; i < jsonarray.length(); i++) {
//                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
//                                    String mac = jsonobject.getString("devices_mac_address");
//                                    String name = jsonobject.getString("devices_name");
//                                    Log.d("device", String.valueOf(jsonarray));
//                                    Log.d("isi:",mac+name);
//                                }
//                                Log.d("device", String.valueOf(array));
                                Log.v("Response:",id_user+guid+no_ktp+nama+no_hp+alamat+password+devices);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_ID, id_user);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_GUID, guid);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_NIK, no_ktp);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, nama);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_ALAMAT, alamat);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_TELPON, no_hp);
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
//                                Log.d("alamat", alamat+telpon.toString());
                                startActivity(new Intent(Login.this,MenuUtama.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            } else {
//                                hhhhhhhhh
                                String error_message = jsonRESULTS.getString("msg");
                                Toast.makeText(Login.this, error_message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        loading.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.v("debug", "onFailure: ERROR > " + t.toString());
                    try {
                        loading.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
    @OnClick(R.id.btnRegis)
    void btnRegis(){
        Intent b = new Intent(Login.this, Registerr.class);
        startActivity(b);
        finish();
    }

    public void showSnackbar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Harus Di isi !!!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ulangi", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Silahkan ulangi", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        username.setFocusableInTouchMode(true);
                        password.setFocusableInTouchMode(true);
                    }
                });
        snackbar.show();
    }

}
