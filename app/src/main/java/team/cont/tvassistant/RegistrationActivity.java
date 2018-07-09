package team.cont.tvassistant;

import android.content.Context;
import android.content.Intent;
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

public class RegistrationActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";
    private String tLogin;
    private String tEmail;
    private String pPassword;
    private EditText Login,Email,Password;
    private String rstatus="false";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Login = (EditText) findViewById(R.id.login_e);
        Email = (EditText) findViewById(R.id.email_e);
        Password = (EditText) findViewById(R.id.password_e);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

     public void createUser(View view){
        tLogin=Login.getText().toString();
        tEmail=Email.getText().toString();
        pPassword=Password.getText().toString();
        new MyTask().execute();

    }

        private class MyTask extends AsyncTask<String,String,String>{
            private String fLogin="",FEmail="",ftext="";
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
                  String sql = "SELECT * FROM user WHERE login="+"'"+tLogin+"'"+" or "+"email='"+tEmail+"'";
                  final ResultSet rs=st.executeQuery(sql);
                  if(rs.next())
                  {rstatus="false";}
                  else{rstatus="true";
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
                if(rstatus=="true"){
                    new AddNewUser().execute();

                } else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                        "Такий логін або Е-мейл вже використовується", Toast.LENGTH_SHORT);
                toast.show();
                }
            }
        }

    private class AddNewUser extends AsyncTask<String,String,String>{
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
                String sql = "INSERT INTO user (login, password, email) VALUES ("+"'"+tLogin+"','"+pPassword+"','"+tEmail+"') ";
                st.execute(sql);
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
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Реєстрація пройшла вдало", Toast.LENGTH_SHORT);
                toast.show();
            Intent activity1 = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(activity1);
        }
    }

}


