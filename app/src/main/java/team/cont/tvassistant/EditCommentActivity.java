package team.cont.tvassistant;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EditCommentActivity extends AppCompatActivity {
    int F_ID,U_ID;
    String F_NAME,F_GENRE;
    TextView tv6;
    Button bSaveChange;
    int position;
    RadioGroup radioGroup;
    RadioButton R1,R2,R3;
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
        setContentView(R.layout.activity_edit_comment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            F_ID = extras.getInt("f_id", 1);
            F_NAME=extras.getString("f_name");
            U_ID=extras.getInt("u_id",1);
            //     F_GENRE=extras.get
        }
        radioGroup = (RadioGroup ) findViewById(R.id.r_group1);
        CommentText = (EditText) findViewById(R.id.editText2);

        R1 = (RadioButton) findViewById(R.id.radioButton2);
        R2 = (RadioButton) findViewById(R.id.radioButton3);
        R3 = (RadioButton) findViewById(R.id.radioButton4);

        bSaveChange = (Button) findViewById(R.id.button6);



        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        new LoadCommentTask().execute();

        bSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SaveChange().execute();

            }
        });

    }

    private class LoadCommentTask extends AsyncTask<String,String,String> {
        private String FComment="";
        int Fposition;

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

                String sql = "SELECT comment.text, comment.position  FROM comment WHERE comment.user_id="+U_ID+" AND comment.video_id="+F_ID;

                final ResultSet rs= st.executeQuery(sql);
                rs.next();
                FComment=rs.getString(1);
                Fposition=rs.getInt(2);

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
            CommentText.setText(FComment);
            switch(Fposition) {
                case 0:
                    R3.setChecked(true);
                    break;
                case 1:
                    R1.setChecked(true);
                    break;
                case 2:
                    R2.setChecked(true);
                    break;
            }


        }
    }

    private class SaveChange extends AsyncTask<String,String,String>{
        String z;
        @Override
        protected void onPreExecute()
        {

            progressBar.setVisibility(View.VISIBLE);
            z=CommentText.getText().toString();

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

        }
        @Override
        protected String doInBackground(String ... params){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                String sql = "UPDATE comment Set text='"+z+"' , position="+position+" WHERE user_id="+U_ID+" AND video_id="+F_ID;
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
            Intent activity1 = new Intent(EditCommentActivity.this, VideoActivity.class);
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
