package team.cont.tvassistant;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";
    ProgressBar progressBar;
    private String rstatus="false";
    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних
    public static final String SAVED_ID = "saved_id";
    int userID;
    private List<View> allEds;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, MODE_PRIVATE);
        userID=myPrefs.getInt(SAVED_ID,1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        create = (Button) findViewById(R.id.button9);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity1 = new Intent(ListActivity.this, CreateListActivity.class);

                startActivity(activity1);
            }
        });
        new LoadListsTask().execute();

    }


    private class LoadListsTask extends AsyncTask<String,String,String> {
        ArrayList<String> FNames = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();
        String res="";
        int ind=0;

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

                String sql = "SELECT id, name  FROM list WHERE  user_id="+userID;

                final ResultSet rs= st.executeQuery(sql);


                while(rs.next()){
                    FNames.add(rs.getString(2));
                    IDs.add(rs.getInt(1));
                    ind++;
                }
                con.close();

            }
            catch(Exception e) {
                e.printStackTrace();
                res=e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //    super.onPostExecute(aVoid);

            allEds = new ArrayList<View>();
            //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
            final LinearLayout linear = (LinearLayout) findViewById(R.id.linear10);
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные

            displayMyView(linear,FNames,ind,IDs);
        }
    }


    private void displayMyView(LinearLayout liner,ArrayList<String> names,int numbers,ArrayList<Integer> ids){
        for(int i=0;i<numbers;i++) {

            final View view = getLayoutInflater().inflate(R.layout.list_layout, null);
            Button BT = (Button) view.findViewById(R.id.button10);
            BT.setText(names.get(i));
            int s=i;

            //добавляем все что создаем в массив
            int ID= ids.get(i);
            String nm=names.get(i);


          BT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent activity1 = new Intent(ListActivity.this, VideoListActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putInt("List_ID", ID);
                    bundle.putString("List_Name", nm);
                    bundle.putInt("userID",userID);
                    activity1.putExtras(bundle);
                    startActivity(activity1);

                }
            });

            allEds.add(view);
            liner.addView(view);
        }
    }

}
