package team.cont.tvassistant;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListsInVideoActivity extends AppCompatActivity {

    private List<View> allEds;
    ProgressBar progressBar;
    int F_ID,F_USER_ID;
    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";

    ArrayList<Integer> SavedIDs = new ArrayList<>();
    ArrayList<String> NFNames = new ArrayList<>();
    ArrayList<Integer> NIDs = new ArrayList<>();
    int Nind=0;
    int LN=0;
    int testv=0;
    TextView TextNull,TextNull2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_in_video);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            F_ID = extras.getInt("f_id", 1);
            F_USER_ID=extras.getInt("f_userID",1);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        TextNull = (TextView) findViewById(R.id.textView23);
        TextNull2 = (TextView) findViewById(R.id.textView25);
        new LoadSavedLists().execute();
        new LoadNotSavedLists().execute();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        final LinearLayout linear = (LinearLayout) findViewById(R.id.linear21);
                        if(Nind==0){TextNull2.setVisibility(View.VISIBLE);}else{
                        displayMyView2(linear, NFNames, Nind, NIDs);}
                    }
                },
                5000);
    }

    private class LoadSavedLists extends AsyncTask<String,String,String> {
        ArrayList<String> FNames = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();

        String res="";
        int ind=0;
        int z=0;

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

                String sql = "SELECT list.id, list.name FROM list,list_logs WHERE list_logs.list_id=list.id AND list_logs.video_id="+F_ID+ " AND list.user_id="+F_USER_ID;

                final ResultSet rs= st.executeQuery(sql);

                while(rs.next()){
                    FNames.add(rs.getString(2));
                    IDs.add(rs.getInt(1));
                    ind++;
                    z++;
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
            if(z>0) {
                allEds = new ArrayList<View>();
                //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
                final LinearLayout linear = (LinearLayout) findViewById(R.id.linear20);
                //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные
                SavedIDs=IDs;
                displayMyView(linear, FNames, ind, IDs);
            }else{
                TextNull.setVisibility(View.VISIBLE);

            }
        }
    }

    private void displayMyView(LinearLayout liner,ArrayList<String> names,int numbers,ArrayList<Integer> ids){
        for(int i=0;i<numbers;i++) {

            final View view = getLayoutInflater().inflate(R.layout.list_in_video, null);
            Button Name = (Button) view.findViewById(R.id.button5);
            Button X = (Button) view.findViewById(R.id.button13);
            Name.setText(names.get(i));
            int z=i;
            X.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  new DelFromList(ids.get(z)).execute();
                    Name.setTextColor(Color.parseColor("#CC0000"));
                    Name.setEnabled(false);
                    X.setEnabled(false);

                }
            });

            allEds.add(view);
            liner.addView(view);
        }
    }

    private class DelFromList extends AsyncTask<String,String,String> {
        int z;
        public DelFromList(Integer q) {
            z=q;
        }

        String res="";
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
                String sql = "DELETE FROM list_logs WHERE list_id="+z+" AND video_id="+F_ID;
                st.execute(sql);
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

        }
    }

    private class LoadNotSavedLists extends AsyncTask<String,String,String> {
        ArrayList<String> FNames = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();

        String res="";
        int ind=0;
        int z=0;

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

                String sql = "SELECT id, name FROM list WHERE user_id="+F_USER_ID;

                final ResultSet rs= st.executeQuery(sql);

                while(rs.next()){
                    FNames.add(rs.getString(2));
                    IDs.add(rs.getInt(1));
                    ind++;
                    z++;
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
            if(z>0) {
                allEds = new ArrayList<View>();
                //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
                int i=-1;
                for (Integer str : SavedIDs) { // проходимся по списку forRemoveLines
                    if (IDs.contains(str)) { // определяем содержится ли текущий элемент списка forRemoveLines в списке allLines
                        IDs.remove(str); // если содержится удаляем его
                    }
                    i++;
                    Log.w("FFF=",String.valueOf(i));
                }


                for(int f=0;f<IDs.size();f++){
                Log.w("MYLIST---",String.valueOf(IDs.get(f)));
                 new LoadNotSL(IDs.get(f)).execute();
                }

            }else{
                TextNull2.setVisibility(View.VISIBLE);

            }
        }
    }

    private class LoadNotSL extends AsyncTask<String,String,String> {
        int z;

        public LoadNotSL(Integer q) {
            z=q;
        }

        String res="";
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
                String sql = "Select name, id  FROM list WHERE id="+z;
                final ResultSet rs=st.executeQuery(sql);

                while(rs.next()){
                    NFNames.add(rs.getString(1));
                    NIDs.add(rs.getInt(2));
                }

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
            Nind++;
            Log.w("NNNNN=",String.valueOf(Nind));

            }

    }


    private void displayMyView2(LinearLayout liner,ArrayList<String> names,int numbers,ArrayList<Integer> ids){
        for(int i=0;i<numbers;i++) {
            final View view = getLayoutInflater().inflate(R.layout.list_in_video_2, null);
            Button Name = (Button) view.findViewById(R.id.button5);
            Button X = (Button) view.findViewById(R.id.button13);
            Name.setText(names.get(i));
            int z=i;
            X.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Name.setTextColor(Color.parseColor("#3CCC00"));
                    new AddInList(ids.get(z)).execute();
                    Name.setEnabled(false);
                    X.setEnabled(false);
                }
            });

            allEds.add(view);
            liner.addView(view);
        }
    }


    private class AddInList extends AsyncTask<String,String,String> {
        int z;
        public AddInList(Integer q) {
            z=q;
        }
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
                String sql = "INSERT INTO list_logs (list_id, video_id) VALUES ("+z+" , "+F_ID+" ) ";
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
        }
    }


}
