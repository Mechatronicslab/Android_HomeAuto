package com.example.hans.agrigo.Menu;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hans.agrigo.Fragment.AccountFragment;
import com.example.hans.agrigo.Fragment.FavoriteFragment;
import com.example.hans.agrigo.Fragment.SearchFragment;
import com.example.hans.agrigo.MenuLogin.Login;
import com.example.hans.agrigo.R;

public class MenuUtama extends AppCompatActivity
   implements BottomNavigationView.OnNavigationItemSelectedListener{

    SharedPreferences sharedPreferences;
    String email, name, name2, macSensor;
    public final static String TAG_EMAIL = "email";
    public final static String TAG_NAME = "name";
    public final static String TAG_NAME2 = "name2";
    public final static String KEY_MAC = "macSensor";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu_utama);
            loadFragment(new SearchFragment());
            BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);

//            sharedPreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
//
//            email = sharedPreferences.getString(TAG_EMAIL, null);
//            name = sharedPreferences.getString(TAG_NAME, null);
//            name2 = sharedPreferences.getString(TAG_NAME2, null);
//            macSensor = sharedPreferences.getString(KEY_MAC, null);
//
//            Toast.makeText(this,"Hello "+ name+" " + name2, Toast.LENGTH_LONG).show();

//            Bundle data = new Bundle();
//            data.putString(AccountFragment.KEY_ACTIVITY, email);
//            data.putString(AccountFragment.KEY_ACTIVITY1, name);
//            data.putString(AccountFragment.KEY_ACTIVITY2, name2);
//            AccountFragment fragtry = new AccountFragment();
//            fragtry.setArguments(data);
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fl_container, fragtry)
//                    .commit();
        }

        private boolean loadFragment(Fragment fragment) {
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, fragment)
                        .commit();
                return true;
            }
            return false;
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            Bundle data = new Bundle();
            switch (menuItem.getItemId()){
                case R.id.search_menu:
//                    data.putString(SearchFragment.KEY_MAC, macSensor);
                    fragment = new SearchFragment();
                    fragment.setArguments(data);
                    break;
//                case R.id.favorite_menu:
//                    fragment = new FavoriteFragment();
//                    break;
                case R.id.account_menu:
                    data.putString(AccountFragment.KEY_ACTIVITY, email);
                    data.putString(AccountFragment.KEY_ACTIVITY1, name);
                    data.putString(AccountFragment.KEY_ACTIVITY2, name2);

                    fragment = new AccountFragment();
                    fragment.setArguments(data);
                    break;
            }
            return loadFragment(fragment);
        }
    }