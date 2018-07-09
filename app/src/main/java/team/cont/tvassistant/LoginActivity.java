package team.cont.tvassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";

    private EditText lEmail;
    private EditText lpassword;
    private String rstatus="false";
    ProgressBar progressBar;
    private String tEmail,tpassword;    


    //Постійні дані
    private SharedPreferences  sPref;
    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних
    public static final String SAVED_ID = "saved_id";    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = getSharedPreferences(LOGIN_SETTING, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        lEmail = (EditText) findViewById(R.id.email_log);
        lpassword = (EditText) findViewById(R.id.passwordE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        //Загрузка (або спроба) даних з локальної бази для
        //визначення необхідності логінення
        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, MODE_PRIVATE);
        String userE = myPrefs.getString(SAVED_EMAIL,null);
        String userL = myPrefs.getString(SAVED_LOGIN,null);
        if (userE != null && userL != null )
        {
             Intent intentq = new Intent(LoginActivity.this, GlobalActivity.class);

             startActivity(intentq);
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, MODE_PRIVATE);
        String userE = myPrefs.getString(SAVED_EMAIL,null);
        String userL = myPrefs.getString(SAVED_LOGIN,null);

        if (userE != null && userL != null )
        {
            Intent intentqs = new Intent(LoginActivity.this, GlobalActivity.class);

            startActivity(intentqs);

        }
    }

    public void goRegistrationActivity(View view){

        Intent activity1 = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(activity1);
    }



    public void LoginOn(View view){
        tEmail=lEmail.getText().toString();
        tpassword=lpassword.getText().toString();
        new LoginTask().execute();

    }


    private class LoginTask extends AsyncTask<String,String,String> {
        Boolean isSuccess = false;
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String ... params){
            try {

                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();

                String sql = "SELECT * FROM user WHERE email="+"'"+tEmail+"'"+" and "+"password='"+tpassword+"'";

                final ResultSet rs=st.executeQuery(sql);
                if(rs.next())
                {rstatus="true";
                isSuccess=true;}
                else{rstatus="false";
                    con.close();}


            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return rstatus;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //    super.onPostExecute(aVoid);
            if(isSuccess){
                new SaveTask().execute();

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Авторизація пройшла успішно", Toast.LENGTH_SHORT);
                toast.show();

            } else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Невірна комбінація Емайла та пароля", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }



    private class SaveTask extends AsyncTask<String,String,String>{
        private String Login="",Email="";
        private int m_id;
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String ... params){
            try {

                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                String sql = "SELECT * FROM user WHERE email="+"'"+tEmail+"'";

                final ResultSet rs= st.executeQuery(sql);
                rs.next();
                Login=rs.getString(2);
                Email=rs.getString(4);
                m_id=rs.getInt(1);
                con.close();


            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return rstatus;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //    super.onPostExecute(aVoid);
            SharedPreferences.Editor editor = sPref.edit();
            editor.putString(SAVED_LOGIN, Login);
            editor.putString(SAVED_EMAIL, Email);
            editor.putInt(SAVED_ID,m_id);
            editor.apply();



        //      sPref = getSharedPreferences("LoginFile",MODE_PRIVATE);
        //      SharedPreferences.Editor ed = sPref.edit();
        //       ed.putString(SAVED_EMAIL, Email);
        //      ed.putString(SAVED_LOGIN, Login);
        //      ed.commit();
            ///
        //      SharedPreferences myPrefs = getSharedPreferences("LoginFile", MODE_PRIVATE);
        //      String userEmail = myPrefs.getString(SAVED_EMAIL,"");
        //     String userLogin = myPrefs.getString(SAVED_LOGIN,"");
        //      TV1.setText(userEmail);

        //     String userEmail=sPref.getString(SAVED_EMAIL,"");
        //    TV1.setText(userEmail);


           // TV1.setText(Login);
          //  TV2.setText(Email);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Hello "+Login, Toast.LENGTH_SHORT);
            toast.show();

             Intent activity1 = new Intent(LoginActivity.this, GlobalActivity.class);
        //    activity1.putExtra("flogin", Login);
        //    activity1.putExtra("femail", Email);
              startActivity(activity1);
        }
    }


}
