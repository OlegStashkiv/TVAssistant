package team.cont.tvassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.sql.DriverManager;
import java.sql.Statement;

public class CreateListActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";
    Button Create;
    ProgressBar progressBar;
    EditText ED;
    public static final String LOGIN_SETTING = "loginfile";
    public static final String SAVED_ID = "saved_id";
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, Context.MODE_PRIVATE);
        userID=myPrefs.getInt(SAVED_ID,1);
        Create = (Button) findViewById(R.id.button12);
        progressBar = (ProgressBar) findViewById(R.id.progressBar6);
        ED = (EditText) findViewById(R.id.editText3);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CreateList().execute();


            }
        });
    }


    private class CreateList extends AsyncTask<String,String,String> {
        String name;
        @Override
        protected void onPreExecute()
        {
            name=ED.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String ... params){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                String sql = "INSERT INTO list (user_id, name ) VALUES ("+userID+" , "+"'"+name+"' ) ";
                st.execute(sql);
                con.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

            Intent activity1 = new Intent(CreateListActivity.this, ListActivity.class);
            finish();
            startActivity(activity1);

        }
    }

}
