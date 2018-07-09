package team.cont.tvassistant;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DescInfoActivity extends AppCompatActivity {

    TextView TName;
    TextView Desc;
    ProgressBar progressBar;
    int F_ID;
    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_info);
        TName = (TextView) findViewById(R.id.text_name);
        Desc = (TextView) findViewById(R.id.text_Desc);
        progressBar = (ProgressBar) findViewById(R.id.progressBar7);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            F_ID = extras.getInt("f_id", 1);
        }

        new LoadDesc().execute();
    }

    private class LoadDesc extends AsyncTask<String,String,String> {
        private String FDesc="",FName="";

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

                String sql = "SELECT description, name FROM video WHERE id="+F_ID;

                final ResultSet rs= st.executeQuery(sql);
                rs.next();
                FDesc=rs.getString(1);
                FName=rs.getString(2);

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
            //    super.onPostExecute(aVoid);

            Desc.setText(FDesc);
            TName.setText(FName);

        }
    }

    @Override
    public void onBackPressed() {
       finish();
    }


}
