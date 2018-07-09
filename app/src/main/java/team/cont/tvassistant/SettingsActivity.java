package team.cont.tvassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {


    private SharedPreferences  sPref;
    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sPref = getSharedPreferences(LOGIN_SETTING, Context.MODE_PRIVATE);
    }

    public void logOut(View view){
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(SAVED_LOGIN, null);
        editor.putString(SAVED_EMAIL, null);
        editor.apply();
        Intent activity1 = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(activity1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, MODE_PRIVATE);
        String userEmail = myPrefs.getString(SAVED_EMAIL,null);
        String userLogin = myPrefs.getString(SAVED_LOGIN,null);

        if (userEmail != null && userLogin != null )
        {Toast toast = Toast.makeText(getApplicationContext(),
                "+++++++++", Toast.LENGTH_SHORT);
            toast.show();

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "-------", Toast.LENGTH_SHORT);
            toast.show();
            Intent activity1 = new Intent(SettingsActivity.this, LoginActivity.class);
            startActivity(activity1);
        }
    }
}
