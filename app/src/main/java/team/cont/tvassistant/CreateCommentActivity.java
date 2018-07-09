package team.cont.tvassistant;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.Statement;

public class CreateCommentActivity extends AppCompatActivity {

    int F_ID,U_ID;
    String F_NAME,F_GENRE;
    TextView tv6;
    Button bAdd;
    int position;
    RadioGroup radioGroup;
    int checkedRadioButtonId;
    EditText CommentText;
    ProgressBar progressBar;
    String CT;
    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);
       tv6 = (TextView) findViewById(R.id.textView6);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            F_ID = extras.getInt("f_id", 1);
            F_NAME=extras.getString("f_name");
            U_ID=extras.getInt("u_id",1);
       //     F_GENRE=extras.get
        }
        radioGroup = (RadioGroup ) findViewById(R.id.r_group1);
        CommentText = (EditText) findViewById(R.id.editText2);
        bAdd = (Button) findViewById(R.id.button6);
    bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CT=CommentText.getText().toString();
                checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                switch(checkedRadioButtonId) {
                    case R.id.radioButton2:
                        position=1;
                        break;
                    case R.id.radioButton3:
                        position=2;
                        break;
                    case R.id.radioButton4:
                        position=0;
                        break;
                }

                new AddTask().execute();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

    }



    private class AddTask extends AsyncTask<String,String,String> {
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
                String sql = "INSERT INTO comment (video_id, user_id, text, position) VALUES ("+F_ID+" , "+U_ID+" , "+"'"+CT+"' , "+position+" ) ";
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

            Intent activity1 = new Intent(CreateCommentActivity.this, VideoActivity.class);
            Bundle bundle = new Bundle();

            bundle.putInt("f_id", F_ID);
            bundle.putString("f_name",F_NAME);
       //     bundle.putString("f_genre",FGenre);

            activity1.putExtras(bundle);
            finish();
            startActivity(activity1);

        }
    }

}
