package com.example.hans.agrigo.MenuRegister;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.hans.agrigo.BuildConfig;
import com.example.hans.agrigo.MenuLogin.Login;
import com.example.hans.agrigo.Network.InitRetrofit;
import com.example.hans.agrigo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registerr extends AppCompatActivity{
    @BindView(R.id.noKtp)
    EditText noktp;
    @BindView(R.id.nama)
    EditText name2;
    @BindView(R.id.nomor)
    EditText nomor;
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnRegister)
    Button btnRegis;
    @BindView(R.id.Register)
    RelativeLayout Formregister;
    ProgressDialog loading;
    private static final String TAG = Registerr.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    double Latitude;
    double Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name2.getText().toString().equals("") || alamat.getText().toString().equals("")
                        || noktp.getText().toString().equals("")
                        || email.getText().toString().equals("")
                        || password.getText().toString().equals("")
                        || alamat.getText().toString().equals("")) {
                    name2.setFocusable(false);
                    nomor.setFocusable(false);
                    noktp.setFocusable(false);
                    password.setFocusable(false);
                    email.setFocusable(false);
                    alamat.setFocusable(false);
                    showSnackbar();
                } else {
                    loading = ProgressDialog.show(Registerr.this,"Loading.....",null,true);
                    Signup();
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }
    public void Signup() {
        String  guid = UUID.randomUUID().toString();
        String Nik= String.valueOf(noktp.getText());
        String Nama = String.valueOf(name2.getText());
        String No_tlp = String.valueOf(nomor.getText());
        String Almat = String.valueOf(alamat.getText());
        String Email= String.valueOf(email.getText());
        String Password = String.valueOf(password.getText());
        String Lat="";
        String Long="";

        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().RegsiterUser(guid,Nik,Nama,No_tlp,Almat,Email,Password,Lat,Long);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("msg").equals("Berhasil Registrasi")) {
                            Toast.makeText(Registerr.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                            Log.d("responsenya", response.body().toString());
                            Intent intent = new Intent(Registerr.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String error_message = jsonRESULTS.getString("msg");
                            Toast.makeText(Registerr.this, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }else{
                    Toast.makeText(Registerr.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                    Log.d("responsenya", response.body().toString());
                    loading.dismiss();

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    Log.d("responsenya", t.toString());
                    Toast.makeText(Registerr.this,t.toString(),Toast.LENGTH_LONG).show();
                    loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @OnClick(R.id.btnLogin)
    void btnRegis(){
        Intent b = new Intent(Registerr.this, Login.class);
        startActivity(b);
        finish();
    }

    public void showSnackbar() {
        Snackbar snackbar = Snackbar.make(Formregister, "Kolom Tidak boleh kosong", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ulangi", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(Formregister, "Silahkan ulangi", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        noktp.setFocusableInTouchMode(true);
                        name2.setFocusableInTouchMode(true);
                        email.setFocusableInTouchMode(true);
                        nomor.setFocusableInTouchMode(true);
                        alamat.setFocusableInTouchMode(true);
                        password.setFocusableInTouchMode(true);
                    }
                });
        snackbar.show();
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            Latitude=mLastLocation.getLatitude();
                            Longitude=mLastLocation.getLongitude();
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    private void showSnackbar(final String text) {
        View container = findViewById(R.id.Register);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(Registerr.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.no_location_detected, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
}