package team.cont.tvassistant;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StatisticActivity extends AppCompatActivity {

    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";
    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних
    public static final String SAVED_ID = "saved_id";
    int userID;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        progressBar = (ProgressBar) findViewById(R.id.progressBar8);
        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, MODE_PRIVATE);
        String userEmail = myPrefs.getString(SAVED_EMAIL,null);
        String userLogin = myPrefs.getString(SAVED_LOGIN,null);
        userID=myPrefs.getInt(SAVED_ID,1);

        new LoadSFilm().execute();
        new LoadSSerial().execute();
        new LoadSMult().execute();
        new LoadSAnime().execute();
        new LoadAllSave().execute();
        new LoadLFilm().execute();
        new LoadLSerial().execute();
        new LoadLMult().execute();
        new LoadLAnime().execute();
        new LoadAllLook().execute();

    }



    public String convert(int q){
        String z="";
        double value = (double) q/60;
        z= String.format("%.2f", value)+" год";

        return z;
    }


    private class LoadSFilm extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(saved.id) FROM video,saved WHERE video.id=saved.film_id AND saved.user_id="+ userID +" AND video.type=1";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

            if(Count==0){
                TextView tv = (TextView) findViewById(R.id.textView34);
                tv.setText("0");
                TextView tv2 = (TextView) findViewById(R.id.textView35);
                tv2.setText("0");

            }else{
                TextView tv = (TextView) findViewById(R.id.textView34);
                tv.setText(String.valueOf(Count));
                TextView tv2 = (TextView) findViewById(R.id.textView35);
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadSSerial extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(saved.id) FROM video,saved WHERE video.id=saved.film_id AND saved.user_id="+ userID +" AND video.type=2";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView40);
            TextView tv2 = (TextView) findViewById(R.id.textView41);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadSMult extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(saved.id) FROM video,saved WHERE video.id=saved.film_id AND saved.user_id="+ userID +" AND video.type=3";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView43);
            TextView tv2 = (TextView) findViewById(R.id.textView44);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadSAnime extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(saved.id) FROM video,saved WHERE video.id=saved.film_id AND saved.user_id="+ userID +" AND video.type=4";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView49);
            TextView tv2 = (TextView) findViewById(R.id.textView50);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadAllSave extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(saved.id) FROM video,saved WHERE video.id=saved.film_id AND saved.user_id="+ userID +" ";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView46);
            TextView tv2 = (TextView) findViewById(R.id.textView47);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadLFilm extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(looked.id) FROM video,looked WHERE video.id=looked.film_id AND looked.user_id="+ userID +" AND video.type=1";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView60);
            TextView tv2 = (TextView) findViewById(R.id.textView61);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadLSerial extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(looked.id) FROM video,looked WHERE video.id=looked.film_id AND looked.user_id="+ userID +" AND video.type=2";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView62);
            TextView tv2 = (TextView) findViewById(R.id.textView63);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadLMult extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(looked.id) FROM video,looked WHERE video.id=looked.film_id AND looked.user_id="+ userID +" AND video.type=3";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView64);
            TextView tv2 = (TextView) findViewById(R.id.textView65);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadLAnime extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(looked.id) FROM video,looked WHERE video.id=looked.film_id AND looked.user_id="+ userID +" AND video.type=4";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView66);
            TextView tv2 = (TextView) findViewById(R.id.textView67);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

    private class LoadAllLook extends AsyncTask<String,String,String> {
        private String fLogin="",FEmail="",ftext="";
        private int Count,Sum;
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
                String sql = "Select SUM(video.time), COUNT(looked.id) FROM video,looked WHERE video.id=looked.film_id AND looked.user_id="+ userID +" ";
                final ResultSet rs=st.executeQuery(sql);
                while(rs.next())
                {Count=rs.getInt(2);
                    Sum=rs.getInt(1);}

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView68);
            TextView tv2 = (TextView) findViewById(R.id.textView69);
            if(Count==0){
                tv.setText("0");
                tv2.setText("0");

            }else{
                tv.setText(String.valueOf(Count));
                tv2.setText(convert(Sum));
            }


        }
    }

}
